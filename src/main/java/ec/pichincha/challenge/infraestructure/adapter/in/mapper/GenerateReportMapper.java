package ec.pichincha.challenge.infraestructure.adapter.in.mapper;

import ec.pichincha.challenge.domain.CommonMappingConfig;
import ec.pichincha.challenge.domain.models.api.RequestGenerateReportApiModel;
import ec.pichincha.challenge.domain.models.api.ResponseGenerateReportApiModel;
import ec.pichincha.challenge.domain.models.enums.CryptocurrencyEnum;
import ec.pichincha.challenge.infraestructure.exception.ApplicationException;
import ec.pichincha.challenge.models.*;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;


@org.mapstruct.Mapper(
        componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)

public interface GenerateReportMapper extends CommonMappingConfig {
    GenerateReportMapper INSTANCE = Mappers.getMapper(GenerateReportMapper.class);
    @Mappings({
            @Mapping(source = "data.model", target = "model"),
            @Mapping(source = "data.date", target = "date"),
            @Mapping(source = "data.cryptocurrency", target = "cryptocurrency")
    })
    RequestGenerateReportApiModel mapToRequestGenerateReportApiModel(RequestGenerateReport requestGenerateReport);

    default CryptocurrencyEnum map(String sourceEnum) throws ApplicationException {
        return Arrays.stream(CryptocurrencyEnum.values())
                .filter(cryptocurrencyEnum -> cryptocurrencyEnum.getDescription().equals(sourceEnum))
                .findFirst()
                .orElseThrow(()->
                        new ApplicationException("400", "Cryptocurrency not found", HttpStatus.BAD_REQUEST));

    }


    default BigDecimal roundBigDecimal(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    @Mappings({
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "model", target = "model"),
            @Mapping(source = "cryptocurrency", target = "cryptocurrency"),
            @Mapping(source = "usdAmount", target = "usdAmount"),
            @Mapping(source = "cryptocurrencyAmount", target = "cryptocurrencyAmount")
    })
    ResponseGenerateReportDataInner mapToResponseGenerateReportDataInner(ResponseGenerateReportApiModel responseGenerateReportApiModel);

}
