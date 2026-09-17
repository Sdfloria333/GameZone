package com.gamezone.service;

import com.gamezone.model.accessories.*;
import com.gamezone.persistence.AccessoryRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AccessoryService {

    private final AccessoryRepository repository = new AccessoryRepository();
    private final List<Accessory> accessories = repository.loadAccessories();

    public AccessoryService() {
        if (accessories.isEmpty()) {
            accessories.add(new Controller("ACC01", "Control PS5", 69.99, 10, List.of("CONS01"), "Inalámbrico"));
            accessories.add(new Cable("ACC02", "Cable HDMI", 15.50, 25, List.of("CONS01"), 2.0, "HDMI"));
            accessories.add(new Memory("ACC03", "MicroSD 128GB", 29.99, 15, List.of("CONS03"), 128, "microSD"));
            repository.saveAccessories(accessories);
        }
    }

    public boolean addAccessory(Accessory accessory) {
        if (accessory == null)
            return false;
        accessories.add(accessory);
        return repository.saveAccessories(accessories);
    }

    public List<Accessory> listAccessories() {
        return accessories;
    }

    public Accessory findById(String id) {
        return accessories.stream()
                .filter(a -> a.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public List<Accessory> findByConsole(String consoleId) {
        return accessories.stream()
                .filter(a -> a.getCompatibleConsoleIds().contains(consoleId))
                .collect(Collectors.toList());
    }
}