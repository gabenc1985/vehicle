package ec.pichincha.challenge.infraestructure.adapter.out.external.livecoin.client;

import ec.pichincha.challenge.infraestructure.adapter.out.external.livecoin.dto.ResponseLiveCoinWatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class LiveCoinWatchClientImpl implements LiveCoinWatchClient {

    @Value("${spring.live.coin.watch.uri}")
    private String uri;


    @Override
    public Mono<ResponseLiveCoinWatch> getLiveCoinWatchByFilters(String cryptocurrency, String currency) {
        WebClient client = WebClient.create();
        String url = String.format(uri, cryptocurrency, currency);
        return client.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ResponseLiveCoinWatch.class)
                //.transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .map(responseLiveCoinWatch -> {
                    if(responseLiveCoinWatch.getData() == null)
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                    return responseLiveCoinWatch;
                }).retry(3)
                //.onErrorMap(throwable -> new RuntimeException());
                .doOnError(throwable -> log.error("Error LiveCoinWatchClient: {}" , throwable.getMessage()));
    }
}
