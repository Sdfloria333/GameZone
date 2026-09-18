package com.gamezone.model.warranties;

import com.gamezone.model.products.Product;
import com.gamezone.model.sales.Sale;

import java.time.LocalDate;

/**
 * Concrete warranty type covering factory defects and accidental damage
 * for 12 months, with an additional cost of 10% of the product price.
 */
public class ExtendedWarranty extends Warranty {

    /**
     * Creates a new extended warranty for the given product and sale.
     *
     * @param warrantyId unique identifier of the warranty
     * @param product product covered by this warranty
     * @param sale sale in which this warranty was generated
     * @param startDate date on which the warranty starts
     */
    public ExtendedWarranty(String warrantyId, Product product, Sale sale, LocalDate startDate) {
        super(warrantyId, product, sale, startDate);
    }

    @Override
    public int getDurationInMonths() {
        return 12;
    }

    @Override
    public String getWarrantyType() {
        return "Garantía Extendida";
    }

    @Override
    public double getAdditionalCost() {
        return getProduct().getPrice() * 0.10;
    }
}
