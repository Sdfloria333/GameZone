package com.gamezone.model.warranties;

import com.gamezone.model.products.Product;
import com.gamezone.model.sales.Sale;

import java.time.LocalDate;

/**
 * Concrete warranty type covering only factory defects for 6 months,
 * with no additional cost.
 */
public class BasicWarranty extends Warranty {

    /**
     * Creates a new basic warranty for the given product and sale.
     *
     * @param warrantyId unique identifier of the warranty
     * @param product product covered by this warranty
     * @param sale sale in which this warranty was generated
     * @param startDate date on which the warranty starts
     */
    public BasicWarranty(String warrantyId, Product product, Sale sale, LocalDate startDate) {
        super(warrantyId, product, sale, startDate);
    }

    @Override
    public int getDurationInMonths() {
        return 6;
    }

    @Override
    public String getWarrantyType() {
        return "Garantía Básica";
    }

    @Override
    public double getAdditionalCost() {
        return 0.0;
    }
}
