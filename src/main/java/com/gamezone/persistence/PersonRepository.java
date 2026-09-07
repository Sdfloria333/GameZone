package com.gamezone.persistence;

import com.gamezone.model.Customer;
import com.gamezone.model.Seller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading Customer and Seller data to and from text files.
 * This class is the only one allowed to perform file I/O for the person module.
 */
public class PersonRepository {

    private static final String CUSTOMERS_FILE = "data/customers.txt";
    private static final String SELLERS_FILE = "data/sellers.txt";

    /**
     * Saves the given list of customers to the customers file.
     * Each line represents one customer, fields separated by commas.
     */
    public void saveCustomers(List<Customer> customers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (Customer c : customers) {
                writer.println(c.getName() + "," + c.getIdentification() + ","
                        + c.getPhone() + "," + c.getEmail());
            }
        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }

    /**
     * Loads the list of customers from the customers file.
     * Returns an empty list if the file does not exist yet.
     */
    public List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();
        File file = new File(CUSTOMERS_FILE);
        if (!file.exists()) {
            return customers;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Customer customer = new Customer(parts[0], parts[1], parts[2], parts[3]);
                customers.add(customer);
            }
        } catch (IOException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
        return customers;
    }

    /**
     * Saves the given list of sellers to the sellers file.
     */
    public void saveSellers(List<Seller> sellers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SELLERS_FILE))) {
            for (Seller s : sellers) {
                writer.println(s.getName() + "," + s.getIdentification() + ","
                        + s.getPhone() + "," + s.getEmployeeCode() + "," + s.getShift());
            }
        } catch (IOException e) {
            System.out.println("Error saving sellers: " + e.getMessage());
        }
    }

    /**
     * Loads the list of sellers from the sellers file.
     * Returns an empty list if the file does not exist yet.
     */
    public List<Seller> loadSellers() {
        List<Seller> sellers = new ArrayList<>();
        File file = new File(SELLERS_FILE);
        if (!file.exists()) {
            return sellers;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Seller seller = new Seller(parts[0], parts[1], parts[2], parts[3], parts[4]);
                sellers.add(seller);
            }
        } catch (IOException e) {
            System.out.println("Error loading sellers: " + e.getMessage());
        }
        return sellers;
    }
}