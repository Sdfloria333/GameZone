package com.gamezone.model;

/**
 * Represents an item detail in a sale transaction.
 */
public class SaleDetail {
    private String productId;
    private int quantity;
    private double unitPrice;

    public SaleDetail(String productId, int quantity, double unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /**
     * Calculates the subtotal for this item detail.
     *
     * @return Subtotal amount.
     */
    public double getSubtotal() {
        return this.quantity * this.unitPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}