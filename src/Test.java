import models.Configuration;
import models.ConfigurationType;
import models.Product;
import models.ProductType;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Configuration> computerConfigurations = new ArrayList<>();
        List<Configuration> smartphoneConfigurations = new ArrayList<>();

        computerConfigurations.add(new Configuration(ConfigurationType.PROCESSOR, "Intel i5", 799.00));
        computerConfigurations.add(new Configuration(ConfigurationType.PROCESSOR, "Intel i7", 1269.00));
        computerConfigurations.add(new Configuration(ConfigurationType.PROCESSOR, "AMD Ryzen 7", 699.00));
        computerConfigurations.add(new Configuration(ConfigurationType.RAM, "8GB", 0));
        computerConfigurations.add(new Configuration(ConfigurationType.RAM, "16GB", 159.00));
        computerConfigurations.add(new Configuration(ConfigurationType.RAM, "32GB", 279.00));

        smartphoneConfigurations.add(new Configuration(ConfigurationType.COLOR, "Czarny", 49.00));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.COLOR, "Biały", 49.00));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.COLOR, "Szary", 0));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.BATTERY_CAPACITY, "3000mAh", 0));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.BATTERY_CAPACITY, "4000mAh", 50.00));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.BATTERY_CAPACITY, "5000mAh", 100.00));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.SMARTPHONE_ACCESSORY, "Obudowa", 59.00));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.SMARTPHONE_ACCESSORY, "Szkło hartowane", 49.00));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.SMARTPHONE_ACCESSORY, "Ładowarka", 79.00));
        smartphoneConfigurations.add(new Configuration(ConfigurationType.SMARTPHONE_ACCESSORY, "Słuchawki", 149.00));


        Product p1 = new Product(1, "Komputer", ProductType.COMPUTER, 1000.00, 12, computerConfigurations);
        Product p2 = new Product(2, "Smartfon", ProductType.SMARTPHONE, 800.00, 5, smartphoneConfigurations);

//        p1.configure();
        System.out.println(p1.configure().isPresent());
//        p2.configure();
    }
}
