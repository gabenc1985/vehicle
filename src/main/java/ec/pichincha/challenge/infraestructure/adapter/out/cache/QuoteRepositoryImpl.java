package ec.pichincha.challenge.infraestructure.adapter.out.cache;

import com.google.gson.Gson;
import ec.pichincha.challenge.domain.models.cache.QuoteCacheModel;
import ec.pichincha.challenge.domain.port.out.cache.QuoteCacheRepository;
import ec.pichincha.challenge.infraestructure.adapter.out.cache.entity.QuoteRedis;
import ec.pichincha.challenge.infraestructure.conf.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@Component
@Import({RedisConfig.class})
@Slf4j
public class QuoteRepositoryImpl implements QuoteCacheRepository {

    @Autowired
    private CacheMapper quoteMapper;

    @Value("${entity.cache.ttl}")
    private Integer ttl;

    private final ReactiveRedisTemplate<String, QuoteRedis> redisTemplate;
    private final ReactiveHashOperations<String, String, String> hashOperations;

    private final String KEY = "_QUOTE_";

    public QuoteRepositoryImpl(ReactiveRedisTemplate<String, QuoteRedis> template) {

        this.redisTemplate = template;
        this.hashOperations = redisTemplate.opsForHash();
    }

    private int TTL;

    @Override
    public Mono<String> saveQuote(QuoteCacheModel quote) {
        String uuid = UUID.randomUUID().toString();
        QuoteRedis quoteRedis = quoteMapper.mapToQuoteRedis(quote);
        quoteRedis.setConvertionId(uuid);
        log.info(quoteRedis.getConvertionId());
        return this.hashOperations.put(KEY + uuid, uuid, new Gson().toJson(quoteRedis))
                .flatMap(aBoolean ->
                {
                    quote.setConversionTimelife(String.valueOf(ttl).concat(" seconds"));
                    return
                            redisTemplate.expire(KEY + uuid, Duration.ofSeconds(ttl))
                                    .map(aBoolean1 -> uuid);
                });
    }

    @Override
    public Mono<QuoteCacheModel> findQuote(String convertionId) {
        return this.hashOperations.get(KEY + convertionId, convertionId)
                .flatMap(s -> {
                    QuoteRedis quoteRedis = new Gson().fromJson(s, QuoteRedis.class);
                    QuoteCacheModel quote = quoteMapper.mapToQuoteCacheModel(quoteRedis);
                    return redisTemplate.getExpire(KEY + convertionId)
                            .map(duration -> {
                                quote.setConversionTimelife(String.valueOf(duration.getSeconds()).concat(" seconds"));
                                log.info(quote.toString());
                                return quote;
                            });
                });
    }

}
