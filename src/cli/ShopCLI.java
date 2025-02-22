package cli;

import models.Cart;
import services.ProductManager;

import java.util.Scanner;

public class ShopCLI {
    static String menu = """
            Menu:
            [1] Wyświetl dostępne produkty
            [2] Dodaj produkt do koszyka
            [3] Wyświetl swój koszyk
            [4] Złóż zamówienie
            [5] Wyjdź
            
            Wybierz jedną z dostępnych opcji:
            """;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        ProductManager productManager = new ProductManager();
        Cart cart = new Cart();
        int userInput;

        do {
            System.out.print(menu);
            userInput = scanner.nextInt();
            switch (userInput) {
                case 1 -> productManager.displayProducts();
                case 2 -> System.out.println("2");
                case 3 -> System.out.println(cart);
                case 4 -> System.out.println("4");
                case 5 -> System.out.println("Opuszczanie sklepu...");
            }
        } while(userInput != 5);
    }
}
