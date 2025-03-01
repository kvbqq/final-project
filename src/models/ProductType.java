package models;

public enum ProductType {
    COMPUTER("Komputer"),
    SMARTPHONE("Smartfon"),
    ELECTRONICS("Elektronika");

    private final String name;

    ProductType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
