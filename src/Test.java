import models.Cart;
import services.ProductManager;

public class Test {
    public static void main(String[] args) {
        ProductManager productManager = new ProductManager();
        productManager.displayProducts();

        Cart cart = new Cart();
//        cart.addToCart(ProductManager.getProducts().get(0));

//        System.out.println(cart);
    }
}
