package consoleMenu;
/**
 * @author KumaraMalik
 */
public class Electronics extends Product {
    //Instance variables in Electronics class
    private String brand;
    private int warrantyPeriod;

    // Electronics constructor
    @Override
    public String getProductType() {
        return "Electronics";
    }

    public Electronics(String productID, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        super(productID, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }


    // get methods
    public String getBrand() {
        return brand;
    }
    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }


    // set methods
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setWarrantyPeriod(int period) {
        this.warrantyPeriod = period;
    }

    // to string method
    @Override
    public String displayDetails() {
        return super.displayDetails() + ", Brand: " + getBrand() + ", Warranty Period(months): " + getWarrantyPeriod();
    }
}

