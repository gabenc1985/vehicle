package ec.pichincha.challenge.domain.models.api;

import ec.pichincha.challenge.domain.models.enums.CryptocurrencyEnum;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Generated
public class RequestGenerateReportApiModel implements Serializable {

    private String model;
    private CryptocurrencyEnum cryptocurrency;
    private LocalDate date;

}
