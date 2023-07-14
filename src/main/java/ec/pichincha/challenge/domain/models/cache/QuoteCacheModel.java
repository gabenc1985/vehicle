package ec.pichincha.challenge.domain.models.cache;

import ec.pichincha.challenge.domain.models.Version;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuoteCacheModel implements Serializable {

    private String convertionId;
    private String conversionTimelife;
    private Set<Version> versions;
}
