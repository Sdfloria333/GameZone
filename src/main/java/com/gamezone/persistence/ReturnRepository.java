package com.gamezone.persistence;

import com.gamezone.model.products.Product;
import com.gamezone.model.returns.Return;
import com.gamezone.model.sales.Sale;
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
 * Manages the persistence of returns using a JSON file.
 * Stores only sale and product IDs internally, and resolves them
 * into full objects when loading, using the injected services.
 */
public class ReturnRepository {

    private static final String RETURNS_FILE = "src/main/data/returns.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final SaleService saleService;
    private final ProductService productService;

    /**
     * Creates a new ReturnRepository with the services needed to
     * resolve Sale and Product references when loading returns.
     *
     * @param saleService the service used to resolve original sales
     * @param productService the service used to resolve returned products
     */
    public ReturnRepository(SaleService saleService, ProductService productService) {
        this.saleService = saleService;
        this.productService = productService;
    }

    /**
     * Lightweight internal representation used for JSON persistence,
     * storing only identifiers instead of full object references.
     */
    private static class ReturnRecord {
        String returnId;
        String date;
        String saleId;
        List<String> productIds;
        String reason;
        double refundAmount;
    }

    /**
     * Saves all returns to the JSON file, storing only their
     * sale and product identifiers.
     *
     * @param returns the list of returns to save
     * @return true if the save was successful
     */
    public boolean saveAll(List<Return> returns) {
        List<ReturnRecord> records = new ArrayList<>();

        for (Return r : returns) {
            ReturnRecord record = new ReturnRecord();
            record.returnId = r.getReturnId();
            record.date = r.getDate().toString();
            record.saleId = r.getOriginalSale().getSaleId();
            record.reason = r.getReason();
            record.refundAmount = r.getRefundAmount();

            List<String> ids = new ArrayList<>();
            for (Product product : r.getReturnedProducts()) {
                ids.add(product.getId());
            }
            record.productIds = ids;

            records.add(record);
        }

        File file = new File(RETURNS_FILE);
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
     * Loads all returns from the JSON file, resolving each sale and
     * product reference through the injected services.
     *
     * @return the list of returns, or an empty list if the file does not exist
     */
    public List<Return> loadAll() {
        List<Return> returns = new ArrayList<>();
        File file = new File(RETURNS_FILE);

        if (!file.exists()) {
            return returns;
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<ReturnRecord>>() {}.getType();
            List<ReturnRecord> records = gson.fromJson(reader, listType);

            if (records == null) {
                return returns;
            }

            for (ReturnRecord record : records) {
                Sale sale = saleService.findSaleById(record.saleId);
                if (sale == null) {
                    continue;
                }

                List<Product> products = new ArrayList<>();
                for (String productId : record.productIds) {
                    Product product = productService.findProductById(productId);
                    if (product != null) {
                        products.add(product);
                    }
                }

                Return r = new Return(record.returnId, LocalDate.parse(record.date),
                        sale, products, record.reason, record.refundAmount);
                returns.add(r);
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }

        return returns;
    }
}
