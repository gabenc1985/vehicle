package ec.pichincha.challenge.infraestructure.adapter.out.persistence.repository;

import ec.pichincha.challenge.infraestructure.exception.ApplicationException;
import ec.pichincha.challenge.util.MockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import reactor.test.StepVerifier;

@SpringBootTest
@EnableR2dbcRepositories
//@Import(H2TestSupport.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GenericRepositoryTest {

    @Autowired
    GenericRepository genericRepository;

    @Autowired
    ModelRepository modelRepository;

   // @Autowired
   // private DatabaseClient databaseClient;

    /*
    @PostConstruct
    void init() {
        databaseClient.sql("CREATE TABLE IF NOT EXISTS public.catalog_service_model(id SERIAL PRIMARY KEY, name VARCHAR(250) NOT NULL, id_model VARCHAR(10) NOT NULL, id_vehicle VARCHAR(10) NOT NULL);")
                .then()
                .block();
        databaseClient.sql("CREATE TABLE IF NOT EXISTS public.quote(id SERIAL PRIMARY KEY, full_name VARCHAR(250) NOT NULL, model VARCHAR(100) NOT NULL, version VARCHAR(100) NOT NULL, purchase_id VARCHAR(100) NOT NULL, create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, cryptocurrency VARCHAR(100) NOT NULL, price_usd NUMERIC(12, 2) DEFAULT 0.0, price_cryptocurrency NUMERIC(12, 4) DEFAULT 0.0 );")
                .then()
                .block();

        databaseClient.sql("DELETE FROM catalog_service_model;")
                .then()
                .block();

        databaseClient.sql("INSERT INTO catalog_service_model(name, id_model, id_vehicle) values ('ACCENT', '1','1036');")
                .then()
                .block();

    }

    @PreDestroy
    void destroy(){
        databaseClient.sql("DELETE FROM catalog_service_model;")
                .then()
                .block();

        databaseClient.sql("DELETE FROM quote;")
                .then()
                .block();
    }*/

    @Test
    void findByName() {
        StepVerifier.create(genericRepository.findByName("ACCENT"))
                .consumeNextWith(carPersistenceModel -> Assertions.assertNotNull(carPersistenceModel))
                .verifyComplete();
    }

    @Test
    void findByNameThrow() {
        StepVerifier.create(genericRepository.findByName("demo"))
                .consumeErrorWith(throwable -> Assertions.assertInstanceOf(ApplicationException.class, throwable))
                .verify();
    }

   // @Test
    void saveQuotePersistenceModel() {
        StepVerifier.create(genericRepository.saveQuotePersistenceModel(MockData.getQuotePersistenceModel()))
                .consumeNextWith(quotePersistenceModel -> Assertions.assertNotNull(quotePersistenceModel.getPurchaseId()))
                .verifyComplete();

    }

}