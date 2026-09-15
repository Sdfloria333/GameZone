package com.gamezone.persistence;

import com.gamezone.model.products.Console;
import com.gamezone.model.products.Videogame;
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

public class ProductRepository {

    private static final String CONSOLES_FILE = "src/main/data/consoles.json";
    private static final String VIDEOGAMES_FILE = "src/main/data/videogames.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public boolean saveConsoles(List<Console> consoles) {
        return saveToFile(CONSOLES_FILE, consoles);
    }

    public List<Console> loadConsoles() {
        Type listType = new TypeToken<ArrayList<Console>>() {
        }.getType();
        return loadFromFile(CONSOLES_FILE, listType);
    }

    public boolean saveVideogames(List<Videogame> videogames) {
        return saveToFile(VIDEOGAMES_FILE, videogames);
    }

    public List<Videogame> loadVideogames() {
        Type listType = new TypeToken<ArrayList<Videogame>>() {
        }.getType();
        return loadFromFile(VIDEOGAMES_FILE, listType);
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