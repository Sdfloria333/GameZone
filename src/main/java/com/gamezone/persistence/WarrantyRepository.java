package com.gamezone.persistence;

import com.gamezone.model.warranties.BasicWarranty;
import com.gamezone.model.warranties.Warranty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the persistence of warranties using a JSON file. Only the
 * sale and product identifiers are stored, so this class has no
 * dependency on any service.
 */
public class WarrantyRepository {

    private static final String WARRANTIES_FILE = "src/main/data/warranties.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Lightweight representation of a persisted warranty. It holds
     * identifiers only; resolving them into objects is the service's job.
     */
    public static class WarrantyRecord {
        private String warrantyId;
        private String type;
        private String productId;
        private String saleId;
        private String startDate;

        /**
         * Creates a warranty record.
         *
         * @param warrantyId the warranty identifier
         * @param type "BASIC" or "EXTENDED"
         * @param productId the identifier of the covered product
         * @param saleId the identifier of the original sale
         * @param startDate the start date in ISO format (yyyy-MM-dd)
         */
        public WarrantyRecord(String warrantyId, String type, String productId,
                              String saleId, String startDate) {
            this.warrantyId = warrantyId;
            this.type = type;
            this.productId = productId;
            this.saleId = saleId;
            this.startDate = startDate;
        }

        /** @return the warranty identifier */
        public String getWarrantyId() { return warrantyId; }

        /** @return the warranty type ("BASIC" or "EXTENDED") */
        public String getType() { return type; }

        /** @return the covered product identifier */
        public String getProductId() { return productId; }

        /** @return the original sale identifier */
        public String getSaleId() { return saleId; }

        /** @return the start date in ISO format */
        public String getStartDate() { return startDate; }
    }

    /**
     * Saves all warranties to the JSON file, storing only identifiers.
     *
     * @param warranties the list of warranties to save
     * @return true if the save was successful
     */
    public boolean saveAll(List<Warranty> warranties) {
        List<WarrantyRecord> records = new ArrayList<>();
        for (Warranty w : warranties) {
            records.add(new WarrantyRecord(
                    w.getWarrantyId(),
                    (w instanceof BasicWarranty) ? "BASIC" : "EXTENDED",
                    w.getProduct().getId(),
                    w.getSale().getSaleId(),
                    w.getStartDate().toString()));
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
     * Loads the persisted warranty records without resolving any reference.
     *
     * @return the list of records, or an empty list if the file does not exist
     */
    public List<WarrantyRecord> loadAll() {
        File file = new File(WARRANTIES_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<WarrantyRecord>>() {}.getType();
            List<WarrantyRecord> records = gson.fromJson(reader, listType);
            return records != null ? records : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}