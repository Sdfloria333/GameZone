package com.gamezone.exceptions;

/**
 * Thrown when an operation violates a business rule of the system, such as
 * insufficient stock, a duplicate customer or seller, or a sale with no
 * products.
 */
public class BusinessRuleException extends GameZoneException {

    /**
     * Creates a new exception describing which business rule was violated.
     *
     * @param message a description, in Spanish, of the rule that was violated
     */
    public BusinessRuleException(String message) {
        super(message);
    }
}
