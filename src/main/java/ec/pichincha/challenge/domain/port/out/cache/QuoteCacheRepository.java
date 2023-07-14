package ec.pichincha.challenge.domain.port.out.cache;

import ec.pichincha.challenge.domain.models.cache.QuoteCacheModel;
import reactor.core.publisher.Mono;

public interface QuoteCacheRepository {

    Mono<String> saveQuote(QuoteCacheModel quote);

    Mono<QuoteCacheModel> findQuote(String convertionId);
}
