package ec.pichincha.challenge.infraestructure.adapter.out.external.coinlore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseCoinLore {

    @JsonProperty("price_usd")
    private BigDecimal priceUsd;

}
