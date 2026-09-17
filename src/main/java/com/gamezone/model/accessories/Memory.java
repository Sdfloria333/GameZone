package com.gamezone.model.accessories;

import java.util.List;

public class Memory extends Accessory {

    private int capacity;
    private String memoryType;

    public Memory(String id, String title, double price, int stockQuantity, List<String> compatibleConsoleIds,
            int capacity, String memoryType) {
        super(id, title, price, stockQuantity, compatibleConsoleIds);
        this.capacity = capacity;
        this.memoryType = memoryType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }

    @Override
    public String getDescription() {
        return String.format("Memoria: %s | Tipo: %s | Capacidad: %dGB | Precio: $%.2f | Stock: %d",
                getTitle(), memoryType, capacity, getPrice(), getStockQuantity());
    }
}