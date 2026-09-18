package com.gamezone.service;

import com.gamezone.model.sales.Sale;
import com.gamezone.model.promotions.*;
import com.gamezone.persistence.PromotionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PromotionService {
    private final PromotionRepository repository;
    private final List<Promotion> promotions;

    public PromotionService(PromotionRepository repository) {
        this.repository = repository;
        this.promotions = repository.loadAll();
    }

    public boolean registerPercentageDiscount(String id, String name, LocalDate startDate, LocalDate endDate,
            double percentage) {
        if (findById(id) != null)
            return false;
        promotions.add(new PercentageDiscount(id, name, startDate, endDate, percentage));
        return repository.saveAll(promotions);
    }

    public boolean registerCategoryDiscount(String id, String name, LocalDate startDate, LocalDate endDate,
            double percentage, String targetCategory) {
        if (findById(id) != null)
            return false;
        promotions.add(new CategoryDiscount(id, name, startDate, endDate, percentage, targetCategory));
        return repository.saveAll(promotions);
    }

    public boolean registerBulkPurchaseDiscount(String id, String name, LocalDate startDate, LocalDate endDate,
            int minQuantity, double percentage) {
        if (findById(id) != null)
            return false;
        promotions.add(new BulkPurchaseDiscount(id, name, startDate, endDate, minQuantity, percentage));
        return repository.saveAll(promotions);
    }

    public List<Promotion> listAllPromotions() {
        return new ArrayList<>(promotions);
    }

    public List<Promotion> listActivePromotions() {
        List<Promotion> activeList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Promotion promo : promotions) {
            if (promo.isActive(today)) {
                activeList.add(promo);
            }
        }
        return activeList;
    }

    public Promotion findById(String id) {
        if (id == null)
            return null;
        for (Promotion promo : promotions) {
            if (promo.getId().equalsIgnoreCase(id)) {
                return promo;
            }
        }
        return null;
    }

    public Promotion findBestPromotionFor(Sale sale) {
        if (sale == null)
            return null;

        Promotion bestPromo = null;
        double maxDiscountAmount = 0.0;
        LocalDate today = LocalDate.now();

        for (Promotion promo : promotions) {
            if (promo.isActive(today)) {
                double currentDiscount = promo.calculateDiscount(sale);
                // Seleccionamos la que otorgue el descuento en dinero más alto
                if (currentDiscount > maxDiscountAmount) {
                    maxDiscountAmount = currentDiscount;
                    bestPromo = promo;
                }
            }
        }

        return bestPromo;
    }
}