package com.gamezone;

import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;
import com.gamezone.service.AccessoryService;
import com.gamezone.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        // 1. Instancias los servicios base
        AccessoryService accessoryService = new AccessoryService();
        ProductService productService = new ProductService(accessoryService);
        PersonService personService = new PersonService();

        // 2. Se inyectan los 3 servicios a SaleService (AQUÍ ESTABA EL ERROR)
        SaleService saleService = new SaleService(productService, accessoryService, personService);

        // 3. Inicias la UI con sus referencias correspondientes
        ConsoleUI ui = new ConsoleUI(productService, personService, saleService, accessoryService);
        ui.start();
    }
}