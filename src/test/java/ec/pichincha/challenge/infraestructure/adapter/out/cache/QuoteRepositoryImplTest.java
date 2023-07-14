package ec.pichincha.challenge.infraestructure.adapter.out.cache;

import com.google.gson.Gson;
import ec.pichincha.challenge.infraestructure.adapter.out.cache.entity.QuoteRedis;
import ec.pichincha.challenge.util.MockData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class QuoteRepositoryImplTest {

    @Mock
    ReactiveRedisTemplate <String, QuoteRedis> redisTemplate;

    @InjectMocks
    QuoteRepositoryImpl quoteRepositoryImpl;

    CacheMapper quoteMapper = CacheMapper.INSTANCE;

    @Mock
    private ReactiveHashOperations hashOperations;

    @BeforeEach
    public void setup() {

        Mockito.when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        Mockito.when(hashOperations.get(Mockito.any(), Mockito.any())).thenReturn(Mono.just(new Gson().toJson(MockData.getQuoteRedis())));
        Mockito.when(hashOperations.put(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Mono.just(Boolean.TRUE));
        Mockito.when(redisTemplate.expire(Mockito.any(), Mockito.any())).thenReturn(Mono.just(Boolean.TRUE));
        Mockito.when(redisTemplate.getExpire(Mockito.any())).thenReturn(Mono.just(Duration.ofSeconds(10)));
        ReflectionTestUtils.setField(quoteRepositoryImpl, "quoteMapper", quoteMapper);
        ReflectionTestUtils.setField(quoteRepositoryImpl, "hashOperations", hashOperations);
        ReflectionTestUtils.setField(quoteRepositoryImpl, "ttl", 200);
    }

    @Test
    void saveQuote() {

        StepVerifier.create(quoteRepositoryImpl.saveQuote(MockData.getQuoteCacheModel()))
                .consumeNextWith(s -> assertNotNull(s))
                .verifyComplete();
        Mockito.verify(hashOperations, Mockito.times(1)).put(Mockito.any(), Mockito.any(), Mockito.any());

    }

    @Test
    void findQuote() {
        StepVerifier.create(quoteRepositoryImpl.findQuote(UUID.randomUUID().toString()))
                .consumeNextWith(s -> assertNotNull(s))
                .verifyComplete();
    }
}