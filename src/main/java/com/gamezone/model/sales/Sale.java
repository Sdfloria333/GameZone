package com.gamezone.model.sales;

import java.time.LocalDate;
import java.util.List;

public class Sale {
    private String id;
    private LocalDate date;
    private List<SaleDetail> details;
    private double total;

    // --- NUEVOS ATRIBUTOS EXIGIDOS ---
    private String appliedPromotionName;
    private double discountAmount;

    public Sale(String id, LocalDate date, List<SaleDetail> details) {
        this.id = id;
        this.date = date;
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

    // --- GETTERS Y SETTERS DE LOS NUEVOS ATRIBUTOS ---
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

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<SaleDetail> getDetails() {
        return details;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    // --- RECIBO CON DETALLE DE PROMOCIÓN ---
    public String generateReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== FACTURA DE VENTA =====\n");
        sb.append("ID Venta: ").append(id).append("\n");
        sb.append("Fecha: ").append(date).append("\n");
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
    }
}