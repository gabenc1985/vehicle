package ec.pichincha.challenge.infraestructure.conf;

import ec.pichincha.challenge.domain.port.in.QuoteService;
import ec.pichincha.challenge.application.service.ApplicationServiceImpl;
import ec.pichincha.challenge.domain.port.out.cache.QuoteCacheRepository;
import ec.pichincha.challenge.domain.port.out.external.CarModelConnector;
import ec.pichincha.challenge.domain.port.out.external.CryptoCurrencyConnector;
import ec.pichincha.challenge.domain.port.out.persistence.ModelPersistenceRepository;
import ec.pichincha.challenge.domain.port.out.persistence.QuotePersistenceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanQuoteConfiguration {

    @Bean
    QuoteService quoteBeanService(final QuoteCacheRepository quoteCacheRepository, final CarModelConnector carModelConnector, final ModelPersistenceRepository modelRepository, final QuotePersistenceRepository quoteRepository, final CryptoCurrencyConnector cryptoCurrencyConnector){
        return new ApplicationServiceImpl(quoteCacheRepository, carModelConnector, modelRepository, quoteRepository, cryptoCurrencyConnector);
    }
}
