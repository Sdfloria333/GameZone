package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.persons.Customer;
import com.gamezone.model.persons.Seller;
import com.gamezone.model.products.Product;
import com.gamezone.model.sales.Sale;

import java.util.List;

/**
 * Validates data and business rules related to sales, before a service
 * registers a new sale. This class concentrates validation logic that used
 * to live inline inside {@code SaleService}.
 */
public class SaleValidator {

    private SaleValidator() {
        // Utility class: not meant to be instantiated.
    }

    /**
     * Validates the data required to register a new sale.
     * <p>
     * A missing customer or seller is treated as invalid input data (the
     * caller failed to provide a resolved customer/seller), while an empty
     * product list is treated as a business rule violation, since a sale
     * with no products is explicitly listed in the requirement as a
     * business rule example.
     *
     * @param customer the customer making the purchase, must not be null
     * @param seller   the seller registering the sale, must not be null
     * @param products the products included in the sale, must not be empty
     * @throws InvalidDataException  if the customer or the seller is null
     * @throws BusinessRuleException if the product list is null or empty
     */
    public static void validateSaleData(Customer customer, Seller seller, List<Product> products) {
        if (customer == null) {
            throw new InvalidDataException("cliente", "debe ser un cliente válido");
        }
        if (seller == null) {
            throw new InvalidDataException("vendedor", "debe ser un vendedor válido");
        }
        if (products == null || products.isEmpty()) {
            throw new BusinessRuleException("La venta debe incluir al menos un producto");
        }
    }

    /**
     * Validates that a sale lookup actually found a sale.
     *
     * @param sale   the sale resolved by the lookup, possibly null
     * @param saleId the identifier that was used in the search
     * @throws ResourceNotFoundException if the sale is null
     */
    public static void validateSaleExists(Sale sale, String saleId) {
        if (sale == null) {
            throw new ResourceNotFoundException("venta", saleId);
        }
    }
}
