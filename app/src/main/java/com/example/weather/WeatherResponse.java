package com.example.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("main")
    private WeatherMain main;

    @SerializedName("weather")
    private List<WeatherInfo> weatherInfoList;

    @SerializedName("name")
    private String name;

    @SerializedName("timezone")
    private int timezone;

    public int getTimezone() {return timezone;}
    public String getName() { return name; }

    public WeatherMain getMain() {
        return main;
    }

    public List<WeatherInfo> getWeatherInfoList() {
        return weatherInfoList;
    }
    public class WeatherMain {
        @SerializedName("temp")
        private double temperature;
        @SerializedName("humidity")
        private int humidity;

        @SerializedName("pressure")
        private double pressure;
        @SerializedName("temp_min")
        private double tempMin;

        @SerializedName("temp_max")
        private double tempMax;

        public double getTemperature() {
            return temperature;
        }

        public int getHumidity() {
            return humidity;
        }

        public double getPressure() {
            return pressure;
        }

        public double getTempMin() {
            return tempMin;
        }

        public double getTempMax() {
            return tempMax;
        }

    }

    public class WeatherInfo {
        @SerializedName("icon")
        private String icon;

        public String getIcon() {
            return icon;
        }
    }
}



