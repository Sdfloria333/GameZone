package com.gamezone.ui;

import com.gamezone.model.Console;
import com.gamezone.model.Customer;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.SaleDetail;
import com.gamezone.model.Seller;
import com.gamezone.model.Videogame;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Console Interface handling all 10 operations defined in the system.
 */
public class ConsoleUI {
    private final ProductService productService;
    private final PersonService personService;
    private final SaleService saleService;
    private final Scanner scanner;

    public ConsoleUI(ProductService productService, PersonService personService, SaleService saleService) {
        this.productService = productService;
        this.personService = personService;
        this.saleService = saleService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int option = -1;
        while (option != 0) {
            printMenu();
            try {
                option = Integer.parseInt(scanner.nextLine());
                executeOption(option);
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid numerical option.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n==========================================");
        System.out.println("         GAMEZONE UNICESAR - SYSTEM       ");
        System.out.println("==========================================");
        System.out.println("--- Product Management ---");
        System.out.println("1. Register Videogame");
        System.out.println("2. Register Console");
        System.out.println("3. List All Products");
        System.out.println("--- Person Management ---");
        System.out.println("4. Register Customer");
        System.out.println("5. List All Customers");
        System.out.println("6. List All Sellers");
        System.out.println("--- Sales Management ---");
        System.out.println("7. Register Sale");
        System.out.println("8. List All Sales");
        System.out.println("9. View Customer Purchase History");
        System.out.println("10. View Seller Sales History");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    private void executeOption(int option) {
        switch (option) {
            case 1 -> registerVideogame();
            case 2 -> registerConsole();
            case 3 -> listProducts();
            case 4 -> registerCustomer();
            case 5 -> listCustomers();
            case 6 -> listSellers();
            case 7 -> registerSale();
            case 8 -> listAllSales();
            case 9 -> viewCustomerHistory();
            case 10 -> viewSellerHistory();
            case 0 -> System.out.println("Closing GameZone Unicesar application. Goodbye!");
            default -> System.out.println("Invalid option. Please try again.");
        }
    }

    private void registerVideogame() {
        System.out.println("\n--- Register Videogame ---");
        try {
            System.out.print("Enter ID: ");
            String id = scanner.nextLine();

            System.out.print("Enter Title: ");
            String title = scanner.nextLine();

            System.out.print("Enter Price: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Stock Quantity: ");
            int stockQuantity = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Platform: ");
            String platform = scanner.nextLine();

            System.out.print("Enter Genre: ");
            String genre = scanner.nextLine();

            System.out.print("Enter Age Rating: ");
            String ageRating = scanner.nextLine();

            Videogame videogame = new Videogame(id, title, price, stockQuantity, platform, genre, ageRating);
            productService.registerProduct(videogame);
            System.out.println("Videogame registered successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Error: Price and Stock Quantity must be valid numbers.");
        }
    }

    private void registerConsole() {
        System.out.println("\n--- Register Console ---");
        try {
            System.out.print("Enter ID: ");
            String id = scanner.nextLine();

            System.out.print("Enter Title: ");
            String title = scanner.nextLine();

            System.out.print("Enter Price: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Stock Quantity: ");
            int stockQuantity = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Brand: ");
            String brand = scanner.nextLine();

            System.out.print("Enter Model: ");
            String model = scanner.nextLine();

            System.out.print("Enter Generation: ");
            String generation = scanner.nextLine();

            Console console = new Console(id, title, price, stockQuantity, brand, model, generation);
            productService.registerProduct(console);
            System.out.println("Console registered successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Error: Price and Stock Quantity must be valid numbers.");
        }
    }

    private void listProducts() {
        System.out.println("\n--- Inventory Products ---");
        List<Product> products = productService.listProducts();
        if (products.isEmpty()) {
            System.out.println("No products registered yet.");
            return;
        }
        for (Product p : products) {
            System.out.println(p.getDescription() + " | Stock: " + p.getStockQuantity());
        }
    }

    private void registerCustomer() {
        System.out.println("\n--- Register Customer ---");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Identification: ");
        String identification = scanner.nextLine();

        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        boolean success = personService.registerCustomer(name, identification, phone, email);
        if (success) {
            System.out.println("Customer registered successfully!");
        } else {
            System.out.println("Failed to register customer. Name and Identification are required.");
        }
    }

    private void listCustomers() {
        System.out.println("\n--- Registered Customers ---");
        List<Customer> customers = personService.listCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers registered yet.");
            return;
        }
        for (Customer c : customers) {
            System.out.println(c.getRoleDescription() + " | ID: " + c.getIdentification()
                    + " | Phone: " + c.getPhone());
        }
    }

    private void listSellers() {
        System.out.println("\n--- Registered Sellers ---");
        List<Seller> sellers = personService.listSellers();
        if (sellers.isEmpty()) {
            System.out.println("No sellers registered.");
            return;
        }
        for (Seller s : sellers) {
            System.out.println(s.getRoleDescription() + " | ID: " + s.getIdentification()
                    + " | Phone: " + s.getPhone());
        }
    }

    private void registerSale() {
        System.out.println("\n--- Register New Sale ---");
        try {
            System.out.print("Enter Sale ID: ");
            String saleId = scanner.nextLine();

            System.out.print("Enter Customer ID: ");
            String customerId = scanner.nextLine();

            System.out.print("Enter Seller ID: ");
            String sellerId = scanner.nextLine();

            List<SaleDetail> details = new ArrayList<>();
            boolean addMore = true;

            while (addMore) {
                System.out.print("Enter Product ID: ");
                String productId = scanner.nextLine();

                System.out.print("Enter Quantity: ");
                int quantity = Integer.parseInt(scanner.nextLine());

                System.out.print("Enter Unit Price: ");
                double unitPrice = Double.parseDouble(scanner.nextLine());

                details.add(new SaleDetail(productId, quantity, unitPrice));

                System.out.print("Add another product to this sale? (y/n): ");
                String resp = scanner.nextLine();
                if (!resp.equalsIgnoreCase("y")) {
                    addMore = false;
                }
            }

            boolean success = saleService.registerSale(saleId, customerId, sellerId, details);
            if (success) {
                System.out.println("Sale registered successfully and saved to JSON!");
            } else {
                System.out.println("Failed to save sale.");
            }

        } catch (Exception e) {
            System.out.println("Error processing sale: " + e.getMessage());
        }
    }

    private void listAllSales() {
        System.out.println("\n--- Sales History ---");
        List<Sale> sales = saleService.getAllSales();
        if (sales.isEmpty()) {
            System.out.println("No sales registered yet.");
            return;
        }
        for (Sale s : sales) {
            System.out.println("ID: " + s.getSaleId() + " | Date: " + s.getDate() +
                    " | Customer: " + s.getCustomerId() + " | Seller: " + s.getSellerId() +
                    " | Total: $" + s.getTotal());
        }
    }

    private void viewCustomerHistory() {
        System.out.println("\n--- Customer Purchase History ---");
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();

        List<Sale> sales = saleService.getSalesByCustomer(customerId);
        if (sales.isEmpty()) {
            System.out.println("No sales found for Customer ID: " + customerId);
            return;
        }
        for (Sale s : sales) {
            System.out.println("Sale ID: " + s.getSaleId() + " | Date: " + s.getDate() + " | Total: $" + s.getTotal());
        }
    }

    private void viewSellerHistory() {
        System.out.println("\n--- Seller Sales History ---");
        System.out.print("Enter Seller ID: ");
        String sellerId = scanner.nextLine();

        List<Sale> sales = saleService.getSalesBySeller(sellerId);
        if (sales.isEmpty()) {
            System.out.println("No sales found handled by Seller ID: " + sellerId);
            return;
        }
        for (Sale s : sales) {
            System.out.println("Sale ID: " + s.getSaleId() + " | Date: " + s.getDate() + " | Total: $" + s.getTotal());
        }
    }
}