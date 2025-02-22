package models;

import java.util.*;
import java.util.stream.Collectors;

public class Cart {
    private List<Product> products = new ArrayList<>();

//    class CartItem - Product product (referencja) List<Configuration> (wybrane przez użytkownika)

    @Override
    public String toString() {
        return products.stream()
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
    }

    public void addToCart(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void makeOrder() {
        // TODO
    }

}
