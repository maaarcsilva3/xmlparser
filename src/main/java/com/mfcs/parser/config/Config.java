package com.mfcs.parser.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public enum Config {
	XML_FILES_LOCATION("XML_FILES_LOCATION"),
    FILE_METADATA_EXTENSION("FILE_METADATA_EXTENSION"),
    FILE_EXTENSION("FILE_EXTENSION"),
    JSON_OUTPUT_LOCATION("JSON_OUTPUT_LOCATION"),
    ARCHIVE_LOCATION("ARCHIVE_LOCATION"),
    FAILED_LOCATION("FAILED_LOCATION"),
    LOGS_LOCATION("LOGS_LOCATION"),
    
    DB_USERNAME("DB_USERNAME"),
    DB_PASSWORD("DB_PASSWORD"),
    DB_IP("DB_IP"),
    DB_PORT("DB_PORT"),
    DB_NAME("DB_NAME")
    
    
    
    ;

    private final String key;
    private static final Properties properties = new Properties();

    Config(String key) {
        this.key = key;
    }

    public String getValue() {
        return properties.getProperty(key, "").trim();
    }

    public static void initialize() {
        try (FileInputStream input = new FileInputStream("config/config.properties")) {
            properties.load(input);
            System.out.println("Config successfully loaded");
        } catch (IOException e) {
            System.err.println("Failed to load config");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
