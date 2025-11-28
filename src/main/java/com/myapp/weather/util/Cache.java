package com.myapp.weather.util;

import com.myapp.weather.model.WeatherResponse;

public class Cache {
    private WeatherResponse lastWeather;
    private String lastCity;
    private long lastTime;
    private final long ttlMillis = 2 * 60 * 1000;

    public boolean isValid(String city) {
        return city.equalsIgnoreCase(lastCity) &&
                (System.currentTimeMillis() - lastTime) < ttlMillis;
    }

    public void store(String city, WeatherResponse data) {
        this.lastCity = city;
        this.lastWeather = data;
        this.lastTime = System.currentTimeMillis();
    }

    public WeatherResponse get() {
        return lastWeather;
    }
}
