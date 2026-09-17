package com.gamezone.persistence;

import com.gamezone.model.accessories.Accessory;
import com.gamezone.model.accessories.Cable;
import com.gamezone.model.accessories.Controller;
import com.gamezone.model.accessories.Memory;
import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccessoryRepository {

    private static final String ACCESSORIES_FILE = "src/main/data/accessories.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public boolean saveAll(List<Accessory> accessories) {
        return saveToFile(ACCESSORIES_FILE, accessories);
    }

    public List<Accessory> loadAll() {
        return loadFromFile(ACCESSORIES_FILE);
    }

    private boolean saveToFile(String path, List<Accessory> data) {
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

    private List<Accessory> loadFromFile(String path) {
        File file = new File(path);
        if (!file.exists())
            return new ArrayList<>();

        List<Accessory> accessories = new ArrayList<>();
        try (FileReader reader = new FileReader(file)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();

                if (jsonObject.has("connectionType")) {
                    accessories.add(gson.fromJson(jsonObject, Controller.class));
                } else if (jsonObject.has("connectorType")) {
                    accessories.add(gson.fromJson(jsonObject, Cable.class));
                } else if (jsonObject.has("memoryType")) {
                    accessories.add(gson.fromJson(jsonObject, Memory.class));
                }
            }
            return accessories;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}