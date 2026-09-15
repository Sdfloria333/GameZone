package com.gamezone.service;

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

    public ProductService() {
        this.repository = new ProductRepository();
        this.consoles = repository.loadConsoles();
        this.videogames = repository.loadVideogames();
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

        product.setStockQuantity(product.getStockQuantity() - quantity);

        if (product instanceof Console) {
            return repository.saveConsoles(consoles);
        } else if (product instanceof Videogame) {
            return repository.saveVideogames(videogames);
        }

        return false;
    }
}