package models;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Cart {
    private final List<CartItem> cartItems = new ArrayList<>();
    private BigDecimal discountPercentage = BigDecimal.ZERO;

    @Override
    public String toString() {
        return cartItems.isEmpty()
                ? "Twój koszyk jest pusty"
                : "Twój koszyk:\n" + cartItems.stream()
                .map(CartItem::toString)
                .collect(Collectors.joining("\n"));
    }

    public void addToCart(CartItem cartItem) {
        cartItems.add(cartItem);
    }

    public void removeFromCart(CartItem cartItem) {
        cartItems.remove(cartItem);
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public BigDecimal getCartPrice() {
        BigDecimal cartItemsPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return discountPercentage.equals(BigDecimal.ZERO)
                ? cartItemsPrice
                : cartItemsPrice.multiply(BigDecimal.ONE.subtract(discountPercentage));
    }
}
