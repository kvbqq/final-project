package models;

import java.math.BigDecimal;

public class Configuration {
    private int id;
    private final ConfigurationType type;
    private final String name;
    private final BigDecimal price;

    public Configuration(int id, ConfigurationType type, String name, BigDecimal price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public ConfigurationType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%d | %s | %s | %.2f zł", id, type, name, price);
    }
}
