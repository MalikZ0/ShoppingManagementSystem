package consoleMenu;
/**
 * @author KumaraMalik
 */
public interface ShoppingManager {
    void addProduct(int choice, String id, String productName, int availableItems, double price, String brand, int warrantyPeriod, String size, String color);
    void deleteProduct(int choice, String id);
    void printProductList();
    void saveToFile(String fileName);
    void loadDataFromFile(String fileName);
}

