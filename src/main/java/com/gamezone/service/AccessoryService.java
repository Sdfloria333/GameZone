package com.gamezone.service;

import com.gamezone.model.accessories.*;
import com.gamezone.persistence.AccessoryRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AccessoryService {

    private final AccessoryRepository repository;
    private final List<Accessory> accessories;

    // Constructor con inyección de dependencia (Requerimiento Pág 4)
    public AccessoryService(AccessoryRepository repository) {
        this.repository = repository;
        this.accessories = repository.loadAll();

        // Datos precargados requeridos por la guía
        if (accessories.isEmpty()) {
            accessories.add(new Controller("ACC01", "Control PS5", 69.99, 10, List.of("CONS01"), "Inalámbrico"));
            accessories.add(new Cable("ACC02", "Cable HDMI", 15.50, 25, List.of("CONS01"), 2.0, "HDMI"));
            accessories.add(new Memory("ACC03", "MicroSD 128GB", 29.99, 15, List.of("CONS03"), 128, "microSD"));
            repository.saveAll(accessories);
        }
    }

    // Constructor por defecto
    public AccessoryService() {
        this(new AccessoryRepository());
    }

    // Métodos específicos de registro (Requerimiento Pág 4)
    public boolean registerController(Controller controller) {
        return addAccessory(controller);
    }

    public boolean registerCable(Cable cable) {
        return addAccessory(cable);
    }

    public boolean registerMemory(Memory memory) {
        return addAccessory(memory);
    }

    public boolean addAccessory(Accessory accessory) {
        if (accessory == null || accessory.getId() == null || accessory.getId().isBlank()) {
            return false;
        }
        // Validar que no exista un ID duplicado
        if (findById(accessory.getId()) != null) {
            return false;
        }
        accessories.add(accessory);
        return repository.saveAll(accessories);
    }

    public List<Accessory> listAccessories() {
        return accessories;
    }

    public List<Accessory> listAccessoriesByType(String type) {
        return accessories.stream()
                .filter(a -> a.getClass().getSimpleName().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public Accessory findById(String id) {
        if (id == null || id.isBlank())
            return null;
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

    // Control de stock integrado para el módulo de ventas (Requerimiento Pág 4-5)
    public boolean hasEnoughStock(String accessoryId, int quantity) {
        Accessory acc = findById(accessoryId);
        return acc != null && acc.getStockQuantity() >= quantity;
    }

    public boolean updateStock(String accessoryId, int quantity) {
        Accessory acc = findById(accessoryId);
        if (acc == null || acc.getStockQuantity() < quantity) {
            return false;
        }
        acc.setStockQuantity(acc.getStockQuantity() - quantity);
        return repository.saveAll(accessories);
    }
}