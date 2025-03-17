import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
        System.out.println("Добавлено в корзину: " + product.getName());
    }

    public void showCart() {
        if (products.isEmpty()) {
            System.out.println("Корзина пуста.");
            return;
        }
        System.out.println("Содержимое корзины:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public double getTotalPrice() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public void clear() {
        products.clear();
    }
}
