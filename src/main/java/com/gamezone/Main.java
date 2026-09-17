package com.gamezone;

import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;
import com.gamezone.service.AccessoryService;
import com.gamezone.ui.ConsoleUI;

public class Main {

    public static void main(String[] args) {
        ProductService productService = new ProductService();
        PersonService personService = new PersonService();
        SaleService saleService = new SaleService(productService, personService);
        AccessoryService accessoryService = new AccessoryService();

        ConsoleUI ui = new ConsoleUI(productService, personService, saleService, accessoryService);
        ui.start();
    }
}