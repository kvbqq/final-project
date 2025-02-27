package models;

import java.math.BigDecimal;
import java.util.List;

public class Product {
    private final int id;
    private final String name;
    private final ProductType type;
    private final BigDecimal price;
    private final List<Configuration> configurations;
    private final long stock;

    public Product(int id, String name, ProductType type, BigDecimal price, long stock, List<Configuration> configurations) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.stock = stock;
        this.configurations = configurations;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProductType getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getStock() {
        return stock;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    @Override
    public String toString() {
        return String.format("%d | %s | %s | %.2f zł", id, name, type.getName(), price);
    }
}
