package com.myapp.weather.service;

import com.google.gson.Gson;
import com.myapp.weather.model.ForecastResponse;
import com.myapp.weather.util.Config;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.time.Duration;

public class ForecastService {

    private static final String BASE = "https://api.openweathermap.org/data/2.5/forecast";
    private final String apiKey;
    private final HttpClient client;
    private final Gson gson = new Gson();

    public ForecastService() {
        this.apiKey = Config.getApiKey();
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public ForecastResponse getForecast(String cityQuery, String units)
            throws IOException, InterruptedException {

        String encoded = java.net.URLEncoder.encode(cityQuery, java.nio.charset.StandardCharsets.UTF_8);
        String url = BASE + "?q=" + encoded + "&appid=" + apiKey + "&units=" + units;

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() == 200)
            return gson.fromJson(resp.body(), ForecastResponse.class);
        else
            throw new IOException("Forecast API failed: " + resp.body());
    }
}
