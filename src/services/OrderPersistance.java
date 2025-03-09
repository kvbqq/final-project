package services;

import models.CartItem;
import models.Configuration;
import models.Order;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Klasa odpowiedzialna za zapis zamówień do pliku tekstowego.
 */
public class OrderPersistance {
    private static final String FILE_NAME = "orders.txt";

    public static void saveOrderToFile(Order order) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writeOrderDetails(order, writer);
            writeCartItems(order, writer);
        } catch (IOException e) {
            System.err.println("\n[Błąd] Nie udało się zapisać zamówienia do pliku");
        }
    }

    private static void writeOrderDetails(Order order, FileWriter writer) throws IOException {
        writer.write(String.format(
                "ID: %d%nImię i nazwisko: %s%nAdres: %s%nData zamówienia: %s%n%nProdukty:%n",
                order.getId(),
                order.getUserName() + " " + order.getUserSurname(),
                order.getUserAddress(),
                order.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm z")))
        );
    }

    private static void writeCartItems(Order order, FileWriter writer) throws IOException {
        for (CartItem cartItem : order.getCart().getCartItems()) {
            writer.write(cartItem.getProduct().getName() + "\n");
            if (!cartItem.getConfigurations().isEmpty()) {
                writeConfigurations(cartItem, writer);
            }
            writer.write("\n");
        }
    }

    private static void writeConfigurations(CartItem cartItem, FileWriter writer) throws IOException {
        writer.write("Konfiguracje:\n");
        for (Configuration configuration : cartItem.getConfigurations()) {
            writer.write(String.format("%s: %s%n", configuration.getType().getName(), configuration.getName()));
        }
    }
}
