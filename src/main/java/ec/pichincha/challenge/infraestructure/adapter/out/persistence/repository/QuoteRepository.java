package ec.pichincha.challenge.infraestructure.adapter.out.persistence.repository;

import ec.pichincha.challenge.infraestructure.adapter.out.persistence.entity.QuoteEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface QuoteRepository extends ReactiveCrudRepository<QuoteEntity, Long> {

    @Query("SELECT * FROM public.quote WHERE " +
            "(model = :param1) " +
            "AND (cryptocurrency = :param2) " +
            "AND DATE_TRUNC('day', create_date) = DATE_TRUNC('day',:param3)")
    Flux<QuoteEntity> findByModelVersionAndDate(@Param("param1") String model, @Param("param2") String cryptocurrency, @Param("param3")LocalDate localDate);


}
