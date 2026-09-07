package com.gamezone.model;

import java.util.Date;
import java.util.List;

/**
 * Represents a sale transaction in GameZone Unicesar.
 */
public class Sale {
    private String saleId;
    private Date date;
    private String customerId;
    private String sellerId;
    private List<SaleDetail> details;
    private double total;

    public Sale(String saleId, Date date, String customerId, String sellerId, List<SaleDetail> details) {
        this.saleId = saleId;
        this.date = date;
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.details = details;
        this.total = calculateTotal();
    }

    /**
     * Calculates the total amount of the sale based on its details.
     *
     * @return Total price of the sale.
     */
    public double calculateTotal() {
        double sum = 0.0;
        if (this.details != null) {
            for (SaleDetail detail : this.details) {
                sum += detail.getSubtotal();
            }
        }
        return sum;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public List<SaleDetail> getDetails() {
        return details;
    }

    public void setDetails(List<SaleDetail> details) {
        this.details = details;
        this.total = calculateTotal();
    }

    public double getTotal() {
        return total;
    }
}