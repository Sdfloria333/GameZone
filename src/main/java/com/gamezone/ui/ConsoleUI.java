package com.gamezone.ui;

import com.gamezone.model.accessories.*;
import com.gamezone.model.persons.*;
import com.gamezone.model.products.*;
import com.gamezone.model.returns.Return;
import com.gamezone.model.sales.*;
import com.gamezone.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final ProductService productService;
    private final PersonService personService;
    private final SaleService saleService;
    private final Scanner scanner;
    private final AccessoryService accessoryService;
    private final ReturnService returnService;

    public ConsoleUI(ProductService productService, PersonService personService, SaleService saleService,
            AccessoryService accessoryService, ReturnService returnService) {
        this.productService = productService;
        this.personService = personService;
        this.saleService = saleService;
        this.scanner = new Scanner(System.in);
        this.accessoryService = accessoryService;
        this.returnService = returnService;
    }

    public void start() {
        int option = -1;
        while (option != 0) {
            printMenu();
            try {
                option = Integer.parseInt(scanner.nextLine());
                executeOption(option);
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un numero valido.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n==========================================");
        System.out.println("          GAMEZONE UNICESAR - SYSTEM       ");
        System.out.println("==========================================");
        System.out.println("--- Gestion de Productos ---");
        System.out.println("1. Registrar Videojuego");
        System.out.println("2. Registrar Consola");
        System.out.println("3. Listar Todos los Productos");
        System.out.println("--- Gestion de Personas ---");
        System.out.println("4. Registrar Cliente");
        System.out.println("5. Listar Clientes");
        System.out.println("6. Listar Vendedores");
        System.out.println("--- Gestion de Ventas ---");
        System.out.println("7. Registrar Venta");
        System.out.println("8. Listar Todas las Ventas");
        System.out.println("9. Ver Historial de Compras de Cliente");
        System.out.println("10. Ver Historial de Ventas de Vendedor");
        System.out.println("--- Gestion de Accesorios ---");
        System.out.println("11. Registrar Accesorio");
        System.out.println("12. Listar Todos los Accesorios");
        System.out.println("13. Buscar Accesorios por Consola");
        System.out.println("--- Gestion de Devoluciones ---");
        System.out.println("16. Registrar Devolucion");
        System.out.println("16. Listar Todas las Devoluciones");
        System.out.println("17. Ver Devoluciones por Cliente");
        System.out.println("18. Ver Devoluciones por Venta");
        System.out.println("19. Consultar Balance Mensual");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opcion: ");
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
            case 11 -> registerAccessory();
            case 12 -> listAccessories();
            case 13 -> listAccessoriesByConsole();
            case 15 -> registerReturn();
            case 16 -> listAllReturns();
            case 17 -> listReturnsByCustomer();
            case 18 -> listReturnsBySale();
            case 19 -> viewMonthlyBalance();
            case 0 -> System.out.println("Saliendo del sistema GameZone. ¡Hasta luego!");
            default -> System.out.println("Opcion no valida. Intente de nuevo.");
        }
    }

    private void registerVideogame() {
        System.out.println("\n--- Registrar Videojuego ---");
        try {
            System.out.print("ID: ");
            String id = scanner.nextLine();

            System.out.print("Titulo: ");
            String title = scanner.nextLine();

            System.out.print("Precio: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Stock: ");
            int stock = Integer.parseInt(scanner.nextLine());

            System.out.print("Plataforma: ");
            String platform = scanner.nextLine();

            System.out.print("Genero: ");
            String genre = scanner.nextLine();

            System.out.print("Clasificacion de edad: ");
            String ageRating = scanner.nextLine();

            Videogame game = new Videogame(id, title, price, stock, platform, genre, ageRating);
            if (productService.registerVideogame(game)) {
                System.out.println("Videojuego registrado con exito.");
            } else {
                System.out.println("Error al registrar el videojuego (ID duplicado o datos invalidos).");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Precio y Stock deben ser valores numericos.");
        }
    }

    private void registerConsole() {
        System.out.println("\n--- Registrar Consola ---");
        try {
            System.out.print("ID: ");
            String id = scanner.nextLine();

            System.out.print("Titulo / Nombre: ");
            String title = scanner.nextLine();

            System.out.print("Precio: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Stock: ");
            int stock = Integer.parseInt(scanner.nextLine());

            System.out.print("Marca: ");
            String brand = scanner.nextLine();

            System.out.print("Modelo: ");
            String model = scanner.nextLine();

            System.out.print("Generacion: ");
            String generation = scanner.nextLine();

            Console console = new Console(id, title, price, stock, brand, model, generation);
            if (productService.registerConsole(console)) {
                System.out.println("Consola registrada con exito.");
            } else {
                System.out.println("Error al registrar la consola (ID duplicado o datos invalidos).");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Precio y Stock deben ser valores numericos.");
        }
    }

    private void listProducts() {
        System.out.println("\n--- Inventario de Productos ---");
        List<Product> products = productService.listAllProducts();
        if (products.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        for (Product p : products) {
            if (p != null) {
                System.out.println("[" + p.getId() + "] " + p.getTitle() + " | Precio: $" + p.getPrice() + " | Stock: "
                        + p.getStockQuantity());
            }
        }
    }

    private void registerCustomer() {
        System.out.println("\n--- Registrar Cliente ---");
        System.out.print("Nombre: ");
        String name = scanner.nextLine();

        System.out.print("Identificacion / Cédula: ");
        String identification = scanner.nextLine();

        System.out.print("Telefono: ");
        String phone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        if (personService.registerCustomer(name, identification, phone, email)) {
            System.out.println("Cliente registrado con exito.");
        } else {
            System.out.println("Error al registrar cliente (Campos obligatorios vacios o ID existente).");
        }
    }

    private void listCustomers() {
        System.out.println("\n--- Clientes Registrados ---");
        List<Customer> customers = personService.listCustomers();
        if (customers.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        for (Customer c : customers) {
            System.out.println("ID: " + c.getIdentification() + " | Nombre: " + c.getName() + " | Tel: " + c.getPhone()
                    + " | Email: " + c.getEmail());
        }
    }

    private void listSellers() {
        System.out.println("\n--- Vendedores Registrados ---");
        List<Seller> sellers = personService.listSellers();
        if (sellers.isEmpty()) {
            System.out.println("No hay vendedores registrados.");
            return;
        }
        for (Seller s : sellers) {
            System.out.println(
                    "ID: " + s.getIdentification() + " | Nombre: " + s.getName() + " | Turno: " + s.getShift());
        }
    }

    private void registerSale() {
        System.out.println("\n--- Registrar Venta ---");
        try {
            System.out.print("ID Venta: ");
            String saleId = scanner.nextLine();

            System.out.print("Identificacion Cliente: ");
            String customerId = scanner.nextLine();

            System.out.print("Identificacion Vendedor: ");
            String sellerId = scanner.nextLine();

            List<SaleDetail> details = new ArrayList<>();
            boolean addMore = true;

            while (addMore) {
                System.out.print("ID Producto o Accesorio: ");
                String itemId = scanner.nextLine();

                System.out.print("Cantidad: ");
                int quantity = Integer.parseInt(scanner.nextLine());

                double price = 0.0;
                boolean found = false;

                // 1. Intentar buscar como Producto (Videojuego/Consola)
                Product product = productService.findProductById(itemId);
                if (product != null) {
                    price = product.getPrice();
                    found = true;
                } else {
                    // 2. Si no existe como producto, intentar buscar como Accesorio
                    Accessory accessory = accessoryService.findById(itemId);
                    if (accessory != null) {
                        price = accessory.getPrice();
                        found = true;
                    }
                }

                // Si no se encontró en ninguna de las dos listas
                if (!found) {
                    System.out
                            .println("El ID ingresado no corresponde a ningún producto o accesorio. Intente de nuevo.");
                    continue;
                }

                // Guardar el detalle usando el precio capturado
                details.add(new SaleDetail(itemId, quantity, price));

                System.out.print("¿Agregar otro item? (s/n): ");
                String resp = scanner.nextLine();
                if (!resp.equalsIgnoreCase("s")) {
                    addMore = false;
                }
            }

            if (saleService.registerSale(saleId, customerId, sellerId, details)) {
                System.out.println("Venta registrada y guardada con exito.");
            } else {
                System.out.println("Error al procesar la venta. Verifique cliente, vendedor o stock disponible.");
            }

        } catch (Exception e) {
            System.out.println("Error en los datos ingresados: " + e.getMessage());
        }
    }

    private void listAllSales() {
        System.out.println("\n--- Historial General de Ventas ---");
        List<Sale> sales = saleService.listSales();
        if (sales.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        for (Sale s : sales) {
            System.out.println("Venta ID: " + s.getSaleId() + " | Fecha: " + s.getDate() + " | Cliente ID: "
                    + s.getCustomerId() + " | Vendedor ID: " + s.getSellerId() + " | Total: $" + s.getTotal());
        }
    }

    private void viewCustomerHistory() {
        System.out.println("\n--- Historial por Cliente ---");
        System.out.print("Identificacion del Cliente: ");
        String customerId = scanner.nextLine();

        List<Sale> sales = saleService.listSalesByCustomer(customerId);
        if (sales.isEmpty()) {
            System.out.println("No se encontraron ventas para el cliente especificado.");
            return;
        }
        for (Sale s : sales) {
            System.out
                    .println("Venta ID: " + s.getSaleId() + " | Fecha: " + s.getDate() + " | Total: $" + s.getTotal());
        }
    }

    private void viewSellerHistory() {
        System.out.println("\n--- Historial por Vendedor ---");
        System.out.print("Identificacion del Vendedor: ");
        String sellerId = scanner.nextLine();

        List<Sale> sales = saleService.listSalesBySeller(sellerId);
        if (sales.isEmpty()) {
            System.out.println("No se encontraron ventas asociadas a este vendedor.");
            return;
        }
        for (Sale s : sales) {
            System.out
                    .println("Venta ID: " + s.getSaleId() + " | Fecha: " + s.getDate() + " | Total: $" + s.getTotal());
        }
    }

    private void registerAccessory() {
        System.out.println("\n--- Registrar Accesorio ---");
        try {
            System.out.print("Tipo (1. Control | 2. Cable | 3. Memoria): ");
            int type = Integer.parseInt(scanner.nextLine());

            System.out.print("ID: ");
            String id = scanner.nextLine();
            System.out.print("Titulo: ");
            String title = scanner.nextLine();
            System.out.print("Precio: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Stock: ");
            int stock = Integer.parseInt(scanner.nextLine());
            System.out.print("ID Consola Compatible: ");
            List<String> compat = List.of(scanner.nextLine());

            Accessory acc = null;
            if (type == 1) {
                System.out.print("Tipo Conexion: ");
                acc = new Controller(id, title, price, stock, compat, scanner.nextLine());
            } else if (type == 2) {
                System.out.print("Longitud (metros): ");
                double len = Double.parseDouble(scanner.nextLine());
                System.out.print("Tipo Conector: ");
                acc = new Cable(id, title, price, stock, compat, len, scanner.nextLine());
            } else if (type == 3) {
                System.out.print("Capacidad (GB): ");
                int cap = Integer.parseInt(scanner.nextLine());
                System.out.print("Tipo Memoria: ");
                acc = new Memory(id, title, price, stock, compat, cap, scanner.nextLine());
            }

            if (acc != null && accessoryService.addAccessory(acc)) {
                System.out.println("Accesorio registrado con exito.");
            } else {
                System.out.println("Error al registrar accesorio.");
            }
        } catch (Exception e) {
            System.out.println("Error en el formato de datos ingresados.");
        }
    }

    private void listAccessories() {
        System.out.println("\n--- Accesorios Registrados ---");
        List<Accessory> accessories = accessoryService.listAccessories();
        if (accessories.isEmpty()) {
            System.out.println("No hay accesorios registrados.");
            return;
        }
        for (Accessory a : accessories) {
            System.out.println(a.getDescription());
        }
    }

    private void listAccessoriesByConsole() {
        System.out.println("\n--- Accesorios por Consola ---");
        System.out.print("ID de la Consola: ");
        String consoleId = scanner.nextLine();

        List<Accessory> accessories = accessoryService.findByConsole(consoleId);
        if (accessories.isEmpty()) {
            System.out.println("No hay accesorios compatibles con esta consola.");
            return;
        }
        for (Accessory a : accessories) {
            System.out.println(a.getDescription());
        }
    }

    private void registerReturn() {
        System.out.println("\n--- Registrar Devolucion ---");
        try {
            System.out.print("ID de la Venta original: ");
            String saleId = scanner.nextLine();

            List<String> productIds = new ArrayList<>();
            boolean addMore = true;

            while (addMore) {
                System.out.print("ID Producto a devolver: ");
                productIds.add(scanner.nextLine());

                System.out.print("¿Agregar otro producto? (s/n): ");
                String resp = scanner.nextLine();
                if (!resp.equalsIgnoreCase("s")) {
                    addMore = false;
                }
            }

            System.out.print("Motivo de la devolucion: ");
            String reason = scanner.nextLine();

            Return newReturn = returnService.registerReturn(saleId, productIds, reason);
            System.out.println(newReturn.generateReturnReceipt());

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error en los datos ingresados: " + e.getMessage());
        }
    }

    private void listAllReturns() {
        System.out.println("\n--- Todas las Devoluciones ---");
        List<Return> returns = returnService.viewAllReturns();
        if (returns.isEmpty()) {
            System.out.println("No hay devoluciones registradas.");
            return;
        }
        for (Return r : returns) {
            System.out.println(r.generateReturnReceipt());
        }
    }

    private void listReturnsByCustomer() {
        System.out.println("\n--- Devoluciones por Cliente ---");
        System.out.print("Identificacion del Cliente: ");
        String customerId = scanner.nextLine();

        List<Return> returns = returnService.viewReturnsByCustomer(customerId);
        if (returns.isEmpty()) {
            System.out.println("No se encontraron devoluciones para este cliente.");
            return;
        }
        for (Return r : returns) {
            System.out.println(r.generateReturnReceipt());
        }
    }

    private void listReturnsBySale() {
        System.out.println("\n--- Devoluciones por Venta ---");
        System.out.print("ID de la Venta: ");
        String saleId = scanner.nextLine();

        List<Return> returns = returnService.viewReturnsBySale(saleId);
        if (returns.isEmpty()) {
            System.out.println("No se encontraron devoluciones para esta venta.");
            return;
        }
        for (Return r : returns) {
            System.out.println(r.generateReturnReceipt());
        }
    }

    private void viewMonthlyBalance() {
        System.out.println("\n--- Balance Mensual ---");
        try {
            System.out.print("Mes (1-12): ");
            int month = Integer.parseInt(scanner.nextLine());
            System.out.print("Año: ");
            int year = Integer.parseInt(scanner.nextLine());

            double balance = returnService.generateMonthlyBalance(month, year);
            System.out.println("Balance neto para " + month + "/" + year + ": $" + balance);
        } catch (NumberFormatException e) {
            System.out.println("Error: Mes y Año deben ser valores numericos.");
        }
    }

}