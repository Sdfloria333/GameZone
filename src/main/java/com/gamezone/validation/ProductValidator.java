package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.products.Product;

/**
 * Validates data and business rules related to products, before a service
 * performs any operation that creates, reads, or modifies a product. This
 * class concentrates validation logic that used to live inline inside
 * {@code ProductService}.
 */
public class ProductValidator {

    private ProductValidator() {
        // Utility class: not meant to be instantiated.
    }

    /**
     * Validates the basic data required to register or update a product.
     *
     * @param id    the product identifier, must not be null or blank
     * @param title the product title, must not be null or blank
     * @param price the product price, must be positive
     * @param stock the available stock quantity, must not be negative
     * @throws InvalidDataException if any of the fields fail validation
     */
    public static void validateProductData(String id, String title, double price, int stock) {
        if (id == null || id.isBlank()) {
            throw new InvalidDataException("identificador", "no puede estar vacío");
        }
        if (title == null || title.isBlank()) {
            throw new InvalidDataException("título", "no puede estar vacío");
        }
        if (price <= 0) {
            throw new InvalidDataException("precio", "debe ser un valor positivo");
        }
        if (stock < 0) {
            throw new InvalidDataException("stock", "no puede ser negativo");
        }
    }

    /**
     * Validates that a product has enough stock to satisfy a requested
     * quantity.
     *
     * @param product           the product being checked
     * @param requestedQuantity the quantity requested for the operation
     * @throws BusinessRuleException if the available stock is insufficient
     */
    public static void validateStockAvailability(Product product, int requestedQuantity) {
        if (product.getStockQuantity() < requestedQuantity) {
            throw new BusinessRuleException(
                    "Stock insuficiente para el producto " + product.getId()
                            + " (disponible: " + product.getStockQuantity()
                            + ", solicitado: " + requestedQuantity + ")");
        }
    }

    /**
     * Validates that a product lookup actually found a product.
     *
     * @param product   the product resolved by the lookup, possibly null
     * @param productId the identifier that was used in the search
     * @throws ResourceNotFoundException if the product is null
     */
    public static void validateProductExists(Product product, String productId) {
        if (product == null) {
            throw new ResourceNotFoundException("producto", productId);
        }
    }
}
