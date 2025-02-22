package models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String userName;
    private final String userSurname;
    private final String userAddress;
    private final List<Product> products;
    private final double price;

    public Order(String userName, String userSurname, String userAddress, List<Product> products) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userAddress = userAddress;
        this.products = products;
        this.price = products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getPrice() {
        return price;
    }
}
