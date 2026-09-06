package com.gamezone.model;

/**
 * Represents a customer who purchases products at the store.
 * A customer is identified additionally by an email address.
 */
public class Customer extends Person {

    private String email;

    public Customer(String name, String identification, String phone, String email) {
        super(name, identification, phone);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns a short description identifying this person as a customer.
     */
    @Override
    public String getRoleDescription() {
        return "Customer - " + getName() + " (" + email + ")";
    }
}