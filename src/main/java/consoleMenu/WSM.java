package consoleMenu;
/**
 * @author KumaraMalik
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class WSM implements ShoppingManager{
    private Map<String, Electronics> electronicsList = new TreeMap<String, Electronics>();
    private Map<String, Clothing> clothesList = new TreeMap<String, Clothing>();
    private Map<String, Product> productCatalog = new TreeMap<>();  // Catalog map for all products

    //////////////// add products to the collection  section ////////////////
    @Override
    public void addProduct(int choice, String id, String productName, int availableItems, double price, String brand, int warrantyPeriod, String size, String color) {
        if (choice == 1){
            Electronics electronics = new Electronics(id, productName, availableItems, price, brand, warrantyPeriod);
            electronicsList.put(id, electronics);
            addToProductCatalog(id, electronics);
            System.out.println("Electronics added successfully!");
        }else if (choice == 2){
            Clothing clothing = new Clothing(id, productName, availableItems, price, size, color);
            clothesList.put(id, clothing);
            addToProductCatalog(id, clothing);
            System.out.println("Clothes added successfully!");
        }else {
            System.out.println("Invalid choice. Please try again.");
        }
    }


    @Override
    public void deleteProduct(int choice, String id) {
        //////////////// remove products from collection section ////////////////
        if (choice == 1){
            Electronics electronics = electronicsList.get(id);
            if (electronics != null) {
                electronicsList.remove(id);
                removeFromProductCatalog(id);
                System.out.println("Electronics removed successfully!");
            }
        } else if (choice == 2) {
            Clothing clothes = clothesList.get(id);
            if (clothes != null) {
                clothesList.remove(id);
                removeFromProductCatalog(id);
                System.out.println("Clothes removed successfully!");
            }
        }else {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
    }

    @Override
    public void printProductList() {
        System.out.println("Electronics in the store:");
        displayElectronics();
        System.out.println("\nClothes in the store:");
        displayClothes();
    }

    @Override
    public void saveToFile(String fileName) {
        ////////////////  product saving to file section ////////////////
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Electronics electronics : electronicsList.values()) {
                writer.write(electronics.displayDetails() + "\n");
            }
            for (Clothing clothes : clothesList.values()) {
                writer.write(clothes.displayDetails() + "\n");
            }
            System.out.println("Data saved to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }
    @Override
    public void loadDataFromFile(String fileName) {
        ////////////////  backup section ////////////////
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Process each line and add products to the store
                String[] parts = line.split(", ");
                String id = parts[0].split(": ")[1];
                String productName = parts[1].split(": ")[1];

                int availableItems = Integer.parseInt(parts[2].split(": ")[1]);

                // Fix: Remove "$" sign from the price string before parsing
                double price = Double.parseDouble(parts[3].split(": ")[1].replace("$", ""));

                if (parts[4].contains("Brand: ")) {
                    // Electronics
                    String brand = parts[4].split(": ")[1];
                    int warrantyPeriod = Integer.parseInt(parts[5].split(": ")[1].trim());

                    Electronics electronics = new Electronics(id, productName, availableItems, price, brand, warrantyPeriod);
                    electronicsList.put(id, electronics);
                    addToProductCatalog(id, electronics);
                } else if (parts[4].contains("Size: ")) {
                    // Clothes
                    String size = parts[4].split(": ")[1];
                    String color = parts[5].split(": ")[1];

                    Clothing clothing = new Clothing(id, productName, availableItems, price, size, color);
                    clothesList.put(id, clothing);
                    addToProductCatalog(id, clothing);
                }
            }
            System.out.println("Data loaded successfully from file: " + fileName);
            reader.close();
        } catch (IOException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
        }
    }

    // getters
    public Map<String, Product> getProductCatalog() {
        return productCatalog;
    }
    public Map<String, Electronics> getElectronicsList() {
        return electronicsList;
    }
    public Map<String, Clothing> getClothesList() {
        return clothesList;
    }
    // Add a product to the main product catalog
    private void addToProductCatalog(String id, Product product) {
        productCatalog.put(id, product);
    }
    //////////////// stock maintain section ////////////////
    // Update electronics stock
    public void updateEStock(String id, int stock){
        int oldStock = getElectronicsList().get(id).getAvailableItems();
        int newStock = oldStock + stock;
        getElectronicsList().get(id).setAvailableItems(newStock);
        updateProductCatalogStock(id, newStock);
    }
    // Update clothes stock
    public void updateCStock(String id, int stock) {
        int oldStock = getClothesList().get(id).getAvailableItems();
        int newStock = oldStock + stock;
        getClothesList().get(id).setAvailableItems(newStock);
        updateProductCatalogStock(id, newStock);
    }
    // Update the product catalog when stock is updated
    private void updateProductCatalogStock(String id, int stock) {
        Product product = productCatalog.get(id);
        if (product != null) {
            product.setAvailableItems(stock);
        }
    }
    // Remove a product from the product catalog
    private void removeFromProductCatalog(String id) {
        productCatalog.remove(id);
    }

    //////////////// printing products section ////////////////
    public void displayProductCatalog() {
        for (Product product : productCatalog.values()) {
            System.out.println(product.displayDetails());
        }
    }
    public void displayElectronics() {
        for (Electronics electronics : electronicsList.values()) {
            System.out.println(electronics.displayDetails());
        }
    }
    public void displayClothes() {
        for (Clothing clothes : clothesList.values()) {
            System.out.println(clothes.displayDetails());
        }
    }
    public Product getProduct(String productId){
        return getProductCatalog().get(productId);
    }
}
