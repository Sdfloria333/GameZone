package com.gamezone.persistence;

import com.gamezone.model.sales.Sale;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter; // 1. IMPORTAR FORMATTER
import java.util.ArrayList;
import java.util.List;

public class SaleRepository {

    private static final String SALES_FILE = "src/main/data/sales.json";

    // 2. Formateador que tolera fecha con u opcionalmente con hora "YYYY-MM-DD[
    // HH:mm:ss]"
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm:ss]");

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> {
                        String dateStr = json.getAsString();
                        return LocalDate.parse(dateStr, FORMATTER); // 3. USAR EL FORMATTER
                    })
            .setPrettyPrinting()
            .create();

    public boolean saveSales(List<Sale> sales) {
        return saveToFile(SALES_FILE, sales);
    }

    public List<Sale> loadSales() {
        Type listType = new TypeToken<ArrayList<Sale>>() {
        }.getType();
        return loadFromFile(SALES_FILE, listType);
    }

    private <T> boolean saveToFile(String path, List<T> data) {
        File file = new File(path);
        if (file.getParentFile() != null)
            file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(data, writer);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private <T> List<T> loadFromFile(String path, Type listType) {
        File file = new File(path);
        if (!file.exists())
            return new ArrayList<>();

        try (FileReader reader = new FileReader(file)) {
            List<T> data = gson.fromJson(reader, listType);
            return data != null ? data : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}