package ec.pichincha.challenge.infraestructure.adapter.out.persistence.repository;

import ec.pichincha.challenge.domain.models.persistence.CarPersistenceModel;
import ec.pichincha.challenge.domain.models.persistence.QuotePersistenceModel;
import ec.pichincha.challenge.domain.port.out.persistence.ModelPersistenceRepository;
import ec.pichincha.challenge.domain.port.out.persistence.QuotePersistenceRepository;
import ec.pichincha.challenge.infraestructure.adapter.out.persistence.RepositoryMapper;
import ec.pichincha.challenge.infraestructure.exception.ApplicationError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenericRepository implements QuotePersistenceRepository, ModelPersistenceRepository {

    private final ModelRepository modelRepository;

    private final QuoteRepository quoteRepository;

    @Override
    public Mono<CarPersistenceModel> findByName(String name) {
        return modelRepository.findByName(name)
                .map(RepositoryMapper.INSTANCE::mapToCarModel)
                .switchIfEmpty(Mono.error(ApplicationError.NOT_FOUND_REGISTER))
                .doOnSuccess(carPersistenceModel -> log.info("Consulta realizada con éxito"))
                .doOnError(throwable -> throwable.printStackTrace());
    }

    @Override
    public Mono<QuotePersistenceModel> saveQuotePersistenceModel(QuotePersistenceModel quoteModel) {
        return quoteRepository.save(RepositoryMapper.INSTANCE.mapToQuote(quoteModel))
                .map(quote -> {
                    quoteModel.setPurchaseId(quote.getPurchaseId());
                    return quoteModel;
                });
    }

    @Override
    public Flux<QuotePersistenceModel> findQuotePersistenceModelBy(String model, String cryptocurrency, LocalDate date) {
        return quoteRepository.findByModelVersionAndDate(model, cryptocurrency, date)
                .map(quoteEntity -> RepositoryMapper.INSTANCE.mapToQuotePersistenceModel(quoteEntity))
                .map(quotePersistenceModel -> {
                    log.info(quotePersistenceModel.toString());
                    return quotePersistenceModel;
                })
                ;
    }
}
