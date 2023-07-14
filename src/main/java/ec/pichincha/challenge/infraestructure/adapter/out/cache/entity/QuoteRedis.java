package ec.pichincha.challenge.infraestructure.adapter.out.cache.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuoteRedis implements Serializable {
    private String convertionId;
    private String conversionTimelife;
    private Set<VersionRedis> versions;
}
