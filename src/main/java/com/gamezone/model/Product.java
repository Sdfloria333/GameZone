package com.gamezone.model;

/**
 * Represents a generic product sold in the store.
 * This class defines the common attributes and behavior shared
 * by all product types (videogames, consoles, etc.).
 */
public abstract class Product {

    private String id;
    private String title;
    private double price;
    private int stockQuantity;

    public Product(String id, String title, double price, int stockQuantity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /**
     * Returns a full description of the product, including its
     * specific characteristics. Each subclass must implement this
     * according to its own attributes.
     */
    public abstract String getDescription();
}