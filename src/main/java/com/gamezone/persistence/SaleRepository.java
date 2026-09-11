package com.gamezone.persistence;

import com.gamezone.model.Sale;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles JSON persistence for Sale objects.
 */
public class SaleRepository {
    private final String filePath = "src/main/data/sales.json";
    private final Gson gson;

    public SaleRepository() {
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setPrettyPrinting()
                .create();
    }

    /**
     * Saves a new sale into the JSON file.
     * @param sale The sale object to persist.
     * @return True if saved successfully, false otherwise.
     */
    public boolean save(Sale sale) {
        List<Sale> sales = findAll();
        sales.add(sale);
        return saveAll(sales);
    }

    /**
     * Reads all sales from the JSON file.
     * @return A list of sales.
     */
    public List<Sale> findAll() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Sale>>() {}.getType();
            List<Sale> sales = gson.fromJson(reader, listType);
            return sales != null ? sales : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private boolean saveAll(List<Sale> sales) {
        File file = new File(filePath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (Writer writer = new FileWriter(file)) {
            gson.toJson(sales, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}