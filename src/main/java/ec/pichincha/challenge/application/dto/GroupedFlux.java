package ec.pichincha.challenge.application.dto;


import lombok.Data;
import lombok.Generated;
import lombok.RequiredArgsConstructor;



@Data
@Generated
@RequiredArgsConstructor
public  class GroupedFlux<K, V> {

    private final String model;
    private final String cryptoCurrency;
    private final String version;
    private final V priceUsdSum;
    private final V priceCryptocurrencySum;

    public GroupedFlux(String key, V priceUsdSum, V priceCryptocurrencySum) {
        String[] parts = key.split("#");
        this.model = parts[0];
        this.cryptoCurrency = parts[1];
        this.version = parts[2];
        this.priceUsdSum = priceUsdSum;
        this.priceCryptocurrencySum = priceCryptocurrencySum;
    }
}
