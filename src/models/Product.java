package models;

import java.util.List;

public class Product {
    private final int id;
    private final String name;
    private final ProductType type;
    private final double price; // TODO bigdecimal
    private final List<Configuration> configurations;
    private final long stock;

    public Product(int id, String name, ProductType type, double price, long stock, List<Configuration> configurations) {
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

    public double getPrice() {
        return price;
    }

    public long getStock() {
        return stock;
    }

    // TODO increaseStock(), decreaseStock()

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    @Override
    public String toString() {
        return String.format("%d | %s | %s | %.2f zł", id, name, type.getName(), price);
    }

//    public Optional<List<Configuration>> configure() {
//        List<Configuration> finalConfiguration = new ArrayList<>();
//
//        Set<ConfigurationType> configurationTypeSet = configurations.stream()
//                .map(Configuration::getType)
//                .collect(Collectors.toSet());
//
//        for (ConfigurationType configurationType : configurationTypeSet) {
//            System.out.println("Konfiguracja elementu - " + configurationType.getName());
//            List<Configuration> configurationList = configurations.stream()
//                    .filter(configuration -> configuration.getType().equals(configurationType))
//                    .toList();
//
//            configurationList.stream()
//                    .map(configuration -> String.format(
//                            "[%d] %s | Dopłata: %.2f zł",
//                            configurationList.indexOf(configuration)+1,
//                            configuration.getName(),
//                            configuration.getPrice()
//                        )
//                    )
//                    .forEach(System.out::println);
//
//            System.out.println("\nWybierz jedną z dostępnych opcji:");
//            // TODO - read single value, read multiple values
//        }
//        return Optional.of(finalConfiguration);
//    }
}
