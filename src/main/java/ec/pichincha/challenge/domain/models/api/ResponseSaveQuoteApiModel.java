package ec.pichincha.challenge.domain.models.api;

import ec.pichincha.challenge.domain.models.enums.CryptocurrencyEnum;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Generated
public class ResponseSaveQuoteApiModel implements Serializable {

    private CryptocurrencyEnum cryptocurrency;
    private String date;
    private String priceUsd;
    private String purchaseId;
    private String fullName;
    private String model;
    private BigDecimal priceCryptocurrency;
    private String version;

}
