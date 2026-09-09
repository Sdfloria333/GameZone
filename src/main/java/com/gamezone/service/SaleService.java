package com.gamezone.service;

import com.gamezone.model.Sale;
import com.gamezone.model.SaleDetail;
import com.gamezone.persistence.SaleRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service handling business rules for sales operations.
 */
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final PersonService personService;

    public SaleService(SaleRepository saleRepository, ProductService productService, PersonService personService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.personService = personService;
    }

    /**
     * Registers a new sale after validating business rules.
     *
     * @param saleId     Identifier for the sale.
     * @param customerId Identifier of the customer.
     * @param sellerId   Identifier of the seller.
     * @param details    List of product details.
     * @return True if sale was registered successfully.
     * @throws Exception If business constraints are violated.
     */
    public boolean registerSale(String saleId, String customerId, String sellerId, List<SaleDetail> details) throws Exception {
        if (details == null || details.isEmpty()) {
            throw new IllegalArgumentException("A sale must contain at least one product.");
        }

        for (SaleDetail detail : details) {
            if (!productService.hasEnoughStock(detail.getProductId(), detail.getQuantity())) {
                throw new IllegalStateException("Insufficient stock for product ID: " + detail.getProductId());
            }
        }

        for (SaleDetail detail : details) {
            productService.reduceStock(detail.getProductId(), detail.getQuantity());
        }

        Sale newSale = new Sale(saleId, new Date(), customerId, sellerId, details);
        return saleRepository.save(newSale);
    }

    /**
     * Retrieves all recorded sales.
     *
     * @return List of sales.
     */
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    /**
     * Retrieves sales history for a specific customer.
     *
     * @param customerId Customer identifier.
     * @return List of sales associated with the customer.
     */
    public List<Sale> getSalesByCustomer(String customerId) {
        List<Sale> filteredSales = new ArrayList<>();
        for (Sale sale : getAllSales()) {
            if (sale.getCustomerId().equalsIgnoreCase(customerId)) {
                filteredSales.add(sale);
            }
        }
        return filteredSales;
    }

    /**
     * Retrieves sales history handled by a specific seller.
     *
     * @param sellerId Seller identifier.
     * @return List of sales handled by the seller.
     */
    public List<Sale> getSalesBySeller(String sellerId) {
        List<Sale> filteredSales = new ArrayList<>();
        for (Sale sale : getAllSales()) {
            if (sale.getSellerId().equalsIgnoreCase(sellerId)) {
                filteredSales.add(sale);
            }
        }
        return filteredSales;
    }
}