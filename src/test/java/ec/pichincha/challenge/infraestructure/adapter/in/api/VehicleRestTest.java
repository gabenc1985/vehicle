package ec.pichincha.challenge.infraestructure.adapter.in.api;

import ec.pichincha.challenge.domain.port.in.QuoteService;
import ec.pichincha.challenge.domain.port.in.ReportService;
import ec.pichincha.challenge.infraestructure.adapter.in.mapper.GenerateQuoteMapper;
import ec.pichincha.challenge.infraestructure.adapter.in.mapper.SaveQuoteMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static ec.pichincha.challenge.util.MockData.*;

@SpringBootTest
class VehicleRestTest {

    @Mock
    QuoteService quoteService;

    @Mock
    ReportService reportService;

    @InjectMocks
    VehicleRest vehicleRest;


    SaveQuoteMapper orderMapper = SaveQuoteMapper.INSTANCE;


    GenerateQuoteMapper quoteMapper = GenerateQuoteMapper.INSTANCE;

    MockServerWebExchange exchange ;
    @BeforeEach
    void init(){
        MockServerHttpRequest request = MockServerHttpRequest.get("/path")
                .header("Authorization", "Bearer token")
                .build();
        exchange = MockServerWebExchange.from(request);
        ReflectionTestUtils.setField(vehicleRest, "orderMapper", orderMapper);
        ReflectionTestUtils.setField(vehicleRest, "quoteMapper", quoteMapper);
    }

    @Test
    void postSaveQuote() {
        Mockito.when(quoteService.getResponseVehicleOrder(Mockito.any()))
                .thenReturn(Mono.just(getResponseSaveQuoteApiModel()));

        StepVerifier.create(vehicleRest.postSaveQuote(Mono.just(getRequestSaveQuote()), exchange))
                .consumeNextWith(responseGenerateQuoteResponseEntity -> Assertions.assertNotNull(responseGenerateQuoteResponseEntity))
                .verifyComplete();

        Mockito.verify(quoteService, Mockito.times(1)).getResponseVehicleOrder(Mockito.any());
    }

    @Test
    void postGenerateQuote() {
        Mockito.when(quoteService.getResponseVehicleQuote(Mockito.any()))
                .thenReturn(Mono.just(getResponseGenerateQuoteApiModel()));

        StepVerifier.create(vehicleRest.postGenerateQuote(Mono.just(getRequestGenerateQuote()), exchange))
                .consumeNextWith(responseGenerateQuoteResponseEntity -> Assertions.assertNotNull(responseGenerateQuoteResponseEntity))
                .verifyComplete();

        Mockito.verify(quoteService, Mockito.times(1)).getResponseVehicleQuote(Mockito.any());
    }

    @Test
    void postGenerateReport() {

        Mockito.when(reportService.getResponseGenerateReportApiModelFlux(Mockito.any()))
                .thenReturn(Flux.just(getResponseGenerateReportApiModel()));
        StepVerifier.create(vehicleRest.postGenerateReport(Mono.just(getRequestGenerateReport()), exchange))
                .consumeNextWith(responseGenerateQuoteResponseEntity -> Assertions.assertNotNull(responseGenerateQuoteResponseEntity))
                .verifyComplete();

        Mockito.verify(reportService, Mockito.times(1)).getResponseGenerateReportApiModelFlux(Mockito.any());
    }

}