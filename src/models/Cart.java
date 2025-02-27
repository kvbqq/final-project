package models;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Cart {
    private final List<CartItem> cartItems = new ArrayList<>();

    @Override
    public String toString() {
        if (cartItems.isEmpty())
            return "Twój koszyk jest pusty";
        return "Twój koszyk:\n" + cartItems.stream()
                .map(CartItem::toString)
                .collect(Collectors.joining("\n"));
    }

    public void addToCart(CartItem cartItem) {
        cartItems.add(cartItem);
    }

    public void removeFromCart(CartItem cartItem) {
        cartItems.remove(cartItem);
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public BigDecimal getCartPrice() {
        return cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
