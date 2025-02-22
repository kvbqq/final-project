package models;

public enum ConfigurationType {
    PROCESSOR("Procesor"),
    RAM("Pamięć RAM"),
    COLOR("Kolor"),
    BATTERY_CAPACITY("Pojemność baterii"),
    SMARTPHONE_ACCESSORY("Akcesoria");

    // new enum - type of configuration (multiple/single)

    private final String name;

    // configurationType based on enum (Single/Multiple)


    ConfigurationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
