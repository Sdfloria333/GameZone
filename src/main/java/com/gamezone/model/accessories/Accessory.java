package com.gamezone.model.accessories;

import java.util.ArrayList;
import java.util.List;
import com.gamezone.model.products.Product;

public abstract class Accessory extends Product {

    private List<String> compatibleConsoleIds;

    public Accessory(String id, String title, double price, int stockQuantity, List<String> compatibleConsoleIds) {
        super(id, title, price, stockQuantity);
        this.compatibleConsoleIds = (compatibleConsoleIds != null) ? compatibleConsoleIds : new ArrayList<>();
    }

    public List<String> getCompatibleConsoleIds() {
        return compatibleConsoleIds;
    }

    public void setCompatibleConsoleIds(List<String> compatibleConsoleIds) {
        this.compatibleConsoleIds = compatibleConsoleIds;
    }

    public void addCompatibleConsoleId(String consoleId) {
        if (!this.compatibleConsoleIds.contains(consoleId)) {
            this.compatibleConsoleIds.add(consoleId);
        }
    }

    @Override
    public abstract String getDescription();
}