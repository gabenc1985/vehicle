package ec.pichincha.challenge.infraestructure.adapter.out.cache.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VersionRedis {

    private String model;

    private String version;

    private BigDecimal priceUsd;

    private BigDecimal priceCryptocurrency;

    private String cryptocurrency;

}
