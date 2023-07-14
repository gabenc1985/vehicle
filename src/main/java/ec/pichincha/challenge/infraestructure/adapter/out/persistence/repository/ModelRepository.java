package ec.pichincha.challenge.infraestructure.adapter.out.persistence.repository;

import ec.pichincha.challenge.infraestructure.adapter.out.persistence.entity.CatalogServiceModelEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ModelRepository extends ReactiveCrudRepository<CatalogServiceModelEntity, Long> {

    Mono<CatalogServiceModelEntity> findByName(String name);

}
