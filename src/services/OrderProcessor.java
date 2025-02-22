package services;

import models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderProcessor {
    private static List<Order> orders = new ArrayList<>();


    public static void addOrder(Order order) {
        orders.add(order);
    }

    public static void displayOrders() {
        System.out.println("Zamówienia:");
        orders.forEach(System.out::println);
    }

    public static void generateInvoice(Order order) {
        // TODO
    }
}