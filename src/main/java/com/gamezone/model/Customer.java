package com.gamezone.model;

/**
 * Represents a customer who purchases products at the store.
 * A customer is identified additionally by an email address.
 */
public class Customer extends Person {

    private String email;

    /**
     * Creates a new customer with the given attributes.
     *
     * @param name the customer's full name
     * @param identification the customer's identification document number
     * @param phone the customer's contact phone number
     * @param email the customer's email address
     */
    public Customer(String name, String identification, String phone, String email) {
        super(name, identification, phone);
        this.email = email;
    }

    /**
     * Returns this customer's email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Updates this customer's email address.
     *
     * @param email the new email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns a short description identifying this person as a customer.
     *
     * @return a description including the customer's name and email
     */
    @Override
    public String getRoleDescription() {
        return "Customer - " + getName() + " (" + email + ")";
    }
}