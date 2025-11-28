package com.myapp.weather;

import com.myapp.weather.model.WeatherResponse;
import com.myapp.weather.model.ForecastResponse;
import com.myapp.weather.service.WeatherService;
import com.myapp.weather.service.ForecastService;
import com.myapp.weather.util.UnitsManager;
import com.myapp.weather.util.Cache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    public static void main(String[] args) {
        WeatherService weatherService = new WeatherService();
        ForecastService forecastService = new ForecastService();
        UnitsManager units = new UnitsManager();
        Cache cache = new Cache();

        System.out.println("Weather Application");
        System.out.println("-------------------");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {

                System.out.println("\nMenu:");
                System.out.println("1. Current Weather");
                System.out.println("2. 3-Day Forecast");
                System.out.println("3. Change Units (C/F)");
                System.out.println("4. Exit");
                System.out.print("Choose option: ");

                String choice = br.readLine();

                if (choice.equals("1")) {
                    System.out.print("Enter city: ");
                    String city = br.readLine().trim();

                    try {
                        WeatherResponse res;

                        if (cache.isValid(city)) {
                            res = cache.get();
                        } else {
                            res = weatherService.getCurrentWeather(city, units.getUnits());
                            if (res != null) cache.store(city, res);
                        }

                        if (res == null) {
                            System.out.println("City not found.");
                        } else {
                            printWeather(res, units);
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                } else if (choice.equals("2")) {

                    System.out.print("Enter city: ");
                    String city = br.readLine().trim();

                    try {
                        ForecastResponse fr = forecastService.getForecast(city, units.getUnits());
                        printForecast(fr, units);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                } else if (choice.equals("3")) {

                    System.out.print("Enter 'C' for Celsius or 'F' for Fahrenheit: ");
                    String u = br.readLine().trim();
                    if (u.equalsIgnoreCase("c")) units.setUnits("metric");
                    else if (u.equalsIgnoreCase("f")) units.setUnits("imperial");
                    else System.out.println("Invalid choice");

                    System.out.println("Units changed to: " + units.temperatureUnit());

                } else if (choice.equals("4")) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void printWeather(WeatherResponse r, UnitsManager units) {
        System.out.println("\nCurrent Weather for " + r.name);
        System.out.println("---------------------------------");
        System.out.println("Temperature: " + r.main.temp + " " + units.temperatureUnit());
        System.out.println("Humidity: " + r.main.humidity + "%");
        System.out.println("Wind: " + r.wind.speed + " " + units.speedUnit());
        System.out.println("Condition: " + r.weather.get(0).description);
    }

    private static void printForecast(ForecastResponse fr, UnitsManager units) {
        System.out.println("\n3-Day Forecast for " + fr.city.name);
        System.out.println("---------------------------------");


        for (int i = 0; i < 24; i += 8) {
            ForecastResponse.ForecastItem item = fr.list.get(i);
            System.out.println(item.dt_txt + ": " +
                    item.main.temp + units.temperatureUnit() + ", " +
                    item.weather.get(0).description);
        }
    }
}
