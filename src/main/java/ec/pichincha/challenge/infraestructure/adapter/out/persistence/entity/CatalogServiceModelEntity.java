package ec.pichincha.challenge.infraestructure.adapter.out.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("public.catalog_service_model")
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class CatalogServiceModelEntity {

    @Id
    private Long id;

    private String name;

    private String idModel;

    private String idVehicle;
}
