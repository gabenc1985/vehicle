package ec.pichincha.challenge.domain.models.api;

import ec.pichincha.challenge.domain.models.Version;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Generated
public class ResponseGenerateQuoteApiModel implements Serializable {

    private String convertionId;
    private String conversionTimelife;
    private Set<Version> versions;
}
