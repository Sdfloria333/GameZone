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
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        List<Product> products = productRepository.findAll();

        for (Product existingProduct : products) {
            if (existingProduct.getId().equals(product.getId())) {
                throw new IllegalArgumentException("A product with this ID already exists");
            }
        }

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
     * @throws IllegalArgumentException if the productId is null/empty or if newQuantity is negative
     */
    public void updateStock(String productId, int newQuantity) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }

        if (newQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }

        List<Product> products = productRepository.findAll();
        Product productToUpdate = null;

        for (Product product : products) {
            if (product.getId().equals(productId)) {
                productToUpdate = product;
                break;
            }
        }

        if (productToUpdate != null) {
            productToUpdate.setStockQuantity(newQuantity);
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
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }

        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }
}


