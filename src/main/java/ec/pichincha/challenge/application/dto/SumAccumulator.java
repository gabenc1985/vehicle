package ec.pichincha.challenge.application.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class SumAccumulator {
    private BigDecimal priceUsdSum = BigDecimal.ZERO;
    private BigDecimal priceCryptocurrencySum = BigDecimal.ZERO;

    public void addPriceUsd(BigDecimal price) {
        priceUsdSum = priceUsdSum.add(price);
    }

    public void addPriceCryptocurrency(BigDecimal price) {
        priceCryptocurrencySum = priceCryptocurrencySum.add(price);
    }

}