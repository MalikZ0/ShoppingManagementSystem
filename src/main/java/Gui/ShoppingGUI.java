package Gui;
/**
 * @author KumaraMalik
 */
import consoleMenu.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.Map;
import javax.swing.event.*;
import javax.swing.table.TableRowSorter;

import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ShoppingGUI extends JFrame {
    private JComboBox<String> productTypeComboBox;
    private JTable productTable;
    private JTextArea productDetailsTextArea;
    private JButton addToCartButton;
    private JButton viewCartButton;
    private DefaultTableModel tableModel;
    private ShoppingCart cart;
    private WSM store;
    private String selectedProductType;
    private Object[][] productListData;
    private String username;
    private ViewProductsWindow viewProductsWindow;

    // Modify the constructor to accept username
    public ShoppingGUI(ShoppingCart cart, WSM store, String username) {
        // Initialize components
        productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothes"});
        tableModel = new DefaultTableModel();
        productTable = new JTable(tableModel);
        productDetailsTextArea = new JTextArea();
        addToCartButton = new JButton("Add to Cart");
        viewCartButton = new JButton("View Cart");

        // Set cart
        this.cart = cart;
        // set store
        this.store = store;
        // Set the username and password
        this.username = username;

        // Set layout
        setLayout(new BorderLayout());

        // Add components to the frame
        add(createTopPanel(), BorderLayout.NORTH);
        add(new JScrollPane(productTable), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        // Set frame properties
        setTitle("Shopping GUI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        // Add table listener
        addTableListener(); // Call the method to add the table listener
        // Initialize the productListData array
        productListData = new Object[0][5];
        // Set up the table columns and model
        createColumns();
        productTable.setAutoCreateRowSorter(true);  // Enable sorting on the table

        // Initialize the productListData array and load product data into the table
        productListData = new Object[0][5];
        loadProductData();
        // Initialize new windows
        viewProductsWindow = new ViewProductsWindow(cart, username);
        // Add product type filter functionality

        // Add action listener to the viewCartButton
        viewCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewProductsWindow.setVisible(true);
            }
        });

        productTypeComboBox.addActionListener(e -> {
            selectedProductType = (String) productTypeComboBox.getSelectedItem();
            loadProductData();
        });

        // Add button to trigger sorting
        JButton sortButton = new JButton("Sort Alphabetically");
        sortButton.addActionListener(e -> {
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
            productTable.setRowSorter(sorter);
            java.util.List<RowSorter.SortKey> sortKeys = new java.util.ArrayList<>();
            int columnIndexToSort = 1; // Column index for product name
            sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
            sorter.setSortKeys(sortKeys);
            sorter.sort();
        });
        viewProductsWindow = new ViewProductsWindow(cart, username);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        // Add your code to create the top panel here
        // ...

        // Example code:
        JLabel titleLabel = new JLabel("Shopping GUI");
        topPanel.add(titleLabel);

        return topPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        // Add your code to create the bottom panel here
        // ...

        // Example code:
        JButton submitButton = new JButton("Submit");
        bottomPanel.add(submitButton);

        return bottomPanel;
    }

    private void addTableListener() {
        // Add your code to add table listener here
        // ...

        // Example code:
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // Handle table selection change
            }
        });
    }

    public void loadProductData() {
        // Add your code to load product data here
        // ...

        // Example code:
        List<Product> productList = store.getProducts();
        for (Product product : productList) {
            // Add product data to table model
        }
    }

    private void createColumns() {
        // Add your code to create columns here
        // ...

        // Example code:
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Price");
    }

    public void addToCart() {
        // Add your code to add to cart here
        // ...

        // Example code:
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            Product selectedProduct = productListData[selectedRow];
            cart.addToCart(selectedProduct);
        }
    }

    public void viewProducts() {
        // Add your code to view products here
        // ...

        // Example code:
        viewProductsWindow = new ViewProductsWindow(cart, username);
        viewProductsWindow.showProducts();
    }
    public class MainClass {
        public void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            });
        }
    }

    class LoginFrame extends JFrame {
        private JTextField usernameField;
        private JPasswordField passwordField;

        public LoginFrame() {
            setTitle("Login");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel(new java.awt.GridLayout(3, 2));

            JLabel usernameLabel = new JLabel("Username:");
            usernameField = new JTextField();
            JLabel passwordLabel = new JLabel("Password:");
            passwordField = new JPasswordField();
            JButton loginButton = new JButton("Login");

            panel.add(usernameLabel);
            panel.add(usernameField);
            panel.add(passwordLabel);
            panel.add(passwordField);
            panel.add(new JLabel());
            panel.add(loginButton);

            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    if (isValidUser(username, password)) {
                        ShoppingGUI shoppingGUI = new ShoppingGUI(null, null, username);
                        shoppingGUI.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            add(panel);
        }

        private boolean isValidUser(String username, String password) {
            // Add your user authentication logic here
            // For demonstration purposes, let's assume the username is "admin" and the password is "password"
            return username.equals("admin") && password.equals("password");
        }
    }



    class ViewProductsWindow extends JFrame {
        private JTable productsTable;
        private DefaultTableModel tableModel;

        public ViewProductsWindow(ShoppingCart cart, String username) {
            // Initialize components
            tableModel = new DefaultTableModel();
            productsTable = new JTable(tableModel);

            // Set layout
            setLayout(new BorderLayout());

            // Add components to the frame
            add(new JScrollPane(productsTable), BorderLayout.CENTER);

            // Set frame properties
            setTitle("View Products");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

        }
        // Modify the showProducts method to accept username and productId
        public void showProducts(ShoppingCart cart, String username, String productId) {
            // Clear existing data and create columns
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            createColumns();

            // Populate the tableModel using the cart product details
            for (Map.Entry<String, ? extends Product> entry : cart.getProducts().entrySet()) {
                Product product = entry.getValue();
                tableModel.addRow(new Object[]{product.getProductID(), product.getAvailableItems(), product.getPrice()});
            }

            // Calculate total and discounts
            double total = cart.calculateTotal();
            double firstPersonDiscount = cart.calculateFirstPersonDiscount(username);
            double categoryDiscount = cart.calculateThreeItemDiscount();
            double finalTotal = total - firstPersonDiscount - categoryDiscount;

            // Create labels for the discounts
            JLabel totalLabel = new JLabel("Total: $" + total);
            JLabel firstPersonDiscountLabel = new JLabel("First Person Discount (10%): $" + firstPersonDiscount);
            JLabel categoryDiscountLabel = new JLabel("Three Times in the Same Category Discount (20%): $" + categoryDiscount);
            JLabel finalTotalLabel = new JLabel("Final Total: $" + finalTotal);

            // Create a panel to display discount labels
            JPanel labelsPanel = new JPanel(new GridLayout(4, 1));
            labelsPanel.add(totalLabel);
            labelsPanel.add(firstPersonDiscountLabel);
            labelsPanel.add(categoryDiscountLabel);
            labelsPanel.add(finalTotalLabel);

            // Add components to the frame
            add(new JScrollPane(productsTable), BorderLayout.CENTER);
            add(labelsPanel, BorderLayout.SOUTH);
            setVisible(true);
        }

        private void createColumns() {
            tableModel.addColumn("Product");
            tableModel.addColumn("Quantity");
            tableModel.addColumn("Price");

        }
    }
}