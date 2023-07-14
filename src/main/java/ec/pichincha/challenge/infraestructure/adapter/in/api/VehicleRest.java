package ec.pichincha.challenge.infraestructure.adapter.in.api;

import ec.pichincha.challenge.domain.port.in.QuoteService;
import ec.pichincha.challenge.domain.port.in.ReportService;
import ec.pichincha.challenge.infraestructure.adapter.in.mapper.GenerateQuoteMapper;
import ec.pichincha.challenge.infraestructure.adapter.in.mapper.GenerateReportMapper;
import ec.pichincha.challenge.infraestructure.adapter.in.mapper.SaveQuoteMapper;
import ec.pichincha.challenge.infraestructure.exception.ApplicationError;
import ec.pichincha.challenge.models.*;
import ec.pichincha.challenge.server.V1ApiDelegate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class VehicleRest implements V1ApiDelegate {

    private final QuoteService quoteService;

    private final ReportService reportService;

    private final GenerateQuoteMapper quoteMapper;

    private final SaveQuoteMapper orderMapper;

    @Override
    public Mono<ResponseEntity<ResponseGenerateQuote>> postGenerateQuote(Mono<RequestGenerateQuote> requestGenerateQuote,
                                                                         ServerWebExchange exchange) {
        return requestGenerateQuote.flatMap(request -> quoteService.getResponseVehicleQuote(quoteMapper.mapToRequestQuoteApiModel(request)))
                .map(quoteMapper::mapToResponseGenerateQuote)
                .map(ResponseEntity::ok)
                ;
    }

    @Override
    public Mono<ResponseEntity<ResponseSaveQuote>> postSaveQuote(Mono<RequestSaveQuote> requestSaveQuote,
                                                                 ServerWebExchange exchange) {
        return requestSaveQuote.flatMap(request -> quoteService.getResponseVehicleOrder(orderMapper.mapToRequestOrderApiModel(request)))
                .map(orderMapper::mapToResponseSaveQuote)
                .map(responseSaveQuote -> ResponseEntity.status(HttpStatus.CREATED).body(responseSaveQuote))
                ;
    }

    @Override
    public Mono<ResponseEntity<ResponseGenerateReport>> postGenerateReport(Mono<RequestGenerateReport> requestGenerateReport,
                                                                           ServerWebExchange exchange) {
        return requestGenerateReport.map(requestGenerateReport1 -> reportService.getResponseGenerateReportApiModelFlux(GenerateReportMapper.INSTANCE.mapToRequestGenerateReportApiModel(requestGenerateReport1)))
                .map(responseGenerateReportApiModelFlux -> responseGenerateReportApiModelFlux
                        .map(responseGenerateReportApiModel -> GenerateReportMapper.INSTANCE.mapToResponseGenerateReportDataInner(responseGenerateReportApiModel))
                        .switchIfEmpty(Mono.error(ApplicationError.NOT_CONTENT)).collectList()
                )
                .flatMap(listMono -> listMono)
                .map(responseGenerateReportDataInners ->
                    {

                        ResponseGenerateReport response = new ResponseGenerateReport();
                        response.data(responseGenerateReportDataInners);
                        return response;
                    })
                .map(ResponseEntity::ok);
    }
}
