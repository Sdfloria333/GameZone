package com.gamezone;

import com.gamezone.persistence.ProductRepository;
import com.gamezone.persistence.SaleRepository;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;
import com.gamezone.ui.ConsoleUI;

/**
 * Main application entry point for GameZone Unicesar.
 */
public class Main {
    public static void main(String[] args) {
        ProductRepository productRepo = new ProductRepository("data/products.csv");
        SaleRepository saleRepo = new SaleRepository();

        ProductService productService = new ProductService(productRepo);
        PersonService personService = new PersonService();
        SaleService saleService = new SaleService(saleRepo, productService, personService);

        ConsoleUI consoleUI = new ConsoleUI(productService, personService, saleService);
        consoleUI.start();
    }
}