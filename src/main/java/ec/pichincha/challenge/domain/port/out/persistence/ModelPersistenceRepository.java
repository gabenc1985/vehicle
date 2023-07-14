package ec.pichincha.challenge.domain.port.out.persistence;

import ec.pichincha.challenge.domain.models.persistence.CarPersistenceModel;
import reactor.core.publisher.Mono;

public interface ModelPersistenceRepository {
    Mono<CarPersistenceModel> findByName(String name);

}
