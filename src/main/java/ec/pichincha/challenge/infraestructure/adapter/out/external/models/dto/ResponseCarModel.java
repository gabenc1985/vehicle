package ec.pichincha.challenge.infraestructure.adapter.out.external.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseCarModel implements Serializable {

    @JsonProperty(value = "VER_CODIGO")
    private Integer code;

    @JsonProperty("VER_NOMBRE")
    private String name;

    @JsonProperty("VEA_ANIO")
    private Integer anio;

    @JsonProperty("VEA_PRECIO_PVP")
    private BigDecimal pricePvp;

    @JsonProperty("VEA_BONO")
    private BigDecimal bono;

    @JsonProperty("VEA_PRECIO_FINAL")
    private BigDecimal priceUsd;

    @JsonProperty("VEA_DISCAPACIDAD_100")
    private Integer disabilityPercentage;

    @JsonProperty("VER_COD_SGC")
    private String codeSgc;

}
