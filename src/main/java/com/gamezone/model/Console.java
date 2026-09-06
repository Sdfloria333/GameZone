package com.gamezone.model;

/**
 * Represents a console product available in the GameZone store.
 * A console has a brand, model, and generation.
 */
public class Console extends Product {

    private String brand;
    private String model;
    private String generation;

    /**
     * Creates a new Console with the specified product information.
     *
     * @param id the unique identifier of the console
     * @param title the title of the console
     * @param price the price of the console
     * @param stockQuantity the available quantity in stock
     * @param brand the brand of the console
     * @param model the model of the console
     * @param generation the generation of the console
     */
    public Console(String id, String title, double price, int stockQuantity,
                   String brand, String model, String generation) {
        super(id, title, price, stockQuantity);
        this.brand = brand;
        this.model = model;
        this.generation = generation;
    }

    /**
     * Gets the brand of the console.
     *
     * @return the console brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Gets the model of the console.
     *
     * @return the console model
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets the generation of the console.
     *
     * @return the console generation
     */
    public String getGeneration() {
        return generation;
    }

    /**
     * Returns a complete description of the console including its
     * brand, model, generation, and price.
     *
     * @return a formatted description of the console
     */
    @Override
    public String getDescription() {
        return getTitle() + " - " + brand + " " + model
                + " (" + generation + ") - $" + getPrice();
    }
}
