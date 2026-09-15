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
    private final PersonService personService;
    private final List<Sale> sales;

    public SaleService(ProductService productService, PersonService personService) {
        this.repository = new SaleRepository();
        this.productService = productService;
        this.personService = personService;
        this.sales = repository.loadSales();
    }

    public boolean registerSale(String saleId, String customerId, String sellerId, List<SaleDetail> details) {
        if (saleId == null || saleId.isBlank() || details == null || details.isEmpty()) {
            return false;
        }

        if (personService.findCustomerByIdentification(customerId) == null) {
            return false;
        }

        if (personService.findSellerByIdentification(sellerId) == null) {
            return false;
        }

        for (SaleDetail detail : details) {
            if (!productService.hasEnoughStock(detail.getProductId(), detail.getQuantity())) {
                return false;
            }
        }

        for (SaleDetail detail : details) {
            productService.reduceStock(detail.getProductId(), detail.getQuantity());
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