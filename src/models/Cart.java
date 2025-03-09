package models;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Klasa reprezentująca koszyk zakupowy użytkownika.
 * Umożliwia dodawanie, usuwanie produktów oraz stosowanie rabatów.
 */
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

    /**
     * Dodaje produkt do koszyka.
     * @param cartItem Obiekt reprezentujący pozycję w koszyku.
     */
    public void addToCart(CartItem cartItem) {
        cartItems.add(cartItem);
    }

    /**
     * Usuwa produkt z koszyka.
     * @param cartItem Obiekt reprezentujący pozycję w koszyku.
     */
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
