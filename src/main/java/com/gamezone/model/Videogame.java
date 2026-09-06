
package com.gamezone.model;

/**
 * Represents a videogame product available in the GameZone store.
 * A videogame has a platform, genre, and age rating.
 */
public class Videogame extends Product {

    private String platform;
    private String genre;
    private String ageRating;

    /**
     * Creates a new Videogame with the specified product information.
     *
     * @param id the unique identifier of the videogame
     * @param title the title of the videogame
     * @param price the price of the videogame
     * @param stockQuantity the available quantity in stock
     * @param platform the platform where the videogame can be played
     * @param genre the genre of the videogame
     * @param ageRating the age rating of the videogame
     */
    public Videogame(String id, String title, double price, int stockQuantity,
                     String platform, String genre, String ageRating) {
        super(id, title, price, stockQuantity);
        this.platform = platform;
        this.genre = genre;
        this.ageRating = ageRating;
    }

    /**
     * Gets the platform where the videogame can be played.
     *
     * @return the videogame platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Gets the genre of the videogame.
     *
     * @return the videogame genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Gets the age rating of the videogame.
     *
     * @return the age rating of the videogame
     */
    public String getAgeRating() {
        return ageRating;
    }

    /**
     * Returns a complete description of the videogame including its
     * platform, genre, age rating, and price.
     *
     * @return a formatted description of the videogame
     */
    @Override
    public String getDescription() {
        return getTitle() + " (" + platform + ") - Genre: " + genre
                + " - Age rating: " + ageRating + " - $" + getPrice();
    }
}

