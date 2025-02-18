package models;

public class Configuration {
    private final ConfigurationType type;
    private final String name;
    private final double price;

    public Configuration(ConfigurationType type, String name, double price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public ConfigurationType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
