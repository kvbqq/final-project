package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Cart {
    private List<Product> products = new ArrayList<>();

    @Override
    public String toString() {
        return products.stream().map(Product::toString).collect(Collectors.joining("\n"));
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
