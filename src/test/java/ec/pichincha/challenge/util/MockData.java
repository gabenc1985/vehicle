package ec.pichincha.challenge.util;

import com.google.gson.Gson;
import ec.pichincha.challenge.domain.models.Version;
import ec.pichincha.challenge.domain.models.api.*;
import ec.pichincha.challenge.domain.models.cache.QuoteCacheModel;
import ec.pichincha.challenge.domain.models.enums.CryptocurrencyEnum;
import ec.pichincha.challenge.domain.models.external.CarModelExternalModel;
import ec.pichincha.challenge.domain.models.external.CryptocurrencyExternalModel;
import ec.pichincha.challenge.domain.models.persistence.CarPersistenceModel;
import ec.pichincha.challenge.domain.models.persistence.QuotePersistenceModel;
import ec.pichincha.challenge.infraestructure.adapter.out.cache.entity.QuoteRedis;
import ec.pichincha.challenge.infraestructure.adapter.out.cache.entity.VersionRedis;
import ec.pichincha.challenge.models.RequestGenerateQuote;
import ec.pichincha.challenge.models.RequestGenerateReport;
import ec.pichincha.challenge.models.RequestSaveQuote;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class MockData {

    public static CarPersistenceModel getCarPersistenceModel() {
        CarPersistenceModel carPersistenceModel = new CarPersistenceModel();
        carPersistenceModel.setIdModel(Constants.ACCENT_MODEL);
        carPersistenceModel.setIdVehicle(Constants.ID_VERSION);
        carPersistenceModel.setVersion(Constants.VERSION_ACCENT_1);
        carPersistenceModel.setPriceUsd(new BigDecimal("100000"));
        return carPersistenceModel;

    }

    public static CarModelExternalModel getCarModelExternalModel1() {
        CarModelExternalModel carModelExternalModel = new CarModelExternalModel();
        carModelExternalModel.setPriceUsd(Constants.PRICE_VERSION_ACCENT_1);
        carModelExternalModel.setVersion(Constants.VERSION_ACCENT_1);

        return carModelExternalModel;
    }

    public static CarModelExternalModel getCarModelExternalModel2() {
        CarModelExternalModel carModelExternalModel2 = new CarModelExternalModel();
        carModelExternalModel2.setPriceUsd(Constants.PRICE_VERSION_ACCENT_2);
        carModelExternalModel2.setVersion(Constants.VERSION_ACCENT_2);

        return carModelExternalModel2;
    }

    public static List<CarModelExternalModel> getCarModelExternalModel() {
        return Arrays.asList(getCarModelExternalModel1(), getCarModelExternalModel2());
    }

    public static RequestGenerateQuoteApiModel getRequestGenerateQuoteApiModel() {
        RequestGenerateQuoteApiModel request = new RequestGenerateQuoteApiModel();
        request.setModel(Constants.ACCENT_MODEL);
        request.setCryptocurrency(CryptocurrencyEnum.BTC);
        return request;
    }

    public static CryptocurrencyExternalModel getCryptocurrencyExternalModel() {
        CryptocurrencyExternalModel cryptocurrencyExternalModel = new CryptocurrencyExternalModel();
        cryptocurrencyExternalModel.setCode(Constants.CRYPTOCURRENCY_BTC);
        cryptocurrencyExternalModel.setPriceUsd(Constants.PRICE_CRYPTOCURRENCY);
        return cryptocurrencyExternalModel;
    }

    public static QuoteCacheModel getQuoteCacheModel() {
        QuoteCacheModel quoteCacheModel = new QuoteCacheModel();
        quoteCacheModel.setConvertionId(Constants.CONVERTION_ID);
        quoteCacheModel.setConversionTimelife(Constants.CONVERTION_TTL);
        quoteCacheModel.setVersions(getVersions());
        return quoteCacheModel;
    }

    private static Set<Version> getVersions() {
        Version version = new Version();
        version.setVersion(Constants.VERSION_ACCENT_1);
        version.setModel(Constants.ACCENT_MODEL);
        version.setPriceCryptocurrency(Constants.PRICE_CRYPTOCURRENCY);
        version.setCryptocurrency(Constants.CRYPTOCURRENCY_BTC);
        version.setPriceUsd(Constants.PRICE_VERSION_ACCENT_1);
        Version version2 = new Version();
        version2.setVersion(Constants.VERSION_ACCENT_2);
        version2.setModel(Constants.ACCENT_MODEL);
        version2.setPriceCryptocurrency(Constants.PRICE_CRYPTOCURRENCY);
        version2.setCryptocurrency(Constants.CRYPTOCURRENCY_BTC);
        version2.setPriceUsd(Constants.PRICE_VERSION_ACCENT_2);
        return Set.of(version, version2);
    }

    private static Set<VersionRedis> getVersionRedis() {
        VersionRedis versionRedis = new VersionRedis();
        versionRedis.setVersion(Constants.VERSION_ACCENT_1);
        versionRedis.setModel(Constants.ACCENT_MODEL);
        versionRedis.setPriceCryptocurrency(Constants.PRICE_CRYPTOCURRENCY);
        versionRedis.setCryptocurrency(Constants.CRYPTOCURRENCY_BTC);
        versionRedis.setPriceUsd(Constants.PRICE_VERSION_ACCENT_1);
        VersionRedis version2 = new VersionRedis();
        version2.setVersion(Constants.VERSION_ACCENT_2);
        version2.setModel(Constants.ACCENT_MODEL);
        version2.setPriceCryptocurrency(Constants.PRICE_CRYPTOCURRENCY);
        version2.setCryptocurrency(Constants.CRYPTOCURRENCY_BTC);
        version2.setPriceUsd(Constants.PRICE_VERSION_ACCENT_2);
        return Set.of(versionRedis, version2);
    }

    public static QuotePersistenceModel getQuotePersistenceModel() {
        QuotePersistenceModel quotePersistenceModel = new QuotePersistenceModel();
        quotePersistenceModel.setModel(Constants.ACCENT_MODEL);
        quotePersistenceModel.setFullName(Constants.FULL_NAME);
        quotePersistenceModel.setCryptocurrency(Constants.CRYPTOCURRENCY_BTC);
        quotePersistenceModel.setCreateDate(new Date());
        quotePersistenceModel.setId(1);
        quotePersistenceModel.setPurchaseId(UUID.randomUUID().toString());
        quotePersistenceModel.setVersion(Constants.VERSION_ACCENT_1);
        quotePersistenceModel.setPriceCryptocurrency(Constants.PRICE_CRYPTOCURRENCY);
        quotePersistenceModel.setPriceUsd(Constants.PRICE_VERSION_ACCENT_1);
        return quotePersistenceModel;
    }

    public static QuotePersistenceModel getQuotePersistenceModel(String model, String version, BigDecimal priceCryptocurrency, BigDecimal priceUsd) {
        QuotePersistenceModel quote = new QuotePersistenceModel();
        quote.setModel(model);
        quote.setVersion(version);
        quote.setFullName(Constants.FULL_NAME);
        quote.setCryptocurrency(Constants.CRYPTOCURRENCY_BTC);
        quote.setCreateDate(new Date());
        quote.setId(1);
        quote.setPurchaseId(UUID.randomUUID().toString());
        quote.setPriceCryptocurrency(priceCryptocurrency);
        quote.setPriceUsd(priceUsd);
        return quote;
    }

    public static RequestSaveQuoteApiModel getRequestSaveQuoteApiModel() {
        RequestSaveQuoteApiModel requestSaveQuoteApiModel = new RequestSaveQuoteApiModel();
        requestSaveQuoteApiModel.setModel(Constants.ACCENT_MODEL);
        requestSaveQuoteApiModel.setVersion(Constants.VERSION_ACCENT_1);
        requestSaveQuoteApiModel.setCryptocurrency(Constants.CRYPTOCURRENCY_BTC);
        requestSaveQuoteApiModel.setConvertionId(UUID.randomUUID().toString());
        requestSaveQuoteApiModel.setFullName(Constants.FULL_NAME);
        return requestSaveQuoteApiModel;
    }

    public static RequestGenerateReportApiModel getRequestGenerateReportApiModel() {
        RequestGenerateReportApiModel requestGenerateReportApiModel = new RequestGenerateReportApiModel();
        requestGenerateReportApiModel.setCryptocurrency(CryptocurrencyEnum.BTC);
        requestGenerateReportApiModel.setModel(Constants.ACCENT_MODEL);
        requestGenerateReportApiModel.setDate(LocalDate.now());
        return requestGenerateReportApiModel;
    }


    public static ResponseGenerateQuoteApiModel getResponseGenerateQuoteApiModel() {
        ResponseGenerateQuoteApiModel responseGenerateQuoteApiModel = new ResponseGenerateQuoteApiModel();
        responseGenerateQuoteApiModel.setConversionTimelife(Constants.CONVERTION_TTL);
        responseGenerateQuoteApiModel.setVersions(getVersions());
        responseGenerateQuoteApiModel.setConvertionId(UUID.randomUUID().toString());
        return responseGenerateQuoteApiModel;
    }

    public static ResponseSaveQuoteApiModel getResponseSaveQuoteApiModel() {
        ResponseSaveQuoteApiModel responseSaveQuoteApiModel = new ResponseSaveQuoteApiModel();
        responseSaveQuoteApiModel.setModel(Constants.ACCENT_MODEL);
        responseSaveQuoteApiModel.setCryptocurrency(CryptocurrencyEnum.BTC);
        responseSaveQuoteApiModel.setDate("2023-12-05");
        responseSaveQuoteApiModel.setVersion(Constants.VERSION_ACCENT_1);
        responseSaveQuoteApiModel.setFullName(Constants.FULL_NAME);
        responseSaveQuoteApiModel.setPriceCryptocurrency(Constants.PRICE_CRYPTOCURRENCY);
        responseSaveQuoteApiModel.setPriceUsd(Constants.PRICE_VERSION_ACCENT_1.toString());
        responseSaveQuoteApiModel.setPurchaseId(UUID.randomUUID().toString());
        return responseSaveQuoteApiModel;
    }

    public static RequestGenerateQuote getRequestGenerateQuote() {
        return new Gson().fromJson("{'data': {'model': 'ACCENT', 'cryptocurrency': 'BTC'} }", RequestGenerateQuote.class);
    }

    public static RequestSaveQuote getRequestSaveQuote() {
        return new Gson().fromJson("{'data': {'cryptocurrency': 'ETH', 'convertionId': '43bc3bea-0b44-4700-8b7a-fbd5b2ad8c28', 'fullName': 'demostracion', 'model': 'ACCENT', 'version': 'ALL NEW ACCENT GL'} }", RequestSaveQuote.class);
    }

    public static RequestGenerateReport getRequestGenerateReport() {
        return new Gson().fromJson("{'data': {'date': '2023-07-12', 'model': 'ACCENT', 'cryptocurrency': 'ETH'} }", RequestGenerateReport.class);
    }


    public static ResponseGenerateReportApiModel getResponseGenerateReportApiModel(){
        ResponseGenerateReportApiModel responseGenerateReportApiModel = new ResponseGenerateReportApiModel();
        responseGenerateReportApiModel.setModel(Constants.ACCENT_MODEL);
        responseGenerateReportApiModel.setCryptocurrency(Constants.CRYPTOCURRENCY_BTC);
        responseGenerateReportApiModel.setDate(LocalDate.now());
        responseGenerateReportApiModel.setVersion(Constants.VERSION_ACCENT_1);
        responseGenerateReportApiModel.setUsdAmount(Constants.PRICE_VERSION_ACCENT_1);
        responseGenerateReportApiModel.setCryptocurrencyAmount(Constants.PRICE_CRYPTOCURRENCY);
        return responseGenerateReportApiModel;
    }


    public static QuoteRedis getQuoteRedis(){
        QuoteRedis quoteRedis = new QuoteRedis();
        quoteRedis.setConvertionId(UUID.randomUUID().toString());
        quoteRedis.setVersions(getVersionRedis());
        quoteRedis.setConversionTimelife(Constants.CONVERTION_TTL);
        return quoteRedis;
    }
}
