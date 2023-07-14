package ec.pichincha.challenge.application.service;

import ec.pichincha.challenge.application.dto.SumAccumulator;
import ec.pichincha.challenge.application.mapper.Mapper;
import ec.pichincha.challenge.domain.models.Version;
import ec.pichincha.challenge.domain.models.api.*;
import ec.pichincha.challenge.domain.models.cache.QuoteCacheModel;
import ec.pichincha.challenge.domain.models.persistence.QuotePersistenceModel;
import ec.pichincha.challenge.domain.port.in.QuoteService;
import ec.pichincha.challenge.domain.port.in.ReportService;
import ec.pichincha.challenge.domain.port.out.cache.QuoteCacheRepository;
import ec.pichincha.challenge.domain.port.out.external.CarModelConnector;
import ec.pichincha.challenge.domain.port.out.external.CryptoCurrencyConnector;
import ec.pichincha.challenge.domain.port.out.persistence.ModelPersistenceRepository;
import ec.pichincha.challenge.domain.port.out.persistence.QuotePersistenceRepository;
import ec.pichincha.challenge.infraestructure.exception.ApplicationError;
import ec.pichincha.challenge.infraestructure.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashSet;
import java.util.function.BiFunction;

@RequiredArgsConstructor
@Slf4j
public class ApplicationServiceImpl implements QuoteService, ReportService {

    private final QuoteCacheRepository quoteCacheRepository;

    private final CarModelConnector carModelConnector;

    private final ModelPersistenceRepository modelPersistenceRepository;

    private final QuotePersistenceRepository quotePersistenceRepository;

    private final CryptoCurrencyConnector cryptoCurrencyConnector;


    @Override
    public Mono<ResponseGenerateQuoteApiModel> getResponseVehicleQuote(RequestGenerateQuoteApiModel requestGenerateQuoteApiModel) {

        return Mono.zip(modelPersistenceRepository.findByName(requestGenerateQuoteApiModel.getModel()),
                        cryptoCurrencyConnector.getCryptoCurrencyPriceByCurrency(requestGenerateQuoteApiModel.getCryptocurrency(), "USD"))
                .onErrorMap(throwable -> new ApplicationException("400", throwable.getMessage(), HttpStatus.BAD_REQUEST))
                .flatMap(objects -> carModelConnector.getCarModelByIdAndModel(objects.getT1().getIdModel(), objects.getT1().getIdVehicle())
                        .map(carModel -> {
                                    BigDecimal priceCrypto = objects.getT2().getPriceUsd();
                                    Version version = new Version();
                                    version.setModel(requestGenerateQuoteApiModel.getModel());
                                    version.setVersion(carModel.getVersion());
                                    version.setPriceUsd(carModel.getPriceUsd());
                                    version.setPriceCryptocurrency(carModel.getPriceUsd().setScale(3, RoundingMode.HALF_UP).divide(priceCrypto, RoundingMode.HALF_UP));
                                    version.setCryptocurrency(requestGenerateQuoteApiModel.getCryptocurrency().getDescription());
                                    log.info(version.toString());
                                    return version;
                                }
                        )
                        .collectList()
                        .flatMap(carModels -> {
                            QuoteCacheModel data = new QuoteCacheModel();
                            data.setVersions(new HashSet<>(carModels));
                            return quoteCacheRepository.saveQuote(data)
                                    .map(s -> {
                                        data.setConvertionId(s);
                                        return data;
                                    });
                        }))
                .map(quoteCacheModel -> Mapper.INSTANCE.mapToResponseQuoteApiModel(quoteCacheModel));
    }

    @Override
    public Mono<ResponseSaveQuoteApiModel> getResponseVehicleOrder(RequestSaveQuoteApiModel requestSaveQuoteApiModel) {
        return this.quoteCacheRepository.findQuote(requestSaveQuoteApiModel.getConvertionId())
                .switchIfEmpty(Mono.error(ApplicationError.QUOTE_NOT_FOUND))
                .map(quote -> quote.getVersions().stream().filter(version -> version.getVersion().equals(requestSaveQuoteApiModel.getVersion())
                                && version.getCryptocurrency().equals(requestSaveQuoteApiModel.getCryptocurrency())
                                && version.getModel().equals(requestSaveQuoteApiModel.getModel()))
                        .findFirst().orElseThrow(() -> ApplicationError.QUOTE_INCORRECT))
                .switchIfEmpty(Mono.error(ApplicationError.QUOTE_INCORRECT))
                .map(Mapper.INSTANCE::mapToQuotePersistenceModel)
                .flatMap(quotePersistenceModel -> {
                    quotePersistenceModel.setCreateDate(new Date());
                    quotePersistenceModel.setCryptocurrency(requestSaveQuoteApiModel.getCryptocurrency());
                    quotePersistenceModel.setFullName(requestSaveQuoteApiModel.getFullName());
                    return this.quotePersistenceRepository.saveQuotePersistenceModel(quotePersistenceModel);
                })
                .map(quote -> Mapper.INSTANCE.mapToResponseOrderApiModel(quote));

    }

    @Override
    public Flux<ResponseGenerateReportApiModel> getResponseGenerateReportApiModelFlux(RequestGenerateReportApiModel requestGenerateQuoteApiModel) {

        BiFunction<SumAccumulator, QuotePersistenceModel, SumAccumulator> accumulator = (sum, item) -> {
            sum.addPriceUsd(item.getPriceUsd());
            sum.addPriceCryptocurrency(item.getPriceCryptocurrency());
            return sum;
        };
        log.info("Inicia proceso generación de reporte {}",requestGenerateQuoteApiModel.toString() );
        return quotePersistenceRepository.findQuotePersistenceModelBy(requestGenerateQuoteApiModel.getModel(), requestGenerateQuoteApiModel.getCryptocurrency().getDescription(), requestGenerateQuoteApiModel.getDate())
                .groupBy(item -> item.getModel() + "#" + item.getVersion() + "#" + item.getCryptocurrency() )
                .flatMap(group -> group
                        .reduce(new SumAccumulator(), accumulator)
                        .map(sum -> new ResponseGenerateReportApiModel(
                                group.key().split("#")[0],
                                group.key().split("#")[1],
                                group.key().split("#")[2],
                                sum.getPriceUsdSum(),
                                sum.getPriceCryptocurrencySum(),
                                requestGenerateQuoteApiModel.getDate()
                        ))
                )
                .map(responseGenerateReportApiModel -> responseGenerateReportApiModel);
    }
}
