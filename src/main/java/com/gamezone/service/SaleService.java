package com.gamezone.service;

import com.gamezone.model.promotions.Promotion;
import com.gamezone.model.sales.Sale;
import com.gamezone.model.sales.SaleDetail;
import com.gamezone.persistence.SaleRepository;
import java.util.ArrayList;
import java.util.List;

public class SaleService {

    private final SaleRepository repository;
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final PersonService personService;
    private final PromotionService promotionService;
    private final List<Sale> sales;

    // CONSTRUCTOR ACTUALIZADO: Inyecta PromotionService
    public SaleService(ProductService productService, AccessoryService accessoryService,
            PersonService personService, PromotionService promotionService) {
        this.repository = new SaleRepository();
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.personService = personService;
        this.promotionService = promotionService; // <-- Se asigna el servicio
        this.sales = repository.loadSales();
    }

    public boolean registerSale(String saleId, String customerId, String sellerId, List<SaleDetail> details) {
        if (saleId == null || saleId.isBlank() || details == null || details.isEmpty()) {
            return false;
        }

        // 1. Validar que la venta no exista previamente por ID
        for (Sale s : sales) {
            if (s.getId().equalsIgnoreCase(saleId)) {
                return false;
            }
        }

        // 2. Validar que existan el cliente y el vendedor
        if (personService.findCustomerByIdentification(customerId) == null) {
            return false;
        }

        if (personService.findSellerByIdentification(sellerId) == null) {
            return false;
        }

        // 3. Validar stock de forma unificada (Productos o Accesorios)
        for (SaleDetail detail : details) {
            boolean hasProductStock = productService.hasEnoughStock(detail.getProductId(), detail.getQuantity());
            boolean hasAccessoryStock = accessoryService.hasEnoughStock(detail.getProductId(), detail.getQuantity());

            if (!hasProductStock && !hasAccessoryStock) {
                return false; // Cancela la venta si no encuentra stock suficiente
            }
        }

        // 4. Reducir stock delegando al servicio correspondiente
        for (SaleDetail detail : details) {
            if (productService.findProductById(detail.getProductId()) != null) {
                productService.reduceStock(detail.getProductId(), detail.getQuantity());
            } else {
                accessoryService.updateStock(detail.getProductId(), detail.getQuantity());
            }
        }

        // 5. Crear la venta inicial
        Sale newSale = new Sale(saleId, java.time.LocalDate.now(), customerId, sellerId, details);

        // 6. CÁLCULO Y APLICACIÓN AUTOMÁTICA DE PROMOCIONES (REQUERIMIENTO DE LA GUÍA)
        if (promotionService != null) {
            Promotion bestPromo = promotionService.findBestPromotionFor(newSale);
            if (bestPromo != null) {
                double discount = bestPromo.calculateDiscount(newSale);
                newSale.setAppliedPromotionName(bestPromo.getName());
                newSale.setDiscountAmount(discount);
                newSale.setTotal(newSale.getSubtotal() - discount);
            } else {
                newSale.setAppliedPromotionName("Ninguna");
                newSale.setDiscountAmount(0.0);
                newSale.setTotal(newSale.getSubtotal());
            }
        }

        sales.add(newSale);
        return repository.saveSales(sales);
    }

    public List<Sale> listSales() {
        return sales;
    }

    public List<Sale> listSalesByCustomer(String customerId) {
        List<Sale> filtered = new ArrayList<>();
        for (Sale sale : sales) {
            if (sale.getCustomerId().equalsIgnoreCase(customerId)) {
                filtered.add(sale);
            }
        }
        return filtered;
    }

    public List<Sale> listSalesBySeller(String sellerId) {
        List<Sale> filtered = new ArrayList<>();
        for (Sale sale : sales) {
            if (sale.getSellerId().equalsIgnoreCase(sellerId)) {
                filtered.add(sale);
            }
        }
        return filtered;
    }
}