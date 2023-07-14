package ec.pichincha.challenge.domain.models;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Generated
public class Version {

    private String model;
    private String version;
    private BigDecimal priceUsd;
    private BigDecimal priceCryptocurrency;
    private String cryptocurrency;

}
