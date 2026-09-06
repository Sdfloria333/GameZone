package com.gamezone.model;

/**
 * Abstract base class representing a person interacting with the store.
 * Concrete subclasses (Customer, Seller) define specific behavior.
 */
public abstract class Person {

    private String name;
    private String identification;
    private String phone;

    public Person(String name, String identification, String phone) {
        this.name = name;
        this.identification = identification;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns a short description of this person's role in the store.
     * Each subclass implements this according to its specific responsibilities.
     */
    public abstract String getRoleDescription();
}