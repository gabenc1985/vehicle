package ec.pichincha.challenge.domain.models.persistence;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarPersistenceModel implements Serializable {

    private String version;

    private String idModel;

    private String idVehicle;

    private BigDecimal priceUsd;
}
