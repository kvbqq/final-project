package models;

public enum ConfigurationType {
    PROCESSOR("Procesor", false),
    RAM("Pamięć RAM", false),
    COLOR("Kolor", false),
    BATTERY_CAPACITY("Pojemność baterii", false),
    SMARTPHONE_ACCESSORY("Akcesoria", true);

    private final String name;
    private final boolean multipleChoices;

    private ConfigurationType(String name, boolean multipleChoices) {
        this.name = name;
        this.multipleChoices = multipleChoices;
    }

    public String getName() {
        return name;
    }

    public boolean isMultipleChoices() {
        return multipleChoices;
    }
}
