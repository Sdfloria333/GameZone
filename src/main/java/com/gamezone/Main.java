package com.gamezone;

import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;
import com.gamezone.ui.ConsoleUI;

public class Main {

    public static void main(String[] args) {
        ProductService productService = new ProductService();
        PersonService personService = new PersonService();
        SaleService saleService = new SaleService(productService, personService);

        ConsoleUI ui = new ConsoleUI(productService, personService, saleService);
        ui.start();
    }
}