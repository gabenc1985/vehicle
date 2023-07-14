package ec.pichincha.challenge.domain.port.in;

import ec.pichincha.challenge.domain.models.api.RequestGenerateQuoteApiModel;
import ec.pichincha.challenge.domain.models.api.RequestSaveQuoteApiModel;
import ec.pichincha.challenge.domain.models.api.ResponseGenerateQuoteApiModel;
import ec.pichincha.challenge.domain.models.api.ResponseSaveQuoteApiModel;
import reactor.core.publisher.Mono;

public interface QuoteService {

    Mono<ResponseGenerateQuoteApiModel> getResponseVehicleQuote(RequestGenerateQuoteApiModel requestGenerateQuoteApiModel);

    Mono<ResponseSaveQuoteApiModel> getResponseVehicleOrder(RequestSaveQuoteApiModel requestSaveQuoteApiModel);

}
