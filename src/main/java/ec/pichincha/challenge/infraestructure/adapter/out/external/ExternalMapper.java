package ec.pichincha.challenge.infraestructure.adapter.out.external;


import ec.pichincha.challenge.domain.models.external.CarModelExternalModel;
import ec.pichincha.challenge.infraestructure.adapter.out.external.models.dto.ResponseCarModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface ExternalMapper {
    ExternalMapper INSTANCE = Mappers.getMapper(ExternalMapper.class);
    @Mappings({
            @Mapping(source ="name",  target= "version")
    })
    CarModelExternalModel mapToCarModelExternal(ResponseCarModel responseCarModel);

}
