package com.gamezone.service;

import com.gamezone.model.products.Product;
import com.gamezone.model.returns.Return;
import com.gamezone.model.sales.Sale;
import com.gamezone.model.sales.SaleDetail;
import com.gamezone.persistence.ReturnRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides business logic for managing product returns: registration
 * with plan/membership validation, queries, and monthly balance reporting.
 */
public class ReturnService {

    private final ReturnRepository repository;
    private final SaleService saleService;
    private final ProductService productService;
    private final List<Return> returns;

    /**
     * Creates a new ReturnService with the required dependencies.
     *
     * @param repository the repository used to persist returns
     * @param saleService the service used to resolve and validate sales
     * @param productService the service used to resolve products and restore stock
     */
    public ReturnService(ReturnRepository repository, SaleService saleService, ProductService productService) {
        this.repository = repository;
        this.saleService = saleService;
        this.productService = productService;
        this.returns = repository.loadAll();
    }

    /**
     * Registers a new return for the given sale and products, validating
     * the return window, product ownership, calculating the refund, and
     * restoring stock for each returned product.
     *
     * @param saleId the identifier of the original sale
     * @param productIds the identifiers of the products being returned
     * @param reason the reason for the return
     * @return the registered return
     * @throws IllegalArgumentException if any validation fails
     */
    public Return registerReturn(String saleId, List<String> productIds, String reason) {
        Sale sale = saleService.findSaleById(saleId);
        if (sale == null) {
            throw new IllegalArgumentException("La venta indicada no existe");
        }

        if (!sale.canBeReturned()) {
            throw new IllegalArgumentException("La venta ya superó el plazo de 30 días para devoluciones");
        }

        if (productIds == null || productIds.isEmpty()) {
            throw new IllegalArgumentException("Debe indicar al menos un producto a devolver");
        }

        for (String productId : productIds) {
            if (!saleContainsProduct(sale, productId)) {
                throw new IllegalArgumentException(
                        "El producto " + productId + " no pertenece a la venta indicada");
            }
        }

        List<Product> returnedProducts = new ArrayList<>();
        for (String productId : productIds) {
            Product product = productService.findProductById(productId);
            if (product != null) {
                returnedProducts.add(product);
            }
        }

        String returnId = "R" + (returns.size() + 1);
        Return newReturn = new Return(returnId, sale, returnedProducts, reason);

        for (String productId : productIds) {
            productService.restoreStock(productId, 1);
        }

        returns.add(newReturn);
        repository.saveAll(returns);

        return newReturn;
    }

    private boolean saleContainsProduct(Sale sale, String productId) {
        for (SaleDetail detail : sale.getDetails()) {
            if (detail.getProductId().equalsIgnoreCase(productId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves all registered returns.
     *
     * @return a list containing all returns
     */
    public List<Return> viewAllReturns() {
        return returns;
    }

    /**
     * Retrieves the returns associated with sales made by the given customer.
     *
     * @param customerId the identifier of the customer
     * @return a list of returns belonging to that customer
     */
    public List<Return> viewReturnsByCustomer(String customerId) {
        List<Return> filtered = new ArrayList<>();
        for (Return r : returns) {
            if (r.getOriginalSale().getCustomerId().equalsIgnoreCase(customerId)) {
                filtered.add(r);
            }
        }
        return filtered;
    }

    /**
     * Retrieves the returns associated with a specific sale.
     *
     * @param saleId the identifier of the sale
     * @return a list of returns associated with that sale
     */
    public List<Return> viewReturnsBySale(String saleId) {
        List<Return> filtered = new ArrayList<>();
        for (Return r : returns) {
            if (r.getOriginalSale().getSaleId().equalsIgnoreCase(saleId)) {
                filtered.add(r);
            }
        }
        return filtered;
    }

    /**
     * Generates the net balance for the given month and year, calculated
     * as total sales minus total returns during that period.
     *
     * @param month the month to evaluate (1-12)
     * @param year the year to evaluate
     * @return the net balance (sales total minus returns total)
     */
    public double generateMonthlyBalance(int month, int year) {
        double totalSales = 0.0;
        for (Sale sale : saleService.listSales()) {
            LocalDate saleDate = sale.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (saleDate.getMonthValue() == month && saleDate.getYear() == year) {
                totalSales += sale.getTotal();
            }
        }

        double totalReturns = 0.0;
        for (Return r : returns) {
            if (r.getDate().getMonthValue() == month && r.getDate().getYear() == year) {
                totalReturns += r.getRefundAmount();
            }
        }

        return totalSales - totalReturns;
    }
}