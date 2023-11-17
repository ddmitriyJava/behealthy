package com.example.behealthy.entities;

import com.google.firebase.firestore.Exclude;
import java.io.Serializable;

public class HealthIndicators implements Serializable{
    public double temperature;
    public double pressure;
    public double pulse;
    public double sugarLevel;

    public HealthIndicators() {}

    public HealthIndicators(double temperature, double pressure, double pulse, double sugarLevel) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.pulse = pulse;
        this.sugarLevel = sugarLevel;
    }

    @Exclude
    public double getTemperature() {
        return temperature;
    }

    @Exclude
    public double getPressure() {
        return pressure;
    }

    @Exclude
    public double getPulse() {
        return pulse;
    }

    @Exclude
    public double getSugarLevel() {
        return sugarLevel;
    }

    @Override
    public String toString() {
        return "Температура: " + temperature + " °C\nТиск: " + pressure + " мм.рт.ст.\nПульс: " + pulse + " ударів/хв\nРівень цукру в крові: " + sugarLevel + " ммоль/л";
    }
}
