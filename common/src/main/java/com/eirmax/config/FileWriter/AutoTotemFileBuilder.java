package com.eirmax.config.FileWriter;

import com.eirmax.config.AutoTotemValidations;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class AutoTotemFileBuilder {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final File CONFIG_FILE = new File("config/justanotherautototem.json");
    public static AutoTotemValidations INSTANCE = new AutoTotemValidations();

    public static void load() {
        try {
            if (CONFIG_FILE.exists()) {
                INSTANCE = GSON.fromJson(new FileReader(CONFIG_FILE), AutoTotemValidations.class);
            } else {
                save();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(INSTANCE, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

