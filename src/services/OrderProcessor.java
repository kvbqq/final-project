package services;

import models.CartItem;
import models.Configuration;
import models.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderProcessor {
    public void processOrder(Order order) {
        OrderPersistance.saveOrderToFile(order);
        generateInvoice(order);
    }

    public void generateInvoice(Order order) {
        try (FileWriter writer = new FileWriter("invoice_" + order.getId() + ".txt")) {
            writer.write(String.format(
                    "Faktura%nID: %d%nImię i Nazwisko: %s%nAdres: %s%nKwota całkowita: %.2f zł%n%nProdukty:%n",
                    order.getId(),
                    order.getUserName() + " " + order.getUserSurname(),
                    order.getUserAddress(),
                    order.getPrice())
            );
            for (CartItem cartItem : order.getCartItems()) {
                writer.write(String.format("%s Cena bazowa: %.2f zł%n", cartItem.getProduct().getName(), cartItem.getProduct().getPrice()));
                if (!cartItem.getConfigurations().isEmpty()) {
                    writer.write("Konfiguracje:\n");
                    for (Configuration configuration : cartItem.getConfigurations()) {
                        writer.write(String.format("%s: %s Dopłata: %.2f zł%n", configuration.getType().getName(), configuration.getName(), configuration.getPrice()));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[Błąd] Nie udało się utworzyć faktury");
        }
    }
}