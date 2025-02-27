package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String userName;
    private final String userSurname;
    private final String userAddress;
    private final List<CartItem> cartItems;
    private final BigDecimal price;
    private final LocalDateTime date;

    public Order(String userName, String userSurname, String userAddress, List<CartItem> cartItems) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userAddress = userAddress;
        this.cartItems = cartItems;
        this.price = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.date = LocalDateTime.now();
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

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("%s %s | %s | %.2f zł", userName, userSurname, userAddress, price);
    }
}
