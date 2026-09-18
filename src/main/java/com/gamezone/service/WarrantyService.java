package com.gamezone.service;

import com.gamezone.model.products.Product;
import com.gamezone.model.sales.Sale;
import com.gamezone.model.warranties.BasicWarranty;
import com.gamezone.model.warranties.ExtendedWarranty;
import com.gamezone.model.warranties.Warranty;
import com.gamezone.persistence.WarrantyRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides business logic for managing warranties: assignment,
 * validity queries, and expiration alerts.
 */
public class WarrantyService {

    private final WarrantyRepository repository;
    private final List<Warranty> warranties;

    /**
     * Creates a new WarrantyService with the specified repository.
     *
     * @param repository the repository used to persist warranties
     */
    public WarrantyService(WarrantyRepository repository) {
        this.repository = repository;
        this.warranties = repository.loadAll();
    }

    /**
     * Creates and persists an automatic basic warranty for the given
     * product and sale.
     *
     * @param product the product covered by the warranty
     * @param sale the sale in which the warranty was generated
     * @param startDate the date on which the warranty starts
     * @return the created basic warranty
     */
    public BasicWarranty assignBasicWarranty(Product product, Sale sale, LocalDate startDate) {
        String warrantyId = "W" + (warranties.size() + 1);
        BasicWarranty warranty = new BasicWarranty(warrantyId, product, sale, startDate);
        warranties.add(warranty);
        repository.saveAll(warranties);
        return warranty;
    }

    /**
     * Creates and persists an extended warranty for the given product and sale.
     *
     * @param product the product covered by the warranty
     * @param sale the sale in which the warranty was generated
     * @param startDate the date on which the warranty starts
     * @return the created extended warranty
     */
    public ExtendedWarranty assignExtendedWarranty(Product product, Sale sale, LocalDate startDate) {
        String warrantyId = "W" + (warranties.size() + 1);
        ExtendedWarranty warranty = new ExtendedWarranty(warrantyId, product, sale, startDate);
        warranties.add(warranty);
        repository.saveAll(warranties);
        return warranty;
    }

    /**
     * Finds the warranty associated with the given product within
     * the given sale.
     *
     * @param productId the identifier of the product
     * @param saleId the identifier of the sale
     * @return the matching warranty, or null if none is found
     */
    public Warranty findWarrantyByProduct(String productId, String saleId) {
        for (Warranty w : warranties) {
            if (w.getProduct().getId().equalsIgnoreCase(productId)
                    && w.getSale().getSaleId().equalsIgnoreCase(saleId)) {
                return w;
            }
        }
        return null;
    }

    /**
     * Retrieves all registered warranties.
     *
     * @return a list containing all warranties
     */
    public List<Warranty> listAllWarranties() {
        return warranties;
    }

    /**
     * Retrieves the warranties that are currently active (today's date
     * falls within their validity period).
     *
     * @return a list of currently active warranties
     */
    public List<Warranty> listActiveWarranties() {
        List<Warranty> active = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Warranty w : warranties) {
            if (w.isActive(today)) {
                active.add(w);
            }
        }
        return active;
    }

    /**
     * Retrieves the warranties whose end date falls within the given
     * number of days from today.
     *
     * @param daysAhead the number of days to look ahead
     * @return a list of warranties expiring soon
     */
    public List<Warranty> listWarrantiesExpiringSoon(int daysAhead) {
        List<Warranty> expiringSoon = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(daysAhead);

        for (Warranty w : warranties) {
            LocalDate endDate = w.getEndDate();
            if (!endDate.isBefore(today) && !endDate.isAfter(limit)) {
                expiringSoon.add(w);
            }
        }
        return expiringSoon;
    }
}
