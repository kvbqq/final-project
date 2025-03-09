package models;

import java.math.BigDecimal;

/**
 * Klasa reprezentująca rabat dostępny w systemie.
 * Przechowuje kod rabatowy oraz jego wartość procentową.
 */
public class Discount {
    private final String code;
    private final BigDecimal percentage;

    public Discount(String code, BigDecimal percentage) {
        this.code = code;
        this.percentage = percentage;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }
}
