package com.covidlk.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class ConfigurationService {
    private final File CONFIG_FILE = new File("configFile.json");
    // Gson use for convert java pojp and json objects
    Gson gson = new GsonBuilder().create();

    public ConfigModel getConfigurations() {
        if (!CONFIG_FILE.exists()) {
            createConfig();
        }
        return getConfigurationFromFile();
    }

    private ConfigModel getConfigurationFromFile() {
        ConfigModel configModel = new ConfigModel();
        try(Reader reader = new FileReader(CONFIG_FILE)) {
            configModel = gson.fromJson(reader,ConfigModel.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configModel;
    }

    private void createConfig() {
        ConfigModel configModel = new ConfigModel();
        // create config file with configModelDefaultValues
        try (Writer writer = new FileWriter(CONFIG_FILE, false))
        {
            gson.toJson(configModel, writer);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
