package ec.pichincha.challenge.domain.port.in;

import ec.pichincha.challenge.domain.models.api.*;
import reactor.core.publisher.Flux;

public interface ReportService {

    Flux<ResponseGenerateReportApiModel> getResponseGenerateReportApiModelFlux(RequestGenerateReportApiModel saveOrder);

}
