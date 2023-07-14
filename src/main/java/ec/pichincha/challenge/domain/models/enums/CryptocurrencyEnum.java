package ec.pichincha.challenge.domain.models.enums;

public enum CryptocurrencyEnum {

    BTC("BTC", "90"),
    ETH("ETH", "80");

    private final String description;
    private final String code;

    CryptocurrencyEnum(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }
}
