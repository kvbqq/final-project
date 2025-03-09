package services;

import models.CartItem;
import models.Configuration;
import models.Order;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Klasa odpowiedzialna za przetwarzanie zamówień, w tym zapis i generowanie faktur.
 */
public class OrderProcessor {
    public void processOrder(Order order) {
        OrderPersistance.saveOrderToFile(order);
        generateInvoice(order);
    }

    public void generateInvoice(Order order) {
        try (FileWriter writer = new FileWriter("invoice_" + order.getId() + ".txt")) {
            writeInvoiceDetails(order, writer);
            writeCartItems(order, writer);
        } catch (IOException e) {
            System.err.println("[Błąd] Nie udało się utworzyć faktury");
        }
    }

    private void writeInvoiceDetails(Order order, FileWriter writer) throws IOException {
        writer.write(String.format(
                "Faktura%nID: %d%nImię i Nazwisko: %s%nAdres: %s%nKwota całkowita: %.2f zł%n%nProdukty:%n",
                order.getId(),
                order.getUserName() + " " + order.getUserSurname(),
                order.getUserAddress(),
                order.getPrice())
        );
    }

    private void writeCartItems(Order order, FileWriter writer) throws IOException {
        for (CartItem cartItem : order.getCart().getCartItems()) {
            writer.write(String.format("%s Cena bazowa: %.2f zł%n", cartItem.getProduct().getName(), cartItem.getProduct().getPrice()));
            if (!cartItem.getConfigurations().isEmpty()) {
                writeConfigurations(cartItem, writer);
            }
        }
    }

    private void writeConfigurations(CartItem cartItem, FileWriter writer) throws IOException {
        writer.write("Konfiguracje:\n");
        for (Configuration configuration : cartItem.getConfigurations()) {
            writer.write(String.format("%s: %s Dopłata: %.2f zł%n", configuration.getType().getName(), configuration.getName(), configuration.getPrice()));
        }
    }
}