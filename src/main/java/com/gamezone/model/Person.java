package com.gamezone.model;

/**
 * Abstract base class representing a person interacting with the store.
 * Concrete subclasses (Customer, Seller) define specific behavior.
 */
public abstract class Person {

    private String name;
    private String identification;
    private String phone;

    /**
     * Creates a new person with the given common attributes.
     *
     * @param name the person's full name
     * @param identification the person's identification document number
     * @param phone the person's contact phone number
     */
    public Person(String name, String identification, String phone) {
        this.name = name;
        this.identification = identification;
        this.phone = phone;
    }

    /**
     * Returns this person's name.
     *
     * @return the person's name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates this person's name.
     *
     * @param name the new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns this person's identification document number.
     *
     * @return the identification number
     */
    public String getIdentification() {
        return identification;
    }

    /**
     * Updates this person's identification document number.
     *
     * @param identification the new identification number to set
     */
    public void setIdentification(String identification) {
        this.identification = identification;
    }

    /**
     * Returns this person's contact phone number.
     *
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Updates this person's contact phone number.
     *
     * @param phone the new phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns a short description of this person's role in the store.
     * Each subclass implements this according to its specific responsibilities.
     *
     * @return a description of this person's role
     */
    public abstract String getRoleDescription();
}