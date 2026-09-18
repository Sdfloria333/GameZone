package com.gamezone.persistence;

import com.gamezone.model.promotions.*;
import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PromotionRepository {

    private static final String PROMOTIONS_FILE = "src/main/data/promotions.json";

    // Adaptador para formatear LocalDate a ISO (YYYY-MM-DD) en JSON
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
            .setPrettyPrinting()
            .create();

    public boolean saveAll(List<Promotion> promotions) {
        File file = new File(PROMOTIONS_FILE);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        JsonArray jsonArray = new JsonArray();
        for (Promotion promo : promotions) {
            JsonObject jsonObject = gson.toJsonTree(promo).getAsJsonObject();

            // Incluimos el discriminador explícito exigido por la guía
            if (promo instanceof PercentageDiscount) {
                jsonObject.addProperty("type", "PERCENTAGE");
            } else if (promo instanceof CategoryDiscount) {
                jsonObject.addProperty("type", "CATEGORY");
            } else if (promo instanceof BulkPurchaseDiscount) {
                jsonObject.addProperty("type", "BULK");
            }
            jsonArray.add(jsonObject);
        }

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(jsonArray, writer);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public List<Promotion> loadAll() {
        File file = new File(PROMOTIONS_FILE);

        // SI EL ARCHIVO NO EXISTE O ESTÁ VACÍO, PRECARGAMOS LAS 3 PROMOCIONES QUE PIDE
        // LA GUÍA
        if (!file.exists() || file.length() == 0) {
            List<Promotion> defaultPromotions = createDefaultPromotions();
            saveAll(defaultPromotions); // Se persisten automáticamente
            return defaultPromotions;
        }

        List<Promotion> promotions = new ArrayList<>();
        try (FileReader reader = new FileReader(file)) {
            JsonElement parsed = JsonParser.parseReader(reader);
            if (!parsed.isJsonArray()) {
                return promotions;
            }

            JsonArray jsonArray = parsed.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                String type = jsonObject.has("type") ? jsonObject.get("type").getAsString() : "";

                switch (type) {
                    case "PERCENTAGE":
                        promotions.add(gson.fromJson(jsonObject, PercentageDiscount.class));
                        break;
                    case "CATEGORY":
                        promotions.add(gson.fromJson(jsonObject, CategoryDiscount.class));
                        break;
                    case "BULK":
                        promotions.add(gson.fromJson(jsonObject, BulkPurchaseDiscount.class));
                        break;
                    default:
                        if (jsonObject.has("targetCategory")) {
                            promotions.add(gson.fromJson(jsonObject, CategoryDiscount.class));
                        } else if (jsonObject.has("minQuantity")) {
                            promotions.add(gson.fromJson(jsonObject, BulkPurchaseDiscount.class));
                        } else if (jsonObject.has("percentage")) {
                            promotions.add(gson.fromJson(jsonObject, PercentageDiscount.class));
                        }
                        break;
                }
            }
        } catch (Exception e) {
            return new ArrayList<>();
        }

        return promotions;
    }

    // MÉTODO PRIVADO PARA CREAR LOS DATOS PRECARGADOS EXIGIDOS
    private List<Promotion> createDefaultPromotions() {
        List<Promotion> defaultList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(5);
        LocalDate endDate = today.plusMonths(1);

        // 1. Promoción Porcentual General
        defaultList.add(new PercentageDiscount(
                "PROMO-01", "Descuento de Bienvenida 10%", startDate, endDate, 10.0));

        // 2. Promoción por Categoría (Especial para VIDEOGAME)
        defaultList.add(new CategoryDiscount(
                "PROMO-02", "Super Descuento Videojuegos 15%", startDate, endDate, 15.0, "VIDEOGAME"));

        // 3. Promoción por Volumen (3 o más productos)
        defaultList.add(new BulkPurchaseDiscount(
                "PROMO-03", "Descuento al por Mayor 20% (3+ items)", startDate, endDate, 3, 20.0));

        return defaultList;
    }
}