package com.gamezone.model.promotions;

import java.time.LocalDate;
import com.gamezone.model.sales.Sale;

/**
 * Concrete promotion applying a percentage discount over the entire sale
 * subtotal.
 */
public class PercentageDiscount extends Promotion {
    private double percentage;

    public PercentageDiscount(String id, String name, LocalDate startDate, LocalDate endDate, double percentage) {
        super(id, name, startDate, endDate);
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(Sale sale) {
        if (sale == null) {
            return 0.0;
        }
        return sale.getSubtotal() * (percentage / 100.0);
    }
}