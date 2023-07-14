package ec.pichincha.challenge.domain.models.api;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Generated
public class ResponseGenerateReportApiModel implements Serializable {


    private String model;
    private String version;
    private String cryptocurrency;
    private BigDecimal usdAmount;
    private BigDecimal cryptocurrencyAmount;
    private LocalDate date;
}
