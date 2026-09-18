package com.gamezone.service;

import com.gamezone.model.accessories.Accessory;
import com.gamezone.model.products.Console;
import com.gamezone.model.products.Product;
import com.gamezone.model.products.Videogame;
import com.gamezone.persistence.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private final ProductRepository repository;
    private final List<Console> consoles;
    private final List<Videogame> videogames;
    private final AccessoryService accessoryService;

    public ProductService(AccessoryService accessoryService) {
        this.repository = new ProductRepository();
        this.consoles = repository.loadConsoles();
        this.videogames = repository.loadVideogames();
        this.accessoryService = accessoryService;
    }

    public boolean registerConsole(Console console) {
        if (console == null || console.getId() == null || console.getId().isBlank() || console.getPrice() < 0) {
            return false;
        }
        if (findProductById(console.getId()) != null) {
            return false;
        }
        consoles.add(console);
        return repository.saveConsoles(consoles);
    }

    public boolean registerVideogame(Videogame videogame) {
        if (videogame == null || videogame.getId() == null || videogame.getId().isBlank() || videogame.getPrice() < 0) {
            return false;
        }
        if (findProductById(videogame.getId()) != null) {
            return false;
        }
        videogames.add(videogame);
        return repository.saveVideogames(videogames);
    }

    public List<Product> listAllProducts() {
        List<Product> allProducts = new ArrayList<>();
        allProducts.addAll(consoles);
        allProducts.addAll(videogames);
        allProducts.addAll(accessoryService.listAccessories());
        return allProducts;
    }

    public List<Console> listConsoles() {
        return consoles;
    }

    public List<Videogame> listVideogames() {
        return videogames;
    }

    public Product findProductById(String id) {
        if (id == null || id.isBlank())
            return null;

        for (Console c : consoles) {
            if (c.getId().equalsIgnoreCase(id))
                return c;
        }
        for (Videogame v : videogames) {
            if (v.getId().equalsIgnoreCase(id))
                return v;
        }
        for (Accessory a : accessoryService.listAccessories()) {
            if (a.getId().equalsIgnoreCase(id))
                return a;
        }
        return null;
    }

    public boolean hasEnoughStock(String productId, int quantity) {
        Product product = findProductById(productId);
        return product != null && product.getStockQuantity() >= quantity;
    }

    public boolean reduceStock(String productId, int quantity) {
        Product product = findProductById(productId);
        if (product == null || product.getStockQuantity() < quantity) {
            return false;
        }


/**
 * Restores stock for a product after a return is processed.
 * This is an additive method used by the returns module.
 *
 * @param productId the identifier of the product to restore
 * @param quantity the quantity to add back to stock
 * @return true if the stock was updated and persisted successfully
 */
        public boolean restoreStock(String productId, int quantity) {
            Product product = findProductById(productId);
            if (product == null || quantity <= 0) {
                return false;
            }

            product.setStockQuantity(product.getStockQuantity() + quantity);

            if (product instanceof Console) {
                return repository.saveConsoles(consoles);
            } else if (product instanceof Videogame) {
                return repository.saveVideogames(videogames);
            } else if (product instanceof Accessory) {
                return accessoryService.addAccessory((Accessory) product);
            }

            return false;
        }

        product.setStockQuantity(product.getStockQuantity() - quantity);

        if (product instanceof Console) {
            return repository.saveConsoles(consoles);
        } else if (product instanceof Videogame) {
            return repository.saveVideogames(videogames);
        } else if (product instanceof Accessory) {
            return accessoryService.addAccessory((Accessory) product);
        }

        return false;
    }
}