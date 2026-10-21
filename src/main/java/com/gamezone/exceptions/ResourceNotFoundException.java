package com.gamezone.exceptions;

/**
 * Thrown when a requested resource (a product, a customer, a seller, a sale)
 * is looked up by its identifier and does not exist in the system.
 */
public class ResourceNotFoundException extends GameZoneException {

    /**
     * Creates a new exception describing which resource type and identifier
     * could not be found, building a descriptive message in Spanish for the
     * end user.
     *
     * @param resourceType the kind of resource that was searched for (e.g. "producto")
     * @param identifier   the identifier that was used in the search
     */
    public ResourceNotFoundException(String resourceType, String identifier) {
        super("El " + resourceType + " con identificador " + identifier + " no fue encontrado");
    }
}