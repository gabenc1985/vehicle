package ec.pichincha.challenge.infraestructure.adapter.in.mapper;

import ec.pichincha.challenge.domain.models.api.RequestSaveQuoteApiModel;
import ec.pichincha.challenge.domain.models.api.ResponseSaveQuoteApiModel;
import ec.pichincha.challenge.models.RequestSaveQuote;
import ec.pichincha.challenge.models.ResponseSaveQuote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;


@Mapper(
        componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface SaveQuoteMapper {
    SaveQuoteMapper INSTANCE = Mappers.getMapper(SaveQuoteMapper.class);

    @Mapping(source ="data.convertionId",  target= "convertionId")
    @Mapping(source ="data.fullName",  target= "fullName")
    @Mapping(source ="data.version",  target= "version")
    @Mapping(source ="data.model",  target= "model")
    @Mapping(source = "data.cryptocurrency", target = "cryptocurrency")
    RequestSaveQuoteApiModel mapToRequestOrderApiModel(RequestSaveQuote requestSaveQuote);



    @Mapping(target= "data.cryptocurrency", source = "cryptocurrency.description")
    @Mapping(target = "data.date", source = "date")
    @Mapping(target = "data.priceUsd", source = "priceUsd")
    @Mapping(target = "data.purchaseId", source = "purchaseId")
    @Mapping(target = "data.fullName", source = "fullName")
    @Mapping(target = "data.model", source = "model")
    @Mapping(target = "data.priceCryptocurrency", source = "priceCryptocurrency")
    @Mapping(target = "data.version", source = "version")
    ResponseSaveQuote mapToResponseSaveQuote(ResponseSaveQuoteApiModel responseOrderApiModel);

}
