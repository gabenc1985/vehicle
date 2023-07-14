package ec.pichincha.challenge.infraestructure.adapter.in.api;

import com.google.gson.Gson;
import ec.pichincha.challenge.models.*;
import ec.pichincha.challenge.util.Constants;
import ec.pichincha.challenge.util.MockData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@SpringBootTest
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VehicleRestIntegrationTest {


    @Autowired
    private WebTestClient webTestClient;


    @Test
    @Order(3)
    void getQuoteAndSaveQuote(){


        RequestGenerateQuote requestGenerateQuote = MockData.getRequestGenerateQuote();
        String bodyValue = new Gson().toJson(requestGenerateQuote).toString();

        WebTestClient.ResponseSpec generateQuote = webTestClient.post().uri("/vehicle/v1/quotes/generate")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestGenerateQuote)
                .exchange()
                .expectStatus().isOk();

        ResponseGenerateQuote responseBody = generateQuote.expectBody(ResponseGenerateQuote.class).returnResult().getResponseBody();


        RequestSaveQuoteData data = new RequestSaveQuoteData();
        data.setConvertionId(responseBody.getData().getConvertionId());
        data.setVersion(responseBody.getData().getVersions().get(0).getVersion());
        data.setModel(responseBody.getData().getVersions().get(0).getModel());
        data.setCryptocurrency(responseBody.getData().getVersions().get(0).getCryptocurrency());
        data.setFullName(Constants.FULL_NAME);
        RequestSaveQuote requestSaveQuote = new RequestSaveQuote();
        requestSaveQuote.setData(data);

        WebTestClient.ResponseSpec saveQuote = webTestClient.post().uri("/vehicle/v1/quotes/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestSaveQuote)
                .exchange()
                .expectStatus().isCreated();

        this.getReports();
        this.getReportsNotFound();
    }


    void getReports() {

        LocalDate date = LocalDate.now();
        String dateString = date.format(DateTimeFormatter.ISO_DATE);
        RequestGenerateReportData data = new RequestGenerateReportData();
        data.model(RequestGenerateReportData.ModelEnum.ACCENT)
                .cryptocurrency(Constants.CRYPTOCURRENCY_BTC)
                .date(dateString);
        RequestGenerateReport requestGenerateReport =new RequestGenerateReport();
        requestGenerateReport.setData(data);


        WebTestClient.ResponseSpec generateQuote = webTestClient.post().uri("/vehicle/v1/reports/generate")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestGenerateReport)
                .exchange()
                .expectStatus().isOk()
                ;

        ResponseGenerateReport responseBody = generateQuote.expectBody(ResponseGenerateReport.class).returnResult().getResponseBody();
        Assertions.assertTrue(responseBody.getData().size()>0);



    }


    void getReportsNotFound() {

        LocalDate date = LocalDate.now();
        String dateString = date.format(DateTimeFormatter.ISO_DATE);
        RequestGenerateReportData data = new RequestGenerateReportData();
        data.model(RequestGenerateReportData.ModelEnum.GRAND_I10)
                .cryptocurrency(Constants.CRYPTOCURRENCY_ETH)
                .date(dateString);
        RequestGenerateReport requestGenerateReport =new RequestGenerateReport();
        requestGenerateReport.setData(data);


        WebTestClient.ResponseSpec generateQuote = webTestClient.post().uri("/vehicle/v1/reports/generate")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestGenerateReport)
                .exchange()
                .expectStatus().isNoContent()
                ;





    }


}