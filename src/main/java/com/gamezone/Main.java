package com.gamezone;

import com.gamezone.persistence.PromotionRepository;
import com.gamezone.persistence.ReturnRepository;
import com.gamezone.persistence.SaleRepository;
import com.gamezone.persistence.WarrantyRepository;
import com.gamezone.service.AccessoryService;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.PromotionService;
import com.gamezone.service.ReturnService;
import com.gamezone.service.SaleService;
import com.gamezone.service.WarrantyService;
import com.gamezone.ui.ConsoleUI;

public class Main {
        public static void main(String[] args) {
                // 1. Instancias de servicios y repositorios base
                PromotionRepository promotionRepository = new PromotionRepository();
                PromotionService promotionService = new PromotionService(promotionRepository);

                AccessoryService accessoryService = new AccessoryService();
                ProductService productService = new ProductService(accessoryService);
                PersonService personService = new PersonService();

// Warranty module: built before SaleService because it no longer depends on it
                SaleRepository saleRepository = new SaleRepository();
                WarrantyRepository warrantyRepository = new WarrantyRepository();
                WarrantyService warrantyService = new WarrantyService(warrantyRepository, saleRepository, productService);

// SaleService receives WarrantyService through its constructor
                SaleService saleService = new SaleService(productService, accessoryService,
                        personService, promotionService, warrantyService);

// Returns module (unchanged, needs saleService so it stays after it)
                ReturnRepository returnRepository = new ReturnRepository(saleService, productService);
                ReturnService returnService = new ReturnService(returnRepository, saleService, productService);

                // 3. Inicias la UI con sus referencias correspondientes (incluye devoluciones y
                // promociones)
                ConsoleUI ui = new ConsoleUI(productService, personService, saleService, accessoryService,
                                returnService,
                                promotionService, warrantyService);

                ui.start();
        }
}