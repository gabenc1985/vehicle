package ec.pichincha.challenge.infraestructure.adapter.out.external.coinlore.client;

import ec.pichincha.challenge.infraestructure.adapter.out.external.coinlore.dto.ResponseCoinLore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class CoinLoreClientImpl implements CoinLoreClient {

    @Value("${spring.coin.lore.uri}")
    private String uri;

    @Override
    public Flux<ResponseCoinLore> getCoinLoreByFilter(String codeCrypto) {
        WebClient client = WebClient.create();
        return client.get()
                .uri(uri.concat("?id=").concat(codeCrypto))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ResponseCoinLore.class)
                .map(responseCoinLore -> {
                    log.info(responseCoinLore.toString());
                    return responseCoinLore;
                })
                ;

    }
}
