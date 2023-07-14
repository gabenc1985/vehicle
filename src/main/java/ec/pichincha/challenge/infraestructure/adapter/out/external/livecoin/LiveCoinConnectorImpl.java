package ec.pichincha.challenge.infraestructure.adapter.out.external.livecoin;

import ec.pichincha.challenge.domain.models.external.CryptocurrencyExternalModel;
import ec.pichincha.challenge.domain.models.enums.CryptocurrencyEnum;
import ec.pichincha.challenge.domain.port.out.external.CryptoCurrencyConnector;
import ec.pichincha.challenge.infraestructure.adapter.out.external.coinlore.client.CoinLoreClient;
import ec.pichincha.challenge.infraestructure.adapter.out.external.livecoin.client.LiveCoinWatchClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class LiveCoinConnectorImpl implements CryptoCurrencyConnector {

    private final LiveCoinWatchClient liveCoinWatchConnector;

    private final CoinLoreClient coinLoreClient;

    @Override
    @CircuitBreaker(name = "cryptocurrency-service", fallbackMethod = "getCryptoCurrencyFallback")
    public Mono<CryptocurrencyExternalModel> getCryptoCurrencyPriceByCurrency(CryptocurrencyEnum cryptocurrency, String currency) {
       return liveCoinWatchConnector.getLiveCoinWatchByFilters(cryptocurrency.getDescription(), currency)
               .map(liveCoinWatchResponse -> CryptocurrencyExternalModel.builder().priceUsd(liveCoinWatchResponse.getData().getLastPriceUSD()).code(currency).build())
               .doOnSuccess(live-> log.info("Get live coin watch successful price: {}", live.getPriceUsd()))
               .doOnError(throwable -> log.error("Error consume live coin watch: {}" , throwable.getMessage()));
    }

    public Mono<CryptocurrencyExternalModel> getCryptoCurrencyFallback(CryptocurrencyEnum cryptocurrency, String currency, Throwable throwable) {
        log.info("Fallback Method getCryptoCurrencyFallback");
        return coinLoreClient.getCoinLoreByFilter(cryptocurrency.getCode())
                .next()
                .map(liveCoinWatchResponse -> {
                    log.info(liveCoinWatchResponse.toString());
                    return CryptocurrencyExternalModel.builder().priceUsd(liveCoinWatchResponse.getPriceUsd()).code(currency).build();
                })
                .doOnSuccess(live-> log.info("Get live coin watch successful {}", live.getPriceUsd()))
                .doOnError(error -> log.error("Error consume live coin watch: {}" , throwable.getMessage()));
    }
}
