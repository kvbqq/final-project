package cli;

import exceptions.ProductOutOfStockException;
import exceptions.WrongIdException;
import models.*;
import services.OrderProcessor;
import services.ProductManager;

import java.math.BigDecimal;
import java.util.*;

/**
 * Klasa obsługująca interfejs wiersza poleceń sklepu.
 * Pozwala użytkownikowi na interakcję z systemem zakupowym.
 */
public class ShopCLI {
    ProductManager productManager = new ProductManager();
    OrderProcessor orderProcessor = new OrderProcessor();
    Cart cart = new Cart();
    List<Discount> discounts = List.of(new Discount("AAA", BigDecimal.valueOf(0.3)), new Discount("BBB", BigDecimal.valueOf(0.15)));
    Scanner scanner = new Scanner(System.in);

    /**
     * Wyświetla menu główne aplikacji i obsługuje interakcję z użytkownikiem.
     */
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

    /**
     * Dodaje produkt do koszyka po wyborze użytkownika.
     * Obsługuje wybór konfiguracji oraz sprawdza dostępność produktu.
     */
    private void addProductToCart() {
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

    /**
     * Pobiera produkt na podstawie ID wprowadzonego przez użytkownika.
     * @return Obiekt produktu.
     * @throws WrongIdException jeśli produkt o podanym ID nie istnieje.
     */
    private Product getProductByIdInput() throws WrongIdException {
        System.out.println("\nPodaj ID produktu:");
        Optional<Product> product = productManager.getProductById(scanner.nextInt());
        scanner.nextLine();

        if (product.isEmpty()) {
            throw new WrongIdException("Brak produktu o podanym ID");
        }
        return product.get();
    }

    /**
     * Pozwala użytkownikowi wybrać konfiguracje dla danego produktu.
     * @param chosenProduct Produkt, dla którego wybierane są konfiguracje.
     * @return Lista wybranych konfiguracji.
     * @throws WrongIdException jeśli podano błędne ID konfiguracji.
     */
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

    /**
     * Pozwala użytkownikowi na wybór wielu konfiguracji dla danego produktu.
     * @param configurations Lista dostępnych konfiguracji.
     * @return Lista wybranych konfiguracji.
     * @throws WrongIdException jeśli podano błędne ID konfiguracji.
     */
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

    /**
     * Pozwala użytkownikowi wybrać jedną konfigurację z listy dostępnych opcji.
     * @param possibleConfigurations Lista możliwych konfiguracji.
     * @return Wybrana konfiguracja.
     * @throws WrongIdException jeśli podano błędne ID konfiguracji.
     */
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

    /**
     * Usuwa produkt z koszyka na podstawie ID wprowadzonego przez użytkownika.
     */
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

    /**
     * Tworzy zamówienie na podstawie produktów w koszyku.
     * Pobiera dane użytkownika oraz stosuje ewentualny rabat.
     */
    private void makeOrder() {
        if (cart.getCartItems().isEmpty())
            System.out.println("\nTwój koszyk jest pusty");
        else {
            applyDiscount();

            System.out.println("\nPodaj imię:");
            String userName = scanner.nextLine();

            System.out.println("\nPodaj nazwisko:");
            String userSurname = scanner.nextLine();

            System.out.println("\nPodaj adres:");
            String userAddress = scanner.nextLine();

            Order order = new Order(0, userName, userSurname, userAddress, cart);

            orderProcessor.processOrder(order);
        }
    }

    /**
     * Pobiera konfigurację na podstawie ID.
     * @param configurations Lista dostępnych konfiguracji.
     * @param id ID konfiguracji.
     * @return Opcjonalna konfiguracja, jeśli istnieje.
     */
    private Optional<Configuration> getConfigurationById(List<Configuration> configurations, int id) {
        return configurations.stream()
                .filter(c -> c.getId() == id)
                .findAny();
    }

    /**
     * Stosuje kod rabatowy podany przez użytkownika.
     * Jeśli kod jest niepoprawny, informuje o błędzie.
     */
    private void applyDiscount() {
        System.out.println("\nPodaj kod rabatowy: ");
        String discountCodeInput = scanner.nextLine();
        Optional<Discount> chosenDiscount = discounts.stream()
                .filter(discount -> discount.getCode().equals(discountCodeInput))
                .findAny();

        if (chosenDiscount.isPresent()) {
            cart.setDiscountPercentage(chosenDiscount.get().getPercentage());
        } else {
            System.err.println("[Błąd] Kod nie istnieje");
        }
    }
}