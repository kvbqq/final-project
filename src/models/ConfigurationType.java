package models;

public enum ConfigurationType {
    PROCESSOR("Procesor"),
    RAM("Pamięć RAM"),
    COLOR("Kolor"),
    BATTERY_CAPACITY("Pojemność baterii"),
    SMARTPHONE_ACCESSORY("Akcesoria");

    private final String name;

    ConfigurationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
