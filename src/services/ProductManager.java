package services;

import models.Configuration;
import models.ConfigurationType;
import models.Product;
import models.ProductType;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductManager {
    private final static List<Product> products = new ArrayList<>();

    public ProductManager() {
        fillDefaultValues();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(int id) {
        Optional<Product> productToRemove = products.stream()
                .filter(product -> product.getId() == id)
                .findAny();

        productToRemove.ifPresent(products::remove);
    }

    public void displayProducts() {
        System.out.println("\nDostępne produkty:");
        products.forEach(System.out::println);
    }

    public Optional<Product> getProductById(int id) {
        return products.stream().
                filter(p -> p.getId() == id)
                .findAny();
    }

    private void fillDefaultValues() {
        List<Configuration> computerConfigurations = new ArrayList<>();
        List<Configuration> smartphoneConfigurations = new ArrayList<>();

        computerConfigurations.add(new Configuration(1, ConfigurationType.PROCESSOR, "Intel i5", new BigDecimal("799.00")));
        computerConfigurations.add(new Configuration(2, ConfigurationType.PROCESSOR, "Intel i7", new BigDecimal("1269.00")));
        computerConfigurations.add(new Configuration(3, ConfigurationType.PROCESSOR, "AMD Ryzen 7", new BigDecimal("699.00")));
        computerConfigurations.add(new Configuration(1, ConfigurationType.RAM, "8GB", BigDecimal.ZERO));
        computerConfigurations.add(new Configuration(2, ConfigurationType.RAM, "16GB", new BigDecimal("159.00")));
        computerConfigurations.add(new Configuration(3, ConfigurationType.RAM, "32GB", new BigDecimal("279.00")));

        smartphoneConfigurations.add(new Configuration(1, ConfigurationType.COLOR, "Czarny", new BigDecimal("49.00")));
        smartphoneConfigurations.add(new Configuration(2, ConfigurationType.COLOR, "Biały", new BigDecimal("49.00")));
        smartphoneConfigurations.add(new Configuration(3, ConfigurationType.COLOR, "Szary", BigDecimal.ZERO));
        smartphoneConfigurations.add(new Configuration(1, ConfigurationType.BATTERY_CAPACITY, "3000mAh", BigDecimal.ZERO));
        smartphoneConfigurations.add(new Configuration(2, ConfigurationType.BATTERY_CAPACITY, "4000mAh", new BigDecimal("50.00")));
        smartphoneConfigurations.add(new Configuration(3, ConfigurationType.BATTERY_CAPACITY, "5000mAh", new BigDecimal("100.00")));
        smartphoneConfigurations.add(new Configuration(1, ConfigurationType.SMARTPHONE_ACCESSORY, "Obudowa", new BigDecimal("59.00")));
        smartphoneConfigurations.add(new Configuration(2, ConfigurationType.SMARTPHONE_ACCESSORY, "Szkło hartowane", new BigDecimal("49.00")));
        smartphoneConfigurations.add(new Configuration(3, ConfigurationType.SMARTPHONE_ACCESSORY, "Ładowarka", new BigDecimal("79.00")));
        smartphoneConfigurations.add(new Configuration(4, ConfigurationType.SMARTPHONE_ACCESSORY, "Słuchawki", new BigDecimal("149.00")));

        addProduct(new Product(1, "Komputer", ProductType.COMPUTER, new BigDecimal("1000.00"), 12, computerConfigurations));
        addProduct(new Product(2, "Smartfon", ProductType.SMARTPHONE, new BigDecimal("800.00"), 5, smartphoneConfigurations));
    }
}
