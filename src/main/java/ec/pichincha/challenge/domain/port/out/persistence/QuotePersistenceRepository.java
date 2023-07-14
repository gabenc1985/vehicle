package ec.pichincha.challenge.domain.port.out.persistence;

import ec.pichincha.challenge.domain.models.persistence.QuotePersistenceModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface QuotePersistenceRepository {
    Mono<QuotePersistenceModel> saveQuotePersistenceModel(QuotePersistenceModel quotePersistenceModel);

    Flux<QuotePersistenceModel> findQuotePersistenceModelBy(String model, String cryptocurrency,  LocalDate date);

}
