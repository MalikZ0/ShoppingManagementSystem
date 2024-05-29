package test;

import consoleMenu.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class WSMTest {
    private WSM wsm;

    @Before
    public void setUp() {
        wsm = new WSM();
    }

    @Test
    public void testAddProduct() {
        wsm.addProduct(1, "P001", "Laptop", 10, 500.0, "Dell", 12, "", "");
        wsm.addProduct(2, "P002", "T-Shirt", 20, 25.0, "", 0, "M", "Blue");

        assertNotNull(wsm.getProduct("P001"));
        assertNotNull(wsm.getProduct("P002"));
    }

    @Test
    public void testDeleteProduct() {
        wsm.addProduct(1, "P003", "Mobile Phone", 15, 300.0, "Samsung", 24, "", "");

        assertNotNull(wsm.getProduct("P003"));

        wsm.deleteProduct(1, "P003");

        assertNull(wsm.getProduct("P003"));
    }

    @Test
    public void testSaveAndLoadDataFromFile() {
        wsm.addProduct(1, "P004", "TV", 5, 700.0, "Sony", 36, "", "");

        wsm.saveToFile("products.txt");
        wsm = new WSM();  // Resetting state

        wsm.loadDataFromFile("products.txt");

        assertNotNull(wsm.getProduct("P004"));
    }

    @Test
    public void testUpdateStock() {
        wsm.addProduct(2, "P005", "Jeans", 30, 40.0, "", 0, "M", "Black");

        int initialStock = wsm.getProduct("P005").getAvailableItems();

        wsm.updateCStock("P005", 20);

        int updatedStock = wsm.getProduct("P005").getAvailableItems();

        assertEquals(initialStock + 20, updatedStock);
    }

    @Test
    public void testDisplayProducts() {
        wsm.addProduct(1, "P006", "Smartwatch", 8, 150.0, "Apple", 24, "", "");

        // Redirect system out to capture output for testing
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        wsm.displayProductCatalog();

        // Reset system out
        System.setOut(System.out);

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Smartwatch"));

    }
}