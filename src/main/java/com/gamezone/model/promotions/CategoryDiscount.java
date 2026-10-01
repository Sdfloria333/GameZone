package com.gamezone.model.promotions;

import java.time.LocalDate;
import java.util.List;
import com.gamezone.model.sales.Sale;
import com.gamezone.model.sales.SaleDetail;

/**
 * Concrete promotion applying a percentage discount strictly to items matching
 * a target category. Supports "VIDEOGAME", "CONSOLE", and the wildcard
 * category "ACCESSORY", which matches any accessory sub-category
 * (controller, cable, or memory).
 */
public class CategoryDiscount extends Promotion {

    /**
     * The item categories that identify an accessory in a sale detail, plus
     * the literal "ACCESSORY" tag itself, in case an item is ever stored
     * under that exact category.
     */
    private static final List<String> ACCESSORY_ITEM_CATEGORIES = List.of("CONTROLLER", "CABLE", "MEMORY",
            "ACCESSORY");

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

    /**
     * Calculates the discount for this promotion by summing the subtotal of
     * every sale item matching the target category, then applying the
     * percentage. When the target category is "ACCESSORY", every item sold
     * under any accessory sub-category (controller, cable, or memory) is
     * treated as a match, since accessories are stored with their specific
     * sub-category rather than the generic "ACCESSORY" tag.
     *
     * @param sale the sale to evaluate
     * @return the discount amount in currency, or 0.0 if nothing matches
     */
    @Override
    public double calculateDiscount(Sale sale) {
        if (sale == null || sale.getDetails() == null) {
            return 0.0;
        }

        double eligibleSubtotal = 0.0;

        // Sumamos solo el subtotal de los items que pertenecen a la categoría objetivo
        for (SaleDetail detail : sale.getDetails()) {
            if (matchesTargetCategory(detail.getItemCategory())) {
                eligibleSubtotal += detail.getSubtotal();
            }
        }

        return eligibleSubtotal * (percentage / 100.0);
    }

    /**
     * Checks whether a sale item's category matches this promotion's target
     * category, treating "ACCESSORY" as a wildcard for any accessory
     * sub-category.
     *
     * @param itemCategory the category of the sale item, as stored in SaleDetail
     * @return true if the item is eligible for this promotion's discount
     */
    private boolean matchesTargetCategory(String itemCategory) {
        if (targetCategory == null || itemCategory == null) {
            return false;
        }
        if (targetCategory.equalsIgnoreCase("ACCESSORY")) {
            return ACCESSORY_ITEM_CATEGORIES.contains(itemCategory.toUpperCase());
        }
        return targetCategory.equalsIgnoreCase(itemCategory);
    }
}