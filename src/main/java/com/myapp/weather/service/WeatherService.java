package com.myapp.weather.service;

import com.google.gson.Gson;
import com.myapp.weather.model.WeatherResponse;
import com.myapp.weather.util.Config;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


public class WeatherService {


    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    private final String apiKey;
    private final HttpClient client;
    private final Gson gson = new Gson();


    public WeatherService() {
        this.apiKey = Config.getApiKey();
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }


    public WeatherResponse getCurrentWeather(String cityQuery)
            throws IOException, InterruptedException {
        return getCurrentWeather(cityQuery, "metric");
    }


    public WeatherResponse getCurrentWeather(String cityQuery, String units)
            throws IOException, InterruptedException {

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "API key not configured. Set OPENWEATHER_API_KEY or config.properties (api.key=...)");
        }


        String encodedCity = java.net.URLEncoder.encode(
                cityQuery, java.nio.charset.StandardCharsets.UTF_8);


        String url = BASE_URL
                + "?q=" + encodedCity
                + "&appid=" + apiKey
                + "&units=" + units;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        String body = response.body();

        if (status == 200) {

            return gson.fromJson(body, WeatherResponse.class);
        } else if (status == 404) {

            return null;
        } else {

            throw new IOException("Weather API call failed. HTTP " + status + ": " + body);
        }
    }
}
