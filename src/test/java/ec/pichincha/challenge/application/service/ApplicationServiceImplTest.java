package ec.pichincha.challenge.application.service;

import ec.pichincha.challenge.domain.port.out.cache.QuoteCacheRepository;
import ec.pichincha.challenge.domain.port.out.external.CarModelConnector;
import ec.pichincha.challenge.domain.port.out.external.CryptoCurrencyConnector;
import ec.pichincha.challenge.domain.port.out.persistence.ModelPersistenceRepository;
import ec.pichincha.challenge.domain.port.out.persistence.QuotePersistenceRepository;
import ec.pichincha.challenge.util.Constants;
import ec.pichincha.challenge.util.MockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
@ContextConfiguration(classes = {ApplicationServiceImplTest.class})
class ApplicationServiceImplTest {

    @Mock
    private QuoteCacheRepository quoteCacheRepository;

    @Mock
    private CarModelConnector carModelConnector;

    @Mock
    private ModelPersistenceRepository modelPersistenceRepository;

    @Mock
    private QuotePersistenceRepository quotePersistenceRepository;

    @Mock
    private CryptoCurrencyConnector cryptoCurrencyConnector;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    @Test
    void getResponseVehicleQuote() {
        Mockito.when(modelPersistenceRepository.findByName(Mockito.anyString()))
                .thenReturn(Mono.just(MockData.getCarPersistenceModel()));
        Mockito.when(cryptoCurrencyConnector.getCryptoCurrencyPriceByCurrency(Mockito.any(), Mockito.anyString()))
                .thenReturn(Mono.just(MockData.getCryptocurrencyExternalModel()));
        Mockito.when(carModelConnector.getCarModelByIdAndModel(Mockito.any(), Mockito.anyString()))
                .thenReturn(Flux.just(MockData.getCarModelExternalModel1(), MockData.getCarModelExternalModel2()));
        String uuidRevision = UUID.randomUUID().toString();
        Mockito.when(quoteCacheRepository.saveQuote(Mockito.any())).thenReturn(Mono.just(uuidRevision));

        StepVerifier.create(applicationService.getResponseVehicleQuote(MockData.getRequestGenerateQuoteApiModel()))
                .consumeNextWith(responseGenerateQuoteApiModel -> Assertions.assertEquals(responseGenerateQuoteApiModel.getConvertionId(), uuidRevision))
                .verifyComplete()
        ;
        Mockito.verify(modelPersistenceRepository, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(carModelConnector, Mockito.times(1)).getCarModelByIdAndModel(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(quoteCacheRepository, Mockito.times(1)).saveQuote(Mockito.any());


    }

    @Test
    void getResponseVehicleOrder() {
        Mockito.when(quoteCacheRepository.findQuote(Mockito.any())).thenReturn(Mono.just(MockData.getQuoteCacheModel()));
        Mockito.when(quotePersistenceRepository.saveQuotePersistenceModel(Mockito.any())).thenReturn(Mono.just(MockData.getQuotePersistenceModel()));

        StepVerifier.create(applicationService.getResponseVehicleOrder(MockData.getRequestSaveQuoteApiModel()))
                .consumeNextWith(responseSaveQuoteApiModel -> {
                    Assertions.assertNotNull(responseSaveQuoteApiModel);
                })
                .verifyComplete()
        ;
        Mockito.verify(quoteCacheRepository, Mockito.times(1)).findQuote(Mockito.any());
        Mockito.verify(quotePersistenceRepository, Mockito.times(1)).saveQuotePersistenceModel(Mockito.any());
    }

    @Test
    void getResponseGenerateReportApiModelFlux() {

        Mockito.when(quotePersistenceRepository.findQuotePersistenceModelBy(Mockito.anyString(), Mockito.anyString(), Mockito.any()))
                .thenReturn(Flux.just(
                        MockData.getQuotePersistenceModel(Constants.ACCENT_MODEL, Constants.VERSION_ACCENT_1, Constants.PRICE_CRYPTOCURRENCY, Constants.PRICE_VERSION_ACCENT_1),
                        MockData.getQuotePersistenceModel(Constants.ACCENT_MODEL, Constants.VERSION_ACCENT_1, Constants.PRICE_CRYPTOCURRENCY, Constants.PRICE_VERSION_ACCENT_1),
                        MockData.getQuotePersistenceModel(Constants.ACCENT_MODEL, Constants.VERSION_ACCENT_2, Constants.PRICE_CRYPTOCURRENCY, Constants.PRICE_VERSION_ACCENT_2),
                        MockData.getQuotePersistenceModel(Constants.ACCENT_MODEL, Constants.VERSION_ACCENT_1, Constants.PRICE_CRYPTOCURRENCY, Constants.PRICE_VERSION_ACCENT_1),
                        MockData.getQuotePersistenceModel(Constants.ACCENT_MODEL, Constants.VERSION_ACCENT_1, Constants.PRICE_CRYPTOCURRENCY, Constants.PRICE_VERSION_ACCENT_1),
                        MockData.getQuotePersistenceModel(Constants.ACCENT_MODEL, Constants.VERSION_ACCENT_1, Constants.PRICE_CRYPTOCURRENCY, Constants.PRICE_VERSION_ACCENT_1),
                        MockData.getQuotePersistenceModel(Constants.ACCENT_MODEL, Constants.VERSION_ACCENT_2, Constants.PRICE_CRYPTOCURRENCY, Constants.PRICE_VERSION_ACCENT_2),
                        MockData.getQuotePersistenceModel(Constants.ACCENT_MODEL, Constants.VERSION_ACCENT_2, Constants.PRICE_CRYPTOCURRENCY, Constants.PRICE_VERSION_ACCENT_2)
                ));
        StepVerifier.create(applicationService.getResponseGenerateReportApiModelFlux(MockData.getRequestGenerateReportApiModel()))
                .consumeNextWith(responseGenerateReportApiModel -> {
                    switch (responseGenerateReportApiModel.getVersion()) {
                        case Constants.VERSION_ACCENT_1 -> {
                            Assertions.assertEquals(responseGenerateReportApiModel.getCryptocurrencyAmount(), Constants.PRICE_CRYPTOCURRENCY.multiply(new BigDecimal(5)));
                            Assertions.assertEquals(responseGenerateReportApiModel.getUsdAmount(), Constants.PRICE_VERSION_ACCENT_1.multiply(new BigDecimal(5)));
                        }
                        case Constants.VERSION_ACCENT_2 -> {
                            Assertions.assertEquals(responseGenerateReportApiModel.getCryptocurrencyAmount(), Constants.PRICE_CRYPTOCURRENCY.multiply(new BigDecimal(3)));
                            Assertions.assertEquals(responseGenerateReportApiModel.getUsdAmount(), Constants.PRICE_VERSION_ACCENT_2.multiply(new BigDecimal(3)));
                        }
                        default -> Assertions.assertFalse(false);
                    }

                })
                .consumeNextWith(responseGenerateReportApiModel -> {
                    switch (responseGenerateReportApiModel.getVersion()) {
                        case Constants.VERSION_ACCENT_1 -> {
                            Assertions.assertEquals(responseGenerateReportApiModel.getCryptocurrencyAmount(), Constants.PRICE_CRYPTOCURRENCY.multiply(new BigDecimal(5)));
                            Assertions.assertEquals(responseGenerateReportApiModel.getUsdAmount(), Constants.PRICE_VERSION_ACCENT_1.multiply(new BigDecimal(5)));
                        }
                        case Constants.VERSION_ACCENT_2 -> {
                            Assertions.assertEquals(responseGenerateReportApiModel.getCryptocurrencyAmount(), Constants.PRICE_CRYPTOCURRENCY.multiply(new BigDecimal(3)));
                            Assertions.assertEquals(responseGenerateReportApiModel.getUsdAmount(), Constants.PRICE_VERSION_ACCENT_2.multiply(new BigDecimal(3)));
                        }
                        default -> Assertions.assertFalse(false);
                    }

                })
                .verifyComplete()
                ;

        Mockito.verify(quotePersistenceRepository, Mockito.times(1)).findQuotePersistenceModelBy(Mockito.anyString(), Mockito.anyString(), Mockito.any());
    }
}