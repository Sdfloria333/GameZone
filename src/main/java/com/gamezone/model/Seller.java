package com.gamezone.model;

/**
 * Represents a seller (employee) who attends customers and registers sales.
 * A seller is identified additionally by an employee code and an assigned shift.
 */
public class Seller extends Person {

    private String employeeCode;
    private String shift;

    /**
     * Creates a new seller with the given attributes.
     *
     * @param name the seller's full name
     * @param identification the seller's identification document number
     * @param phone the seller's contact phone number
     * @param employeeCode the seller's employee code
     * @param shift the seller's assigned work shift
     */
    public Seller(String name, String identification, String phone,
                  String employeeCode, String shift) {
        super(name, identification, phone);
        this.employeeCode = employeeCode;
        this.shift = shift;
    }

    /**
     * Returns this seller's employee code.
     *
     * @return the employee code
     */
    public String getEmployeeCode() {
        return employeeCode;
    }

    /**
     * Updates this seller's employee code.
     *
     * @param employeeCode the new employee code to set
     */
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    /**
     * Returns this seller's assigned work shift.
     *
     * @return the work shift
     */
    public String getShift() {
        return shift;
    }

    /**
     * Updates this seller's assigned work shift.
     *
     * @param shift the new work shift to set
     */
    public void setShift(String shift) {
        this.shift = shift;
    }

    /**
     * Returns a short description identifying this person as a seller.
     *
     * @return a description including the seller's name, employee code, and shift
     */
    @Override
    public String getRoleDescription() {
        return "Seller - " + getName() + " (code: " + employeeCode + ", shift: " + shift + ")";
    }
}