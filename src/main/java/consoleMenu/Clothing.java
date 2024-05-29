package consoleMenu;
/**
 * @author KumaraMalik
 */
public class Clothing extends Product {
    //Instance variables in Clothing class
    private String size;
    private String color;

    // Clothing constructor
    public Clothing(String productID, String productName, int availableItems, double price) {
        super(productID, productName, availableItems, price);
    }

    @Override
    public String getProductType() {
        return "Clothing";
    }

    public Clothing(String productID, String productName, int availableItems, double price, String size, String color) {
        super(productID, productName, availableItems, price);
        this.size = size;
        this.color = color;
    }




    // get methods
    public String getSize() {
        return size;
    }
    public String getColor() {
        return color;
    }

    // set methods
    public void setSize(String size) {
        this.size = size;
    }
    public void setColor(String color) {
        this.color = color;
    }


    // to string method
    @Override
    public String displayDetails() {
        return super.displayDetails() + ", Size: " + getSize() + ", Color: " + getColor();
    }
}
