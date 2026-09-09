package com.gamezone.persistence;

import com.gamezone.model.Product;
import com.gamezone.model.Videogame;
import com.gamezone.model.Console;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the persistence of products using a CSV file.
 * This class is responsible for saving and loading product data.
 */
public class ProductRepository {

    private String filePath;

    /**
     * Creates a new ProductRepository with the specified file path.
     *
     * @param filePath the path of the file used to store product data
     */
    public ProductRepository(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves all products to the CSV file.
     *
     * @param products the list of products to be saved
     */
    public void saveAll(List<Product> products) {
        File file = new File(filePath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Product product : products) {
                writer.println(toCsvLine(product));
            }
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    /**
     * Loads all products from the CSV file.
     *
     * @return a list containing all products stored in the file
     */
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return products;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    products.add(fromCsvLine(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }

        return products;
    }

    private String toCsvLine(Product product) {
        if (product instanceof Videogame) {
            Videogame v = (Videogame) product;
            return String.join(",", "VIDEOGAME", v.getId(), v.getTitle(),
                    String.valueOf(v.getPrice()), String.valueOf(v.getStockQuantity()),
                    v.getPlatform(), v.getGenre(), v.getAgeRating());
        } else if (product instanceof Console) {
            Console c = (Console) product;
            return String.join(",", "CONSOLE", c.getId(), c.getTitle(),
                    String.valueOf(c.getPrice()), String.valueOf(c.getStockQuantity()),
                    c.getBrand(), c.getModel(), c.getGeneration());
        }
        return "";
    }

    private Product fromCsvLine(String line) {
        String[] parts = line.split(",");
        String type = parts[0];
        String id = parts[1];
        String title = parts[2];
        double price = Double.parseDouble(parts[3]);
        int stockQuantity = Integer.parseInt(parts[4]);

        if (type.equals("VIDEOGAME")) {
            return new Videogame(id, title, price, stockQuantity, parts[5], parts[6], parts[7]);
        } else if (type.equals("CONSOLE")) {
            return new Console(id, title, price, stockQuantity, parts[5], parts[6], parts[7]);
        }
        return null;
    }
}


