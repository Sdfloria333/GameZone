package com.gamezone.persistence;

import com.gamezone.model.Customer;
import com.gamezone.model.Seller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles JSON persistence for Customer and Seller objects.
 */
public class PersonRepository {

    private static final String CUSTOMERS_FILE = "src/main/data/customers.json";
    private static final String SELLERS_FILE = "src/main/data/sellers.json";
    private final Gson gson;

    public PersonRepository() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Saves the list of customers to the JSON file.
     */
    public boolean saveCustomers(List<Customer> customers) {
        return saveToFile(CUSTOMERS_FILE, customers);
    }

    /**
     * Loads the list of customers from the JSON file.
     */
    public List<Customer> loadCustomers() {
        Type listType = new TypeToken<ArrayList<Customer>>() {}.getType();
        return loadFromFile(CUSTOMERS_FILE, listType);
    }

    /**
     * Saves the list of sellers to the JSON file.
     */
    public boolean saveSellers(List<Seller> sellers) {
        return saveToFile(SELLERS_FILE, sellers);
    }

    /**
     * Loads the list of sellers from the JSON file.
     */
    public List<Seller> loadSellers() {
        Type listType = new TypeToken<ArrayList<Seller>>() {}.getType();
        return loadFromFile(SELLERS_FILE, listType);
    }

    // Métodos auxiliares genéricos para no repetir código de I/O
    private <T> boolean saveToFile(String filePath, List<T> data) {
        File file = new File(filePath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(data, writer);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
            return false;
        }
    }

    private <T> List<T> loadFromFile(String filePath, Type listType) {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(file)) {
            List<T> data = gson.fromJson(reader, listType);
            return data != null ? data : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}