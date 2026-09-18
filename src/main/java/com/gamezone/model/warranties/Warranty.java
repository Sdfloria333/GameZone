package com.gamezone.model.warranties;

import com.gamezone.model.products.Product;
import com.gamezone.model.sales.Sale;

import java.time.LocalDate;

/**
 * Represents a warranty associated with a product sold in a sale.
 * Concrete subclasses define duration, type, and additional cost.
 */
public abstract class Warranty {

    private String warrantyId;
    private Product product;
    private Sale sale;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Creates a new warranty and calculates its end date automatically
     * based on the concrete subclass duration.
     *
     * @param warrantyId unique identifier of the warranty
     * @param product product covered by this warranty
     * @param sale sale in which this warranty was generated
     * @param startDate date on which the warranty starts
     */
    public Warranty(String warrantyId, Product product, Sale sale, LocalDate startDate) {
        this.warrantyId = warrantyId;
        this.product = product;
        this.sale = sale;
        this.startDate = startDate;
        this.endDate = startDate.plusMonths(getDurationInMonths());
    }

    public String getWarrantyId() {
        return warrantyId;
    }

    public Product getProduct() {
        return product;
    }

    public Sale getSale() {
        return sale;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @return the duration of this warranty type, in months
     */
    public abstract int getDurationInMonths();

    /**
     * @return the display name of this warranty type
     */
    public abstract String getWarrantyType();

    /**
     * @return the additional cost this warranty adds to the sale
     */
    public abstract double getAdditionalCost();

    /**
     * Checks whether the warranty is active on the given date.
     *
     * @param date the date to check
     * @return true if the date is within [startDate, endDate]
     */
    public boolean isActive(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    /**
     * Generates a formatted certificate description in Spanish,
     * since it is displayed directly to the user.
     *
     * @return a formatted warranty certificate string
     */
    public String generateWarrantyCertificate() {
        return "Garantía: " + getWarrantyType()
                + " | Producto: " + product.getTitle()
                + " | Vigente desde " + startDate + " hasta " + endDate;
    }
}
