package com.gamezone.model.accessories;

import java.util.List;

public class Controller extends Accessory {

    private String connectionType;

    public Controller(String id, String title, double price, int stockQuantity, List<String> compatibleConsoleIds,
            String connectionType) {
        super(id, title, price, stockQuantity, compatibleConsoleIds);
        this.connectionType = connectionType;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    @Override
    public String getDescription() {
        return String.format("Control: %s | Conexión: %s | Precio: $%.2f | Stock: %d",
                getTitle(), connectionType, getPrice(), getStockQuantity());
    }
}
