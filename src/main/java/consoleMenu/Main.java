package consoleMenu;

import Gui.ShoppingGUI;

import javax.swing.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        WSM store = new WSM();
        ShoppingCart cart = new ShoppingCart(store);
        Scanner scanner = new Scanner(System.in);
        boolean exit = true;

        while (exit) {
            System.out.println("\nSelect Scenario:");
            System.out.println("1. BACKUP");
            System.out.println("2. User (Buy Products GUI:)");
            System.out.println("3. Shopping Manager :");
            System.out.println("4. Exit :");
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    loadDataOption(store, scanner);
                    break;
                case 2:
                    userScenario(cart, store, scanner);
                    break;
                case 3:
                    storeManagerScenario(store, scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    exit = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //////////////////////////////// store manager//////////////////////////////
    public static void storeManagerScenario(WSM store, Scanner scanner) {
        System.out.println("\nStore Manager Scenario:");

        while (true) {
            System.out.println("\nSelect Store Manager Option:");
            System.out.println("1. Add New Product");
            System.out.println("2. Remove Product");
            System.out.println("3. Display Products");
            System.out.println("4. Save Products to File");
            //    System.out.println("5. load Products from File");
            System.out.println("5. Exit Store Manager Scenario");
            int managerChoice = scanner.nextInt();

            switch (managerChoice) {
                case 1:
                    addNewProduct(store, scanner);
                    break;
                case 2:
                    removeProduct(store, scanner);
                    break;
                case 3:
                    displayProducts(store);
                    break;
                case 4:
                    System.out.println("Enter file name to save products:");
                    String fileName = scanner.next();
                    store.saveToFile(fileName);
                    break;
                case 5:
                    return;  // exit
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    ////////////////  store manager methods ////////////////
    public static void addNewProduct(WSM store, Scanner scanner) {
        System.out.println("\nSelect Product Type:");
        System.out.println("1. Electronics");
        System.out.println("2. Clothes");
        int typeChoice = scanner.nextInt();
        System.out.println("Enter Product ID:");
        String id = scanner.next();
        if (typeChoice == 1 && store.getElectronicsList().containsKey(id)) {
            System.out.println("Enter Stock:");
            int availableItems = scanner.nextInt();
            store.updateEStock(id, availableItems);
            return;
        } else if (typeChoice == 2 && store.getClothesList().containsKey(id)) {
            System.out.println("Enter Stock:");
            int availableItems = scanner.nextInt();
            store.updateCStock(id, availableItems);
            return;
        } else if (typeChoice == 1 && store.getClothesList().containsKey(id)) {
            System.out.println("id is taken");
            return;
        } else if (typeChoice == 2 && store.getElectronicsList().containsKey(id)) {
            System.out.println("id is taken");
            return;
        }
        System.out.println("Enter Product Price:");
        double price = scanner.nextDouble();
        System.out.println("Enter Product Name:");
        String productName = scanner.next();
        System.out.println("Enter Stock Count:");
        int availableItems = scanner.nextInt();
        String color = null;
        String brand = null;
        String size  = null;
        int warrantyPeriod = 0;
        if (typeChoice == 1) {
            System.out.println("Enter Brand:");
            brand = scanner.next();
            System.out.println("Enter Warranty Period (months):");
            warrantyPeriod = scanner.nextInt();
        } else if (typeChoice == 2) {
            System.out.println("Enter Size:");
            size = scanner.next();
            System.out.println("Enter Color:");
            color = scanner.next();
        }
        store.addProduct(typeChoice, id, productName, availableItems, price, brand, warrantyPeriod, size, color);
    }
    public static void removeProduct(WSM store, Scanner scanner) {
        System.out.println("Enter Product Type to Remove:");
        System.out.println("1. Electronics");
        System.out.println("2. Clothes");
        int typeChoice = scanner.nextInt();

        System.out.println("Enter Product ID to Remove:");
        String id = scanner.next();
        store.deleteProduct(typeChoice, id);
    }

    public static void displayProducts(WSM store) {
        store.printProductList();
    }

    private static void loadDataOption(WSM store, Scanner scanner) {
        System.out.println("Enter file name to load data:");
        String fileName = scanner.next();
        store.loadDataFromFile(fileName);
    }
    //////////////////////////////// user//////////////////////////////

    private static void userScenario(ShoppingCart cart, WSM store, Scanner scanner) {
        System.out.println("\nUser Scenario:");

        // User login or register
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();

        if (cart.login(username)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("User not found. Registering...");

            // Register new user
            cart.createUser(username, password);
            System.out.println("User registered successfully!");
        }

        // Apply methods of user's shopping cart
        ShoppingCart userCart = cart.getUserShoppingCart(username);
        while (true) {
            System.out.println("\nSelect User's Shopping Cart Option:");
            System.out.println("1. Open Shopping GUI");
            System.out.println("2. Exit User Scenario");
            int userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1:
                    openShoppingGUI(cart, store, username, password);  // Open the Shopping GUI
                    break;
                case 2:
                    return;  // exit loop
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void openShoppingGUI(ShoppingCart cart, WSM store, String username, String password) {
        SwingUtilities.invokeLater(() -> new ShoppingGUI(cart,store,username));
    }
}