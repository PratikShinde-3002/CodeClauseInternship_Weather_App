package com.myapp.weather.util;

public class UnitsManager {
    private String units = "metric"; // default

    public void setUnits(String selected) {
        if (selected.equalsIgnoreCase("metric") || selected.equalsIgnoreCase("imperial")) {
            this.units = selected.toLowerCase();
        }
    }

    public String getUnits() {
        return units;
    }

    public String temperatureUnit() {
        return units.equals("metric") ? "°C" : "°F";
    }

    public String speedUnit() {
        return units.equals("metric") ? "m/s" : "mph";
    }
}
