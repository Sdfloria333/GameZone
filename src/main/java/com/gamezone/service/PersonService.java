package com.gamezone.service;

import com.gamezone.model.Customer;
import com.gamezone.model.Seller;
import com.gamezone.persistence.PersonRepository;

import java.util.List;

/**
 * Provides business rules for managing customers and sellers.
 * This is the only class in the person module allowed to interact
 * with PersonRepository.
 */
public class PersonService {

    private PersonRepository repository;
    private List<Customer> customers;
    private List<Seller> sellers;

    public PersonService() {
        this.repository = new PersonRepository();
        this.customers = repository.loadCustomers();
        this.sellers = repository.loadSellers();

        // If no sellers exist yet (first run), preload three sellers.
        if (sellers.isEmpty()) {
            preloadSellers();
        }
    }

    /**
     * Preloads three sellers already hired by the store.
     * This reflects that sellers are not registered through the UI.
     */
    private void preloadSellers() {
        sellers.add(new Seller("Ana Torres", "1001", "3001111111", "EMP001", "Morning"));
        sellers.add(new Seller("Carlos Ruiz", "1002", "3002222222", "EMP002", "Afternoon"));
        sellers.add(new Seller("Laura Gomez", "1003", "3003333333", "EMP003", "Evening"));
        repository.saveSellers(sellers);
    }

    /**
     * Registers a new customer after validating required fields.
     * Returns true if the customer was registered successfully.
     */
    public boolean registerCustomer(String name, String identification, String phone, String email) {
        if (name == null || name.isBlank() || identification == null || identification.isBlank()) {
            return false;
        }
        Customer customer = new Customer(name, identification, phone, email);
        customers.add(customer);
        repository.saveCustomers(customers);
        return true;
    }

    /**
     * Returns the list of all registered customers.
     */
    public List<Customer> listCustomers() {
        return customers;
    }

    /**
     * Returns the list of all registered sellers.
     */
    public List<Seller> listSellers() {
        return sellers;
    }
}