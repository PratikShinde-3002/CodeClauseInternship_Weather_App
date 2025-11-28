package com.myapp.weather.model;

import java.util.List;

public class ForecastResponse {

    public List<ForecastItem> list;
    public City city;

    public static class ForecastItem {
        public Main main;
        public List<WeatherResponse.Weather> weather;
        public String dt_txt;
    }

    public static class Main {
        public double temp;
        public int humidity;
    }

    public static class City {
        public String name;
        public String country;
    }
}
