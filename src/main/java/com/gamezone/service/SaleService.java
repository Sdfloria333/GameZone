package com.gamezone.service;

import com.gamezone.model.promotions.Promotion;
import com.gamezone.model.sales.Sale;
import com.gamezone.model.sales.SaleDetail;
import com.gamezone.persistence.SaleRepository;
import java.util.ArrayList;
import java.util.List;

public class SaleService {

    private final SaleRepository repository;
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final PersonService personService;
    private final PromotionService promotionService;
    private final List<Sale> sales;

    // UPDATED CONSTRUCTOR: injects PromotionService
    public SaleService(ProductService productService, AccessoryService accessoryService,
                       PersonService personService, PromotionService promotionService) {
        this.repository = new SaleRepository();
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.personService = personService;
        this.promotionService = promotionService; // <-- service is assigned
        this.sales = repository.loadSales();
    }

    public boolean registerSale(String saleId, String customerId, String sellerId, List<SaleDetail> details) {
        if (saleId == null || saleId.isBlank() || details == null || details.isEmpty()) {
            return false;
        }

        // 1. Validate that the sale does not already exist by ID
        for (Sale s : sales) {
            if (s.getId().equalsIgnoreCase(saleId)) {
                return false;
            }
        }

        // 2. Validate that the customer and the seller exist
        if (personService.findCustomerByIdentification(customerId) == null) {
            return false;
        }

        if (personService.findSellerByIdentification(sellerId) == null) {
            return false;
        }

        // 3. Validate stock in a unified way (Products or Accessories)
        for (SaleDetail detail : details) {
            boolean hasProductStock = productService.hasEnoughStock(detail.getProductId(), detail.getQuantity());
            boolean hasAccessoryStock = accessoryService.hasEnoughStock(detail.getProductId(), detail.getQuantity());

            if (!hasProductStock && !hasAccessoryStock) {
                return false; // Cancels the sale if there is not enough stock
            }
        }

        // 4. Reduce stock by delegating to the corresponding service
        for (SaleDetail detail : details) {
            if (productService.findProductById(detail.getProductId()) != null) {
                productService.reduceStock(detail.getProductId(), detail.getQuantity());
            } else {
                accessoryService.updateStock(detail.getProductId(), detail.getQuantity());
            }
        }

        // 5. Create the initial sale
        Sale newSale = new Sale(saleId, java.time.LocalDate.now(), customerId, sellerId, details);

        // 6. AUTOMATIC PROMOTION CALCULATION AND APPLICATION (REQUIRED BY THE GUIDE)
        if (promotionService != null) {
            Promotion bestPromo = promotionService.findBestPromotionFor(newSale);
            if (bestPromo != null) {
                double discount = bestPromo.calculateDiscount(newSale);
                newSale.setAppliedPromotionName(bestPromo.getName());
                newSale.setDiscountAmount(discount);
                newSale.setTotal(newSale.getSubtotal() - discount);
            } else {
                newSale.setAppliedPromotionName("Ninguna");
                newSale.setDiscountAmount(0.0);
                newSale.setTotal(newSale.getSubtotal());
            }
        }

        sales.add(newSale);
        return repository.saveSales(sales);
    }

    public List<Sale> listSales() {
        return sales;
    }

    public List<Sale> listSalesByCustomer(String customerId) {
        List<Sale> filtered = new ArrayList<>();
        for (Sale sale : sales) {
            if (sale.getCustomerId().equalsIgnoreCase(customerId)) {
                filtered.add(sale);
            }
        }
        return filtered;
    }

    public List<Sale> listSalesBySeller(String sellerId) {
        List<Sale> filtered = new ArrayList<>();
        for (Sale sale : sales) {
            if (sale.getSellerId().equalsIgnoreCase(sellerId)) {
                filtered.add(sale);
            }
        }
        return filtered;
    }

    /**
     * Finds a sale by its unique identifier.
     *
     * @param saleId the identifier of the sale
     * @return the sale if found, or null otherwise
     */
    public Sale findSaleById(String saleId) {
        for (Sale sale : sales) {
            if (sale.getSaleId().equalsIgnoreCase(saleId)) {
                return sale;
            }
        }
        return null;
    }
}