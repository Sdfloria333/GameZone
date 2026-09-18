package com.gamezone.model.promotions;

import java.time.LocalDate;
import com.gamezone.model.sales.Sale;
import com.gamezone.model.sales.SaleDetail;

/**
 * Concrete promotion applying a percentage discount strictly to items matching
 * a target category.
 */
public class CategoryDiscount extends Promotion {
    private double percentage;
    private String targetCategory;

    public CategoryDiscount(String id, String name, LocalDate startDate, LocalDate endDate, double percentage,
            String targetCategory) {
        super(id, name, startDate, endDate);
        this.percentage = percentage;
        this.targetCategory = targetCategory;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getTargetCategory() {
        return targetCategory;
    }

    public void setTargetCategory(String targetCategory) {
        this.targetCategory = targetCategory;
    }

    @Override
    public double calculateDiscount(Sale sale) {
        if (sale == null || sale.getDetails() == null) {
            return 0.0;
        }

        double eligibleSubtotal = 0.0;

        // Sumamos solo el subtotal de los items que pertenecen a la categoría objetivo
        for (SaleDetail detail : sale.getDetails()) {
            if (targetCategory != null && targetCategory.equalsIgnoreCase(detail.getItemCategory())) {
                eligibleSubtotal += detail.getSubtotal();
            }
        }

        return eligibleSubtotal * (percentage / 100.0);
    }
}