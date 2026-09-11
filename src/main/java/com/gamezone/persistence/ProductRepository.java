package com.gamezone.persistence;

import com.gamezone.model.Console;
import com.gamezone.model.Product;
import com.gamezone.model.Videogame;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles JSON persistence for Product objects.
 */
@SuppressWarnings("unused")
public class ProductRepository {
    private static final String FILE_PATH = "src/main/data/products.json";
    private final Gson gson;

    public ProductRepository() {
        // Adaptador simple: detecta automáticamente si es Videogame o Console
        JsonDeserializer<Product> deserializer = (json, typeOfT, context) -> {
            JsonObject jsonObject = json.getAsJsonObject();

            // Si tiene 'platform' o 'genre', es un Videogame
            if (jsonObject.has("platform") || jsonObject.has("genre")) {
                return context.deserialize(jsonObject, Videogame.class);
            }
            // Si tiene 'brand' o 'model', es una Console
            else if (jsonObject.has("brand") || jsonObject.has("model")) {
                return context.deserialize(jsonObject, Console.class);
            }

            return null;
        };

        this.gson = new GsonBuilder()
                .registerTypeAdapter(Product.class, deserializer)
                .setPrettyPrinting()
                .create();
    }

    /**
     * Saves a new product into the JSON file.
     */
    public boolean save(Product product) {
        List<Product> products = findAll();
        products.add(product);
        return saveAll(products);
    }

    /**
     * Reads all products from the JSON file.
     */
    public List<Product> findAll() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Product>>() {}.getType();
            List<Product> products = gson.fromJson(reader, listType);

            if (products == null) return new ArrayList<>();
            products.removeIf(java.util.Objects::isNull);
            return products;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Saves all products to the JSON file.
     */
    public boolean saveAll(List<Product> products) {
        File file = new File(FILE_PATH);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (Writer writer = new FileWriter(file)) {
            gson.toJson(products, writer);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}