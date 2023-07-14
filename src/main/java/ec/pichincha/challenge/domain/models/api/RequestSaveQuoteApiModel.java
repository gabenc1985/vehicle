package ec.pichincha.challenge.domain.models.api;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Generated
public class RequestSaveQuoteApiModel implements Serializable {

    private String convertionId;
    private String fullName;
    private String version;
    private String model;
    private String cryptocurrency;
}
