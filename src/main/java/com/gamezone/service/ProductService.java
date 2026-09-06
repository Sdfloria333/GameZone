package com.gamezone.service;

import com.gamezone.model.Product;
import com.gamezone.persistence.ProductRepository;

import java.util.List;

/**
 * Provides business logic for managing products.
 * This class handles product registration, listing, stock updates, and searches.
 */
public class ProductService {

    private ProductRepository productRepository;

    /**
     * Creates a new ProductService with the specified product repository.
     *
     * @param productRepository the repository used to manage product data
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Registers a new product and saves it in the repository.
     *
     * @param product the product to be registered
     */
    public void registerProduct(Product product) {
        List<Product> products = productRepository.findAll();
        products.add(product);
        productRepository.saveAll(products);
    }

    /**
     * Retrieves all registered products.
     *
     * @return a list containing all registered products
     */
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    /**
     * Updates the stock quantity of a product.
     *
     * @param productId the unique identifier of the product
     * @param newQuantity the new stock quantity for the product
     */
    public void updateStock(String productId, int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }

        List<Product> products = productRepository.findAll();
        Product product = findProductById(productId);

        if (product != null) {
            product.setStockQuantity(newQuantity);
            productRepository.saveAll(products);
        }
    }

    /**
     * Finds a product by its unique identifier.
     *
     * @param id the unique identifier of the product
     * @return the product if found, or null if no product matches the identifier
     */
    public Product findProductById(String id) {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }
}


