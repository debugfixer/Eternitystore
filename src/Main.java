import java.util.*;

// Класс товара
class Product {
    private final String name;
    private final double price;
    private final String manufacturer;
    private double rating;

    public Product(String name, double price, String manufacturer, double rating) {
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.rating = rating;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getManufacturer() { return manufacturer; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    @Override
    public String toString() {
        return name + " - " + price + "Р (" + manufacturer + ", Рейтинг: " + rating + ")";
    }
}

// Класс корзины пользователя
class Cart {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public double getTotal() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public void clear() {
        products.clear();
    }
}

// Класс заказа
class Order {
    private static int idCounter = 1;
    private final int id;
    private final List<Product> products;
    private String status;

    public Order(List<Product> products) {
        this.id = idCounter++;
        this.products = new ArrayList<>(products);
        this.status = "Processing";
    }

    public int getId() { return id; }
    public List<Product> getProducts() { return products; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Order #" + id + " - Status: " + status + " - Items: " + products.size();
    }
}

// Магазин
class Store {
    private final List<Product> inventory = new ArrayList<>();

    public void addProduct(Product product) {
        inventory.add(product);
    }

    public List<Product> searchByName(String keyword) {
        return inventory.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    public List<Product> filterByPrice(double min, double max) {
        return inventory.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .toList();
    }

    public List<Product> getInventory() {
        return new ArrayList<>(inventory);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Store store = new Store();
        store.addProduct(new Product("Ноутбук", 120000, "Apple", 4.34));
        store.addProduct(new Product("Смартфон", 80000, "Samsung", 4.24));
        store.addProduct(new Product("Наушники", 20000, "Sony", 3.5));
        Cart cart = new Cart();

        while (true) {
            System.out.println("1. Показать товар\n2. Найти товар\n3. Фильтр по цене\n4. Добавить в корзину\n5. Просмотреть корзину\n6. Проверить\n7. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> store.getInventory().forEach(System.out::println);
                case 2 -> {
                    System.out.print("Введите ключевое слово: ");
                    String keyword = scanner.nextLine();
                    store.searchByName(keyword).forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("Минимальная цена: ");
                    double min = scanner.nextDouble();
                    System.out.print("Максимальная цена: ");
                    double max = scanner.nextDouble();
                    store.filterByPrice(min, max).forEach(System.out::println);
                }
                case 4 -> {
                    System.out.print("Введите название: ");
                    String name = scanner.nextLine();
                    store.getInventory().stream()
                            .filter(p -> p.getName().equalsIgnoreCase(name))
                            .findFirst()
                            .ifPresent(cart::addProduct);
                }
                case 5 -> cart.getProducts().forEach(System.out::println);
                case 6 -> {
                    if (!cart.getProducts().isEmpty()) {
                        Order order = new Order(cart.getProducts());
                        System.out.println("Заказ размещён: " + order);
                        cart.clear();
                    } else {
                        System.out.println("Корзина пуста!");
                    }
                }
                case 7 -> {
                    System.out.println("Выход...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Ошибка ввода!");
            }
        }
    }
}
