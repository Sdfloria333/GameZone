package com.gamezone.persistence;

import com.gamezone.model.persons.Customer;
import com.gamezone.model.persons.Seller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository {

    private static final String CUSTOMERS_FILE = "src/main/data/customers.json";
    private static final String SELLERS_FILE = "src/main/data/sellers.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public boolean saveCustomers(List<Customer> customers) {
        return saveToFile(CUSTOMERS_FILE, customers);
    }

    public List<Customer> loadCustomers() {
        Type listType = new TypeToken<ArrayList<Customer>>() {
        }.getType();
        return loadFromFile(CUSTOMERS_FILE, listType);
    }

    public boolean saveSellers(List<Seller> sellers) {
        return saveToFile(SELLERS_FILE, sellers);
    }

    public List<Seller> loadSellers() {
        Type listType = new TypeToken<ArrayList<Seller>>() {
        }.getType();
        return loadFromFile(SELLERS_FILE, listType);
    }

    private <T> boolean saveToFile(String path, List<T> data) {
        File file = new File(path);
        if (file.getParentFile() != null)
            file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(data, writer);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private <T> List<T> loadFromFile(String path, Type listType) {
        File file = new File(path);
        if (!file.exists())
            return new ArrayList<>();

        try (FileReader reader = new FileReader(file)) {
            List<T> data = gson.fromJson(reader, listType);
            return data != null ? data : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}