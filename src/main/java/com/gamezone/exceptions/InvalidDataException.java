package com.gamezone.exceptions;

/**
 * Thrown when an input value does not meet the required format, range, or
 * type: a negative price, a negative quantity, an empty string where content
 * is expected, among others.
 */
public class InvalidDataException extends GameZoneException {

    /**
     * Creates a new exception describing which field was invalid and why,
     * building a descriptive message in Spanish for the end user.
     *
     * @param fieldName         the name of the field that failed validation
     * @param problemDescription a description, in Spanish, of what is wrong with it
     */
    public InvalidDataException(String fieldName, String problemDescription) {
        super("El campo " + fieldName + " no es válido: " + problemDescription);
    }
}
