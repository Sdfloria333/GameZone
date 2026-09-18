package com.gamezone.model.sales;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class Sale {
    private String id;
    private LocalDate date;
    private String customerId;
    private String sellerId;
    private List<SaleDetail> details;
    private double total;

    private String appliedPromotionName;
    private double discountAmount;

    public Sale(String id, LocalDate date, String customerId, String sellerId, List<SaleDetail> details) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.details = details;
        this.appliedPromotionName = "Ninguna";
        this.discountAmount = 0.0;
        this.total = getSubtotal();
    }

    public double getSubtotal() {
        double sum = 0.0;
        if (this.details != null) {
            for (SaleDetail detail : this.details) {
                sum += detail.getSubtotal();
            }
        }
        return sum;
    }

    // --- GETTERS Y SETTERS ---
    public String getId() {
        return id;
    }

    // Alias para compatibilidad con SaleService
    public String getSaleId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public List<SaleDetail> getDetails() {
        return details;
    }

    public String getAppliedPromotionName() {
        return appliedPromotionName;
    }

    public void setAppliedPromotionName(String appliedPromotionName) {
        this.appliedPromotionName = appliedPromotionName;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getTotal() {
        return total;
    }

 feature/return-module

    /**
     * Checks whether this sale is still within the 30-day return window.
     *
     * @return true if the current date is within 30 days of the sale date
     */
    public boolean canBeReturned() {
        LocalDate saleDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        long daysSinceSale = ChronoUnit.DAYS.between(saleDate, today);
        return daysSinceSale >= 0 && daysSinceSale <= 30;

    public void setTotal(double total) {
        this.total = total;
    }

   
    public String generateReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== FACTURA DE VENTA =====\n");
        sb.append("ID Venta: ").append(id).append("\n");
        sb.append("Fecha: ").append(date).append("\n");
        sb.append("Cliente: ").append(customerId).append("\n");
        sb.append("Vendedor: ").append(sellerId).append("\n");
        sb.append("----------------------------\n");

        if (details != null) {
            for (SaleDetail d : details) {
                sb.append(String.format("Producto: %s | Cant: %d | Precio: $%.2f | Subtotal: $%.2f\n",
                        d.getProductId(), d.getQuantity(), d.getUnitPrice(), d.getSubtotal()));
            }
        }

        sb.append("----------------------------\n");
        sb.append(String.format("Subtotal: $%.2f\n", getSubtotal()));
        if (discountAmount > 0) {
            sb.append(String.format("Promoción Aplicada: %s (-$%.2f)\n", appliedPromotionName, discountAmount));
        } else {
            sb.append("Promoción Aplicada: Ninguna ($0.00)\n");
        }
        sb.append(String.format("TOTAL FINAL: $%.2f\n", total));
        sb.append("============================\n");

        return sb.toString();
 develop
    }
}