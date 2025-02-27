package cli;

import models.*;
import services.OrderProcessor;
import services.ProductManager;
import java.util.*;
import java.util.stream.Collectors;

public class ShopCLI {
    ProductManager productManager = new ProductManager();
    OrderProcessor orderProcessor = new OrderProcessor();
    Cart cart = new Cart();
    Scanner scanner = new Scanner(System.in);
    public void menu() {
        int userInput;

        do {
            System.out.println("""
                     
                    Menu:
                    [1] Wyświetl dostępne produkty
                    [2] Dodaj produkt do koszyka
                    [3] Usuń produkt z koszyka
                    [4] Wyświetl swój koszyk
                    [5] Złóż zamówienie
                    [6] Wyjdź
                    
                    Wybierz jedną z dostępnych opcji:""");
            userInput = scanner.nextInt();
            scanner.nextLine();
            switch (userInput) {
                case 1 -> productManager.displayProducts();
                case 2 -> addProductToCart();
                case 3 -> removeProductFromCart();
                case 4 -> System.out.println(cart);
                case 5 -> makeOrder();
                case 6 -> System.out.println("\nOpuszczanie sklepu...");
                default -> System.out.println("Błędny wybór");
            }
        } while(userInput != 6);
    }

    private void addProductToCart() {
        List<Configuration> finalConfiguration = new ArrayList<>();
        List<Configuration> configurations;
        Set<ConfigurationType> configurationTypeSet;

        System.out.println("\nPodaj id produktu:");

        Optional<Product> product = productManager.getProductById(scanner.nextInt());
        scanner.nextLine();

        if (product.isPresent()) {
            configurations = product.get().getConfigurations();
            configurationTypeSet = configurations.stream()
                    .map(Configuration::getType)
                    .collect(Collectors.toSet());

            for (ConfigurationType configurationType : configurationTypeSet) {
                List<Configuration> filteredConfigurations = configurations.stream()
                        .filter(configuration -> configuration.getType().equals(configurationType))
                        .toList();

                System.out.println("\nKonfiguracja elementu - " + configurationType.getName());
                filteredConfigurations.stream()
                        .map(configuration -> String.format(
                                        "[%d] %s | Dopłata: %.2f zł",
                                        configuration.getId(),
                                        configuration.getName(),
                                        configuration.getPrice()
                                )
                        )
                        .forEach(System.out::println);

                if (configurationType.isMultipleChoices()) {
                    System.out.println("\nWybierz wiele dostępnych opcji:");
                    String multipleChoiceInput = scanner.nextLine();
                    List<String> chosenOptions = Arrays.stream(multipleChoiceInput.split(",")).toList();
                    for (String chosenOption : chosenOptions) {
                        Optional<Configuration> chosenConfiguration = getConfigurationById(filteredConfigurations, Integer.parseInt(chosenOption.trim()));
                        chosenConfiguration.ifPresent(finalConfiguration::add);
                    }
                } else {
                    System.out.println("\nWybierz jedną z dostępnych opcji:");
                    int singleChoiceInput = scanner.nextInt();
                    scanner.nextLine();
                    Optional<Configuration> chosenConfiguration = getConfigurationById(filteredConfigurations, singleChoiceInput);
                    chosenConfiguration.ifPresent(finalConfiguration::add);
                }
            }
            cart.addToCart(new CartItem(product.get().getId(), product.get(), finalConfiguration));
        }
    }

    private void removeProductFromCart() {
        if (cart.getCartItems().isEmpty())
            System.out.println("\nTwój koszyk jest pusty");
        else {
            System.out.println("\nTwój koszyk:\n" + cart);
            System.out.println("\nPodaj id produktu, który chcesz usunąć");
            Optional<CartItem> cartItemToRemove = cart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getProduct().getId() == scanner.nextInt())
                    .findAny();
            cartItemToRemove.ifPresent(cartItem -> cart.removeFromCart(cartItem));
        }
    }

    private void makeOrder() {
        if (cart.getCartItems().isEmpty())
            System.out.println("\nTwój koszyk jest pusty");
        else {
            System.out.println("\nPodaj imię:");
            String userName = scanner.nextLine();

            System.out.println("\nPodaj nazwisko:");
            String userSurname = scanner.nextLine();

            System.out.println("\nPodaj email:");
            String userEmail = scanner.nextLine();

            Order order = new Order(userName, userSurname, userEmail, cart.getCartItems());

            orderProcessor.addOrder(order);
        }
    }

    private Optional<Configuration> getConfigurationById(List<Configuration> configurations, int id) {
        return configurations.stream()
                .filter(c -> c.getId() == id)
                .findAny();
    }
}