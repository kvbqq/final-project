package cli;

import exceptions.ProductOutOfStockException;
import exceptions.WrongIdException;
import models.*;
import services.OrderProcessor;
import services.ProductManager;
import java.util.*;

public class ShopCLI {
    ProductManager productManager = new ProductManager();
    OrderProcessor orderProcessor = new OrderProcessor();
    Cart cart = new Cart();
    Scanner scanner = new Scanner(System.in);
    public void menu() {
        int userInput = 0;

        while(userInput != 6) {
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
                default -> System.out.println("Błędny wybór (Wprowadź cyfrę od 1 do 6)");
            }
        }
    }
    public void addProductToCart() {
        try {
            Product chosenProduct = getProductByIdInput();
            if (chosenProduct.getStock() == 0) {
                throw new ProductOutOfStockException("Stan produktu wynosi 0");
            }

            List<Configuration> chosenConfigurations = chooseConfigurations(chosenProduct);
            cart.addToCart(new CartItem(chosenProduct.getId(), chosenProduct, chosenConfigurations));

            System.out.println("\nProdukt dodany do koszyka!");

        } catch (ProductOutOfStockException | WrongIdException | NumberFormatException e) {
            System.err.println("\n[Błąd] " + e.getMessage());
        }
    }

    private Product getProductByIdInput() throws WrongIdException {
        System.out.println("\nPodaj ID produktu:");
        Optional<Product> product = productManager.getProductById(scanner.nextInt());
        scanner.nextLine();

        if (product.isEmpty()) {
            throw new WrongIdException("Brak produktu o podanym ID");
        }
        return product.get();
    }

    private List<Configuration> chooseConfigurations(Product chosenProduct) throws WrongIdException {
        List<Configuration> chosenConfigurations = new ArrayList<>();
        List<Configuration> possibleConfigurations = chosenProduct.getConfigurations();
        List<ConfigurationType> configurationTypes = possibleConfigurations.stream()
                .map(Configuration::getType)
                .distinct()
                .toList();

        for (ConfigurationType configurationType : configurationTypes) {
            List<Configuration> filteredConfigurations = possibleConfigurations.stream()
                    .filter(configuration -> configuration.getType().equals(configurationType))
                    .toList();

            System.out.println("\nKonfiguracja elementu - " + configurationType.getName());
            filteredConfigurations.stream()
                    .map(configuration -> String.format(
                            "[%d] %s | Dopłata: %.2f zł",
                            configuration.getId(),
                            configuration.getName(),
                            configuration.getPrice()
                    ))
                    .forEach(System.out::println);

            if (configurationType.isMultipleChoices()) {
                chosenConfigurations.addAll(chooseMultipleConfigurations(filteredConfigurations));
            } else {
                chosenConfigurations.add(chooseSingleConfiguration(filteredConfigurations));
            }
        }
        return chosenConfigurations;
    }

    private List<Configuration> chooseMultipleConfigurations(List<Configuration> configurations) throws WrongIdException {
        System.out.println("\nWybierz wiele dostępnych opcji (oddzielone przecinkami):");
        String input = scanner.nextLine();
        List<String> chosenOptions = Arrays.stream(input.split(",")).toList();

        List<Configuration> chosenConfigurations = new ArrayList<>();
        for (String option : chosenOptions) {
            Optional<Configuration> config = getConfigurationById(configurations, Integer.parseInt(option.trim()));
            if (config.isEmpty()) {
                throw new WrongIdException("Brak konfiguracji o podanym ID");
            }
            chosenConfigurations.add(config.get());
        }
        return chosenConfigurations;
    }

    private Configuration chooseSingleConfiguration(List<Configuration> possibleConfigurations) throws WrongIdException {
        System.out.println("\nWybierz jedną z dostępnych opcji:");
        int choice = scanner.nextInt();
        scanner.nextLine();

        Optional<Configuration> chosenConfiguration = getConfigurationById(possibleConfigurations, choice);
        if (chosenConfiguration.isEmpty()) {
            throw new WrongIdException("Brak konfiguracji o wybranym ID");
        }
        return chosenConfiguration.get();
    }

    private void removeProductFromCart() {
        try {
            if (cart.getCartItems().isEmpty())
                System.out.println("\nTwój koszyk jest pusty");
            else {
                System.out.println("\nTwój koszyk:\n" + cart);
                System.out.println("\nPodaj id produktu, który chcesz usunąć");
                Optional<CartItem> cartItemToRemove = cart.getCartItems().stream()
                        .filter(cartItem -> cartItem.getProduct().getId() == scanner.nextInt())
                        .findAny();
                if (cartItemToRemove.isEmpty())
                    throw new WrongIdException("Produkt z podanym id nie znajduje się w twoim koszyku");

                cart.removeFromCart(cartItemToRemove.get());
            }
        } catch (WrongIdException e) {
            System.err.println("\n[Błąd] " + e.getMessage());
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

            System.out.println("\nPodaj adres:");
            String userAddress = scanner.nextLine();

            Order order = new Order(0, userName, userSurname, userAddress, cart.getCartItems());

            orderProcessor.processOrder(order);
        }
    }

    private Optional<Configuration> getConfigurationById(List<Configuration> configurations, int id) {
        return configurations.stream()
                .filter(c -> c.getId() == id)
                .findAny();
    }
}