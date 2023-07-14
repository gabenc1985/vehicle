package ec.pichincha.challenge.domain.models.external;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarModelExternalModel implements Serializable {

    private String version;

    private BigDecimal priceUsd;
}
