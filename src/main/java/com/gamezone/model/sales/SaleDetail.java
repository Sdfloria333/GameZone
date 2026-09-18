package com.gamezone.model.sales;

/**
 * Represents an item detail in a sale transaction.
 */
public class SaleDetail {
    private String productId;
    private String itemCategory;
    private int quantity;
    private double unitPrice;

    public SaleDetail(String productId, String itemCategory, int quantity, double unitPrice) {
        this.productId = productId;
        this.itemCategory = itemCategory;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public double getSubtotal() {
        return this.quantity * this.unitPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getItemCategory() { 
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) { 
        this.itemCategory = itemCategory;
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