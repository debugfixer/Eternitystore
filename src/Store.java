import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Store {
    private final List<Product> products = new ArrayList<>();
    private final Cart cart = new Cart();
    private final Scanner scanner = new Scanner(System.in);

    public Store() {
        products.add(new Product("Ноутбук", "Электроника", 1000.0));
        products.add(new Product("Смартфон", "Электроника", 700.0));
        products.add(new Product("Холодильник", "Бытовая техника", 1200.0));
        products.add(new Product("Книга", "Книги", 15.0));
    }

    public void run() {
        while (true) {
            System.out.println("\n1. Показать товары\n2. Фильтр товаров\n3. Добавить в корзину\n4. Показать корзину\n5. Оформить заказ\n0. Выход");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showProducts();
                case "2" -> filterProducts();
                case "3" -> addToCart();
                case "4" -> cart.showCart();
                case "5" -> checkout();
                case "0" -> {
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Неверный ввод!");
            }
        }
    }

    private void showProducts() {
        System.out.println("Список товаров:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private void filterProducts() {
        System.out.print("Введите категорию для фильтрации: ");
        String category = scanner.nextLine();
        System.out.println("Результаты:");
        products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .forEach(System.out::println);
    }

    private void addToCart() {
        System.out.print("Введите название товара: ");
        String name = scanner.nextLine();
        products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .ifPresentOrElse(cart::addProduct, () -> System.out.println("Товар не найден!"));
    }

    private void checkout() {
        if (cart.getTotalPrice() == 0) {
            System.out.println("Корзина пуста.");
            return;
        }
        System.out.println("Итоговая сумма: $" + cart.getTotalPrice());
        System.out.println("Заказ оформлен!");
        cart.clear();
    }
}
