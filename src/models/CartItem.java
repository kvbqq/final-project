package models;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CartItem {
    private final int id;
    private final Product product;
    private final List<Configuration> configurations;
    private final BigDecimal price;

    public CartItem(int id, Product product, List<Configuration> configurations) {
        this.id = id;
        this.product = product;
        this.configurations = configurations;
        this.price = product.getPrice().add(configurations.stream()
                .map(Configuration::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("""
                %s
                Konfiguracje:
                %s
                
                Kwota całkowita: %.2f zł
                """,
                product,
                configurations.stream()
                        .map(Configuration::toString)
                        .collect(Collectors.joining("\n")),
                price
                );
    }
}
