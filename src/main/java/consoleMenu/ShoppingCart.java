package consoleMenu;
/**
 * @author KumaraMalik
 */
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ShoppingCart {
    private final List<Product> productList = new ArrayList<>();
    private final Map<String, Product> cart = new TreeMap<>();
    private final Map<String, User> userCredentials = new TreeMap<>();
    private final Map<String, ShoppingCart> userShoppingCarts = new TreeMap<>();
    double currentPrice = 1.0;
    int currentStock = 0;
    private User newUser;
    private WSM store;

    public ShoppingCart(WSM store) {
        this.store = store;
    }

    public void addProduct(String productId) {
        // Assuming store is an instance of WSM that contains the product catalog
        Product product = store.getProduct(productId);

        if (product != null) {
            if (cart.containsKey(productId)) {
                // Product is already in the cart
                Product cartProduct = cart.get(productId);

                if (product.getAvailableItems() > 0) {
                    if (product instanceof Electronics) {
                        cartProduct.setAvailableItems(cartProduct.getAvailableItems() + 1);
                        currentPrice = product.getPrice();
                        cartProduct.setPrice(currentPrice * cartProduct.getAvailableItems());
                        int stock = product.getAvailableItems();
                        product.setAvailableItems(stock - 1);
                        cartProduct.setAvailableItems(currentStock + 1);
                        currentStock++;
                    } else if (product instanceof Clothing) {
                        cartProduct.setAvailableItems(cartProduct.getAvailableItems() + 1);
                        currentPrice = product.getPrice();
                        cartProduct.setPrice(currentPrice * cartProduct.getAvailableItems());
                        int stock = product.getAvailableItems();
                        product.setAvailableItems(stock - 1);
                        cartProduct.setAvailableItems(currentStock + 1);
                        currentStock++;
                    }
                }
            } else {
                // Product is not in the cart
                if (product.getAvailableItems() > 0) {
                    productList.add(product);
                    int stock = product.getAvailableItems();
                    product.setAvailableItems(stock - 1);

                    // Create a new product instance for the cart
                    Product cartProduct = createCartProduct(product);
                    cartProduct.setAvailableItems(currentStock + 1);
                    currentStock++;
                    cart.put(productId, cartProduct);
                } else {
                    System.out.println("Product out of stock.");
                }
            }
        } else {
            System.out.println("Product not found in the catalog.");
        }
    }

    private Product createCartProduct(Product product) {
        // This method creates a new Product instance for the cart
        if (product instanceof Electronics electronicsProduct) {
            return new Electronics(
                    electronicsProduct.getProductID(),
                    electronicsProduct.getProductName(),
                    electronicsProduct.getAvailableItems(),
                    electronicsProduct.getPrice(),
                    electronicsProduct.getBrand(),
                    electronicsProduct.getWarrantyPeriod()
            );
        } else if (product instanceof Clothing clothingProduct) {
            return new Clothing(
                    clothingProduct.getProductID(),
                    clothingProduct.getProductName(),
                    clothingProduct.getAvailableItems(),
                    clothingProduct.getPrice(),
                    clothingProduct.getSize(),
                    clothingProduct.getColor()
            );
        } else {
            // Handle other product types
            return null;
        }
    }

    public double calculateTotal() {
        double total = 0;

        // Map to store the quantity of each category
        Map<String, Integer> categoryQuantities = new TreeMap<>();

        for (Product product : cart.values()) {
            total += product.getPrice();

            // Update category quantities
        String category = getCategory(product);
            categoryQuantities.put(category, categoryQuantities.getOrDefault(category, 0) + 1);
        }

        return total;
    }

    private String getCategory(Product product) {
        if (store.getElectronicsList().containsKey(product.getProductID())) {
            return "Electronics";
        } else if (store.getClothesList().containsKey(product.getProductID())) {
            return "Clothes";
        } else {
            return "";
        }
    }

    public void showProductDetails() {
        for (Product product : cart.values()) {
            System.out.println(product.displayDetails());
        }
    }

    public boolean login(String username) {
        return userCredentials.containsKey(username);
    }

    public void createUser(String username, String password) {
        newUser = new User(username, password);
        userCredentials.put(username, newUser);
        userShoppingCarts.put(username, new ShoppingCart(store));
        calculateFirstPersonDiscount(username);
    }

    public ShoppingCart getUserShoppingCart(String username) {
        return userShoppingCarts.get(username);
    }

    public double calculateFirstPersonDiscount(String username) {
        double v = 0.0;
        if (userCredentials.containsKey(username)) {
            v = 0.1 * calculateTotal();
        }
        return v ;
    }

    public double calculateThreeItemDiscount() {
        int electronicsCount = countProductsOfType(Electronics.class);
        int clothingCount = countProductsOfType(Clothing.class);
        double discount = 0;

        // Calculate discount for Electronics
        if (electronicsCount >= 3) {
            discount += 0.2 * calculateTotalOfType(Electronics.class);
        }

        // Calculate discount for Clothing
        if (clothingCount >= 3) {
            discount += 0.2 * calculateTotalOfType(Clothing.class);
        }

        return discount;
    }

    private <T extends Product> double calculateTotalOfType(Class<T> productType) {
        double total = 0;
        for (Product product : cart.values()) {
            if (productType.isInstance(product)) {
                total += product.getPrice();
            }
        }
        return total;
    }

    private <T extends Product> int countProductsOfType(Class<T> productType) {
        int count = 0;
        for (Product product : cart.values()) {
            if (productType.isInstance(product)) {
                count++;
            }
        }
        return count;
    }

    public Map<String, ? extends Product> getProducts() {
        return cart;
    }

    public double calculateTotalWithDiscounts() {
        double total = calculateTotal();
        total -= calculateFirstPersonDiscount(newUser.getUsername());
        total -= calculateThreeItemDiscount();
        return total;
    }

    public void setUser(User newUser) {
        this.newUser = newUser;
    }
}
