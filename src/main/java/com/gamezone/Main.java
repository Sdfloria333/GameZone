package com.gamezone;

import com.gamezone.persistence.PromotionRepository;
import com.gamezone.service.AccessoryService;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
 feature/return-module
import com.gamezone.service.ReturnService;
import com.gamezone.service.SaleService;
import com.gamezone.persistence.ReturnRepository;
import com.gamezone.service.AccessoryService;

import com.gamezone.service.PromotionService;
import com.gamezone.service.SaleService;
 develop
import com.gamezone.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        // 1. Instancias de servicios y repositorios base
        PromotionRepository promotionRepository = new PromotionRepository();
        PromotionService promotionService = new PromotionService(promotionRepository);

        AccessoryService accessoryService = new AccessoryService();
        ProductService productService = new ProductService(accessoryService);
        PersonService personService = new PersonService();

        // 2. SaleService recibe los 4 servicios (incluyendo PromotionService)
        SaleService saleService = new SaleService(productService, accessoryService, personService, promotionService);

 feature/return-module
        // Se agrega el módulo de devoluciones
        ReturnRepository returnRepository = new ReturnRepository(saleService, productService);
        ReturnService returnService = new ReturnService(returnRepository, saleService, productService);

        // 3. Inicias la UI con sus referencias correspondientes
        ConsoleUI ui = new ConsoleUI(productService, personService, saleService, accessoryService, returnService);

        // 3. ConsoleUI recibe los 5 servicios
        ConsoleUI ui = new ConsoleUI(productService, personService, saleService, accessoryService, promotionService);
 develop
        ui.start();
    }
}