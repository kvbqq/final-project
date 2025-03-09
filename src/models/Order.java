package models;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Klasa reprezentująca zamówienie użytkownika.
 * Przechowuje informacje o użytkowniku, koszyku oraz dacie zamówienia.
 */
public class Order {
    private final int id;
    private final String userName;
    private final String userSurname;
    private final String userAddress;
    private final Cart cart;
    private final BigDecimal price;
    private final ZonedDateTime date;

    public Order(int id, String userName, String userSurname, String userAddress, Cart cart) {
        this.id = id;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userAddress = userAddress;
        this.cart = cart;
        this.price = cart.getCartPrice();
        this.date = ZonedDateTime.now();
    }

    public int getId() {
        return id;
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

    public Cart getCart() {
        return cart;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("%s %s | %s | %.2f zł", userName, userSurname, userAddress, price);
    }
}
