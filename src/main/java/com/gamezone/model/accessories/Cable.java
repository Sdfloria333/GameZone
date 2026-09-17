package com.gamezone.model.accessories;

import java.util.List;

public class Cable extends Accessory {

    private double length;
    private String connectorType;

    public Cable(String id, String title, double price, int stockQuantity, List<String> compatibleConsoleIds,
            double length, String connectorType) {
        super(id, title, price, stockQuantity, compatibleConsoleIds);
        this.length = length;
        this.connectorType = connectorType;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    @Override
    public String getDescription() {
        return String.format("Cable: %s | Conector: %s | Longitud: %.2fm | Precio: $%.2f | Stock: %d",
                getTitle(), connectorType, length, getPrice(), getStockQuantity());
    }
}