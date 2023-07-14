package ec.pichincha.challenge.infraestructure.adapter.out.external.livecoin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataLiveCoin {

    private BigDecimal lastPriceUSD;

}