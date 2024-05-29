package test;

import Gui.ShoppingGUI;
import consoleMenu.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShoppingGUITest {
    private ShoppingCart cart;
    private WSM store;
    private ShoppingGUI gui;

    @Before
    public void setUp() {
        // Initialize the shopping cart and store
        store = new WSM();
        cart = new ShoppingCart(store);

        // Add some products to the store for testing
        store.addProduct(1, "P001", "Laptop", 10, 500.0, "Dell", 12, "", "");
        store.addProduct(2, "P002", "T-Shirt", 20, 25.0, "", 0, "M", "Blue");

        // Initialize the GUI with the shopping cart, store, and username
        gui = new ShoppingGUI(cart, store, "testUser");
    }

    @Test
    public void testAddToCart() {
        // Simulate selecting a product from the table and adding it to the cart
        gui.loadProductData();  // Load product data into the table
        int initialCartSize = cart.getProducts().size();

        // Select and add a product to the cart
        int selectedRowIndex = 0;  // Index of the selected product in the table
        gui.addToCart();  // Trigger the add to cart action

        assertEquals(initialCartSize + 1, cart.getProducts().size());  // Check if the product was added to the cart
    }

    @Test
    public void testViewProducts() {
        // Simulate viewing the products in the separate window
        gui.viewProducts();  // Trigger the view products action

    }
}