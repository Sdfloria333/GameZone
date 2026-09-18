package com.gamezone.model.promotions;

import java.time.LocalDate;
import com.gamezone.model.sales.Sale;
import com.gamezone.model.sales.SaleDetail;


public class BulkPurchaseDiscount extends Promotion {
    private int minQuantity;
    private double percentage;

    public BulkPurchaseDiscount(String id, String name, LocalDate startDate, LocalDate endDate, int minQuantity,
            double percentage) {
        super(id, name, startDate, endDate);
        this.minQuantity = minQuantity;
        this.percentage = percentage;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(Sale sale) {
        if (sale == null || sale.getDetails() == null) {
            return 0.0;
        }

        // Contamos la cantidad total de unidades compradas en la venta
        int totalItemsCount = 0;
        for (SaleDetail detail : sale.getDetails()) {
            totalItemsCount += detail.getQuantity();
        }

        // Si supera o iguala la cantidad mínima, aplica el porcentaje al subtotal
        if (totalItemsCount >= minQuantity) {
            return sale.getSubtotal() * (percentage / 100.0);
        }

        return 0.0;
    }
}