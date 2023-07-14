package ec.pichincha.challenge.domain.port.out.external;

import ec.pichincha.challenge.domain.models.external.CryptocurrencyExternalModel;
import ec.pichincha.challenge.domain.models.enums.CryptocurrencyEnum;
import reactor.core.publisher.Mono;

public interface CryptoCurrencyConnector {

    Mono<CryptocurrencyExternalModel> getCryptoCurrencyPriceByCurrency(CryptocurrencyEnum cryptocurrency, String currency);
}
