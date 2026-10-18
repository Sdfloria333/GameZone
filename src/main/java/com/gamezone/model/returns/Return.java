package com.gamezone.model.returns;

import com.gamezone.model.products.Product;
import com.gamezone.model.sales.Sale;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a product return associated with a previously registered sale.
 * A return may include only some of the products from the original sale.
 */
public class Return {

    private String returnId;
    private LocalDate date;
    private Sale originalSale;
    private List<Product> returnedProducts;
    private String reason;
    private double refundAmount;

    /**
     * Creates a new return for the given sale and products.
     *
     * @param returnId the unique identifier of the return
     * @param originalSale the sale this return refers to
     * @param returnedProducts the products being returned
     * @param reason the reason for the return
     */
    public Return(String returnId, Sale originalSale, List<Product> returnedProducts, String reason) {
        if (returnId == null || returnId.isBlank()) {
            throw new IllegalArgumentException("El identificador de la devolución no puede estar vacío");
        }
        if (originalSale == null) {
            throw new IllegalArgumentException("La devolución debe referenciar una venta existente");
        }
        if (returnedProducts == null || returnedProducts.isEmpty()) {
            throw new IllegalArgumentException("La devolución debe incluir al menos un producto");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Debe indicar un motivo para la devolución");
        }

        this.returnId = returnId;
        this.date = LocalDate.now();
        this.originalSale = originalSale;
        this.returnedProducts = returnedProducts;
        this.reason = reason;
        this.refundAmount = calculateRefundAmount();
    }

    /**
     * Reconstructs an existing return from persisted data, preserving
     * its original date and refund amount instead of recalculating them.
     *
     * @param returnId the unique identifier of the return
     * @param date the original date of the return
     * @param originalSale the sale this return refers to
     * @param returnedProducts the products that were returned
     * @param reason the reason for the return
     * @param refundAmount the previously calculated refund amount
     */
    public Return(String returnId, LocalDate date, Sale originalSale,
                  List<Product> returnedProducts, String reason, double refundAmount) {
        this.returnId = returnId;
        this.date = date;
        this.originalSale = originalSale;
        this.returnedProducts = returnedProducts;
        this.reason = reason;
        this.refundAmount = refundAmount;
    }

    public String getReturnId() {
        return returnId;
    }

    public LocalDate getDate() {
        return date;
    }

    public Sale getOriginalSale() {
        return originalSale;
    }

    public List<Product> getReturnedProducts() {
        return returnedProducts;
    }

    public String getReason() {
        return reason;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    /**
     * Calculates the refund amount by summing, for each returned product, its
     * list price reduced by the same discount ratio the original sale had
     * (discountAmount / subtotal). This keeps the refund proportional to what
     * the customer actually paid: if the original sale carried a promotion,
     * returning an item refunds its price minus its proportional share of
     * that discount, never the full list price. Updates the refundAmount
     * attribute.
     *
     * @return the calculated refund amount
     */
    public double calculateRefundAmount() {
        double total = 0.0;
        double discountRatio = getDiscountRatio();

        for (Product product : returnedProducts) {
            total += product.getPrice() * (1 - discountRatio);
        }
        this.refundAmount = total;
        return total;
    }

    /**
     * Computes the fraction of the original sale's subtotal that its
     * promotion discounted (0.0 if the sale had no discount or no subtotal).
     *
     * @return the discount ratio, between 0.0 and 1.0
     */
    private double getDiscountRatio() {
        double subtotal = originalSale.getSubtotal();
        double discountAmount = originalSale.getDiscountAmount();
        return (subtotal > 0) ? (discountAmount / subtotal) : 0.0;
    }

    /**
     * Generates a formatted receipt describing this return, in Spanish
     * since it is displayed directly to the user. For each returned product
     * it shows the list price, the proportional discount carried over from
     * the original sale's promotion, and the amount actually refunded.
     *
     * @return a formatted return receipt
     */
    public String generateReturnReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("Devolución: ").append(returnId)
                .append(" | Fecha: ").append(date)
                .append(" | Venta original: ").append(originalSale.getSaleId())
                .append(" | Motivo: ").append(reason)
                .append("\nProductos devueltos:\n");

        double discountRatio = getDiscountRatio();

        for (Product product : returnedProducts) {
            double listPrice = product.getPrice();
            double itemDiscount = listPrice * discountRatio;
            double itemRefund = listPrice - itemDiscount;
            sb.append(String.format(
                    "  - %s | Precio de lista: $%.2f | Descuento proporcional: -$%.2f | Reembolsado: $%.2f%n",
                    product.getTitle(), listPrice, itemDiscount, itemRefund));
        }

        sb.append(String.format("Monto total reembolsado: $%.2f", refundAmount));
        return sb.toString();
    }
}