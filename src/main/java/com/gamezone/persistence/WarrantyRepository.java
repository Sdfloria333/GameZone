package com.gamezone.persistence;

import com.gamezone.model.products.Product;
import com.gamezone.model.sales.Sale;
import com.gamezone.model.warranties.BasicWarranty;
import com.gamezone.model.warranties.ExtendedWarranty;
import com.gamezone.model.warranties.Warranty;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the persistence of warranties using a JSON file, storing
 * only sale and product identifiers, and resolving them into full
 * objects when loading using the injected services.
 */
public class WarrantyRepository {

    private static final String WARRANTIES_FILE = "src/main/data/warranties.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final SaleService saleService;
    private final ProductService productService;

    /**
     * Creates a new WarrantyRepository with the services needed to
     * resolve Sale and Product references when loading warranties.
     *
     * @param saleService the service used to resolve original sales
     * @param productService the service used to resolve covered products
     */
    public WarrantyRepository(SaleService saleService, ProductService productService) {
        this.saleService = saleService;
        this.productService = productService;
    }

    /**
     * Lightweight internal representation used for JSON persistence.
     */
    private static class WarrantyRecord {
        String warrantyId;
        String type;
        String productId;
        String saleId;
        String startDate;
    }

    /**
     * Saves all warranties to the JSON file.
     *
     * @param warranties the list of warranties to save
     * @return true if the save was successful
     */
    public boolean saveAll(List<Warranty> warranties) {
        List<WarrantyRecord> records = new ArrayList<>();

        for (Warranty w : warranties) {
            WarrantyRecord record = new WarrantyRecord();
            record.warrantyId = w.getWarrantyId();
            record.type = (w instanceof BasicWarranty) ? "BASIC" : "EXTENDED";
            record.productId = w.getProduct().getId();
            record.saleId = w.getSale().getSaleId();
            record.startDate = w.getStartDate().toString();
            records.add(record);
        }

        File file = new File(WARRANTIES_FILE);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(records, writer);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Loads all warranties from the JSON file, resolving each sale and
     * product reference and reconstructing the correct concrete subtype.
     *
     * @return the list of warranties, or an empty list if the file does not exist
     */
    public List<Warranty> loadAll() {
        List<Warranty> warranties = new ArrayList<>();
        File file = new File(WARRANTIES_FILE);

        if (!file.exists()) {
            return warranties;
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<WarrantyRecord>>() {}.getType();
            List<WarrantyRecord> records = gson.fromJson(reader, listType);

            if (records == null) {
                return warranties;
            }

            for (WarrantyRecord record : records) {
                Product product = productService.findProductById(record.productId);
                Sale sale = saleService.findSaleById(record.saleId);

                if (product == null || sale == null) {
                    continue;
                }

                LocalDate startDate = LocalDate.parse(record.startDate);

                if ("BASIC".equals(record.type)) {
                    warranties.add(new BasicWarranty(record.warrantyId, product, sale, startDate));
                } else if ("EXTENDED".equals(record.type)) {
                    warranties.add(new ExtendedWarranty(record.warrantyId, product, sale, startDate));
                }
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }

        return warranties;
    }
}
