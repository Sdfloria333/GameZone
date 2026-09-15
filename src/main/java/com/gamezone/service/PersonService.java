package com.gamezone.service;

import com.gamezone.model.persons.Customer;
import com.gamezone.model.persons.Seller;
import com.gamezone.persistence.PersonRepository;

import java.util.List;

public class PersonService {

    private final PersonRepository repository;
    private final List<Customer> customers;
    private final List<Seller> sellers;

    public PersonService() {
        this.repository = new PersonRepository();
        this.customers = repository.loadCustomers();
        this.sellers = repository.loadSellers();

        if (sellers.isEmpty()) {
            preloadSellers();
        }
    }

    private void preloadSellers() {
        sellers.add(new Seller("Ana Torres", "1001", "3001111111", "EMP001", "Morning"));
        sellers.add(new Seller("Carlos Ruiz", "1002", "3002222222", "EMP002", "Afternoon"));
        sellers.add(new Seller("Laura Gomez", "1003", "3003333333", "EMP003", "Evening"));
        repository.saveSellers(sellers);
    }

    public boolean registerCustomer(String name, String identification, String phone, String email) {
        if (name == null || name.isBlank() || identification == null || identification.isBlank()) {
            return false;
        }
        Customer customer = new Customer(name, identification, phone, email);
        customers.add(customer);
        return repository.saveCustomers(customers);
    }

    public List<Customer> listCustomers() {
        return customers;
    }

    public List<Seller> listSellers() {
        return sellers;
    }

    public Customer findCustomerByIdentification(String identification) {
        return customers.stream()
                .filter(c -> c.getIdentification().equalsIgnoreCase(identification))
                .findFirst()
                .orElse(null);
    }

    public Seller findSellerByIdentification(String identification) {
        return sellers.stream()
                .filter(s -> s.getIdentification().equalsIgnoreCase(identification))
                .findFirst()
                .orElse(null);
    }
}