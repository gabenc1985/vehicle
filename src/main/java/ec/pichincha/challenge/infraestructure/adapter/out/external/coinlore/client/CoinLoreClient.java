package ec.pichincha.challenge.infraestructure.adapter.out.external.coinlore.client;

import ec.pichincha.challenge.infraestructure.adapter.out.external.coinlore.dto.ResponseCoinLore;
import reactor.core.publisher.Flux;

public interface CoinLoreClient {

    Flux<ResponseCoinLore> getCoinLoreByFilter(String codeCrypto);
}
