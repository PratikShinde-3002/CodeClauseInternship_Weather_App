package com.myapp.weather.model;

import java.util.List;

public class WeatherResponse {

    public Main main;
    public List<Weather> weather;
    public Wind wind;
    public String name;
    public Sys sys;

    public static class Main {
        public double temp;
        public double feels_like;
        public int humidity;
        public double pressure;
    }

    public static class Weather {
        public String main;
        public String description;
    }

    public static class Wind {
        public double speed;
        public double deg;
    }

    public static class Sys {
        public String country;
    }
}
