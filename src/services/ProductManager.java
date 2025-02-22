package services;

import models.Configuration;
import models.ConfigurationType;
import models.Product;
import models.ProductType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductManager {
//    private static List<Configuration> computerConfigurations = new ArrayList<>();
//    private static List<Configuration> smartphoneConfigurations = new ArrayList<>();
//    private static List<Configuration> configurations = new ArrayList<>();
    private static List<Product> products = new ArrayList<>();

    public ProductManager() {
        fillDefaultValues();
    }

    public static List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(int id) {
        Optional<Product> productToRemove = products.stream()
                        .filter(product -> product.getId() == id).findAny();

        productToRemove.ifPresent(product -> products.remove(product));

    }

    public void displayProducts() {
        System.out.println("Dostępne produkty:");
        products.forEach(System.out::println);
    }

    private void fillDefaultValues() {
        List<Configuration> computerConfigurations = new ArrayList<>();
        List<Configuration> smartphoneConfigurations = new ArrayList<>();

        computerConfigurations.add(new Configuration(ConfigurationType.PROCESSOR, "Intel i5", 799.00));
        computerConfigurations.add(new Configuration(ConfigurationType.PROCESSOR, "Intel i7", 1269.00));
        computerConfigurations.add(new Configuration(ConfigurationType.PROCESSOR, "AMD Ryzen 7", 699.00));
        computerConfigurations.add(new Configuration(ConfigurationType.RAM, "8GB", 0));
        computerConfigurations.add(new Configuration(ConfigurationType.RAM, "16GB", 159.00));
        computerConfigurations.add(new Configuration(ConfigurationType.RAM, "32GB", 279.00));

        smartphoneConfigurations.add(new Configuration(ConfigurationType.COLOR, "Czarny", 49.00));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.COLOR, "Biały", 49.00));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.COLOR, "Szary", 0));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.BATTERY_CAPACITY, "3000mAh", 0));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.BATTERY_CAPACITY, "4000mAh", 50.00));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.BATTERY_CAPACITY, "5000mAh", 100.00));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.SMARTPHONE_ACCESSORY, "Obudowa", 59.00));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.SMARTPHONE_ACCESSORY, "Szkło hartowane", 49.00));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.SMARTPHONE_ACCESSORY, "Ładowarka", 79.00));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.SMARTPHONE_ACCESSORY, "Słuchawki", 149.00));

        addProduct(new Product(1, "Komputer", ProductType.COMPUTER, 1000.00, 12, computerConfigurations));
        addProduct(new Product(2, "Smartfon", ProductType.SMARTPHONE, 800.00, 5, smartphoneConfigurations));
    }
}
