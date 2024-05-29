package consoleMenu;
/**
 * @author KumaraMalik
*/
public abstract class Product {
    //Instance variables in Product class
    private String productID;
    private String productName;
    private int availableItems;
    private double price;

    // product constructor
    public Product(String productID, String productName, int availableItems, double price) {
        this.productID = productID;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

    // get methods
    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public double getPrice() {
        return price;
    }


    // set methods
    public void setProductID(String pID) {
        this.productID = pID;
    }
    public void setProductName(String pName) {
        this.productName = pName;
    }
    public void setAvailableItems(int Items) {
        this.availableItems = Items;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public abstract String getProductType();

    // to string method
    public String displayDetails() {
        return "ID: " + getProductID() + ", Product Name: " + getProductName() + ", Stock: " + getAvailableItems() +", Price: $" + getPrice();
    }
}
