package com.gamezone.service;

import com.gamezone.model.sales.Sale;
import com.gamezone.model.sales.SaleDetail;
import com.gamezone.persistence.SaleRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaleService {

    private final SaleRepository repository;
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final PersonService personService;
    private final List<Sale> sales;

    // Inyección de AccessoryService añadida
    public SaleService(ProductService productService, AccessoryService accessoryService, PersonService personService) {
        this.repository = new SaleRepository();
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.personService = personService;
        this.sales = repository.loadSales();
    }

    public boolean registerSale(String saleId, String customerId, String sellerId, List<SaleDetail> details) {
        if (saleId == null || saleId.isBlank() || details == null || details.isEmpty()) {
            return false;
        }

        // 1. Validar que la venta no exista previamente por ID
        for (Sale s : sales) {
            if (s.getSaleId().equalsIgnoreCase(saleId)) {
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
                return false; // Cancela la venta si no encuentra stock en ninguna categoría
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

        Sale newSale = new Sale(saleId, new Date(), customerId, sellerId, details);
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