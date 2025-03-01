package services;

import models.CartItem;
import models.Configuration;
import models.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class OrderPersistance {
    private static final String FILE_NAME = "orders.txt";

    public static void saveOrderToFile(Order order) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(String.format(
                    "ID: %d%nImię i nazwisko: %s%nAdres: %s%nData zamówienia: %s%n%nProdukty:%n",
                    order.getId(),
                    order.getUserName() + " " + order.getUserSurname(),
                    order.getUserAddress(),
                    order.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
                    )
            );
            for (CartItem cartItem : order.getCartItems()) {
                writer.write(cartItem.getProduct().getName() + "\n");
                if (!cartItem.getConfigurations().isEmpty()) {
                    writer.write("Konfiguracje:\n");
                    for (Configuration configuration : cartItem.getConfigurations()) {
                        writer.write(String.format("%s: %s%n", configuration.getType().getName(), configuration.getName()));
                    }
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.err.println("\n[Błąd] Nie udało się zapisać zamówienia do pliku");
        }
    }
}
