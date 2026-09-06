
package com.gamezone.model;

/**
 * Represents a generic product sold in the store.
 * This class defines the common attributes and behavior shared
 * by all product types, such as videogames and consoles.
 */
public abstract class Product {

    private String id;
    private String title;
    private double price;
    private int stockQuantity;

    /**
     * Creates a new product with the specified information.
     *
     * @param id the unique identifier of the product
     * @param title the title of the product
     * @param price the price of the product
     * @param stockQuantity the available quantity in stock
     */
    public Product(String id, String title, double price, int stockQuantity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    /**
     * Gets the unique identifier of the product.
     *
     * @return the product identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the title of the product.
     *
     * @return the product title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the price of the product.
     *
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the available quantity of the product in stock.
     *
     * @return the available stock quantity
     */
    public int getStockQuantity() {
        return stockQuantity;
    }

    /**
     * Sets the available quantity of the product in stock.
     *
     * @param stockQuantity the new available stock quantity
     */
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /**
     * Returns a complete description of the product including
     * its specific characteristics.
     *
     * @return a formatted description of the product
     */
    public abstract String getDescription();
}
