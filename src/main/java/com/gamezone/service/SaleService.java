package com.gamezone.service;

import java.util.ArrayList;
import java.util.List;

import com.gamezone.model.accessories.Accessory;
import com.gamezone.model.products.Console;
import com.gamezone.model.products.Product;
import com.gamezone.model.promotions.Promotion;
import com.gamezone.model.sales.Sale;
import com.gamezone.model.sales.SaleDetail;
import com.gamezone.model.warranties.ExtendedWarranty;
import com.gamezone.persistence.SaleRepository;

public class SaleService {

    private final SaleRepository repository;
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final PersonService personService;
    private final PromotionService promotionService;
    private final List<Sale> sales;
    private WarrantyService warrantyService;

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

    public void setWarrantyService(WarrantyService warrantyService) {
        this.warrantyService = warrantyService;
    }

    public boolean registerSale(String saleId, String customerId, String sellerId, List<SaleDetail> details) {
        return registerSale(saleId, customerId, sellerId, details, new ArrayList<>());
    }

    public boolean registerSale(String saleId, String customerId, String sellerId, List<SaleDetail> details,
                                List<String> productIdsWithExtendedWarranty) {
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

        // 4. Create the initial sale
        Sale newSale = new Sale(saleId, java.time.LocalDate.now(), customerId, sellerId, details);

        // 5. AUTOMATIC PROMOTION CALCULATION AND APPLICATION (REQUIRED BY THE GUIDE)
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

        // 6. WARRANTIES: automatic basic warranty for consoles, optional extended
        // warranty
        if (warrantyService != null) {
            double totalWarrantyCost = 0.0;
            for (SaleDetail detail : details) {
                Product product = productService.findProductById(detail.getProductId());
                if (product instanceof Console) {
                    warrantyService.assignBasicWarranty(product, newSale, newSale.getDate());

                    if (productIdsWithExtendedWarranty != null
                            && productIdsWithExtendedWarranty.contains(product.getId())) {
                        ExtendedWarranty extended = warrantyService.assignExtendedWarranty(product, newSale,
                                newSale.getDate());
                        totalWarrantyCost += extended.getAdditionalCost();
                    }
                }
            }
            // 7. Calculate total final: Subtotal - Discount + Extended Warranty Cost
            newSale.setWarrantyCost(totalWarrantyCost);
            newSale.setTotal(newSale.getTotal() + totalWarrantyCost);
        }

        // 8. Reduce stock by delegating to the corresponding service. Accessories
        // must be checked first: ProductService.findProductById() also returns
        // accessories (Accessory extends Product), so routing them through
        // ProductService.reduceStock() silently fails to persist the change
        // (it ends up calling AccessoryService.addAccessory(), which rejects an
        // already-existing id instead of updating its stock).
        for (SaleDetail detail : details) {
            Product product = productService.findProductById(detail.getProductId());
            if (product instanceof Accessory) {
                accessoryService.updateStock(detail.getProductId(), detail.getQuantity());
            } else if (product != null) {
                productService.reduceStock(detail.getProductId(), detail.getQuantity());
            } else {
                accessoryService.updateStock(detail.getProductId(), detail.getQuantity());
            }
        }
        // 9. Persist the sale
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

    public Sale findSaleById(String saleId) {
        for (Sale sale : sales) {
            if (sale.getSaleId().equalsIgnoreCase(saleId)) {
                return sale;
            }
        }
        return null;
    }
}