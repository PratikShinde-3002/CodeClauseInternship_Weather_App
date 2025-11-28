package com.myapp.weather.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {


    public static String getApiKey() {
        String env = System.getenv("OPENWEATHER_API_KEY");
        if (env != null && !env.isBlank()) return env.trim();


        Path p = Path.of("config.properties");
        if (Files.exists(p)) {
            Properties props = new Properties();
            try (FileInputStream in = new FileInputStream(p.toFile())) {
                props.load(in);
                String k = props.getProperty("api.key");
                if (k != null && !k.isBlank()) return k.trim();
            } catch (IOException e) {

            }
        }
        return null;
    }
}
