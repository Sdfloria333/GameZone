package com.gamezone.model;

/**
 * Represents a seller (employee) who attends customers and registers sales.
 * A seller is identified additionally by an employee code and an assigned shift.
 */
public class Seller extends Person {

    private String employeeCode;
    private String shift;

    public Seller(String name, String identification, String phone,
                  String employeeCode, String shift) {
        super(name, identification, phone);
        this.employeeCode = employeeCode;
        this.shift = shift;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    /**
     * Returns a short description identifying this person as a seller.
     */
    @Override
    public String getRoleDescription() {
        return "Seller - " + getName() + " (code: " + employeeCode + ", shift: " + shift + ")";
    }
}