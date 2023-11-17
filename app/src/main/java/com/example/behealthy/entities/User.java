package com.example.behealthy.entities;

import com.google.firebase.firestore.Exclude;
import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    public String pib;
    public String email;
    public String password;
    public String diagnosis;
    public List<HealthIndicators> healthIndicatorsList;

    public User() {}

    public User(String pib, String email, String password, String diagnosis, List<HealthIndicators> healthIndicatorsList) {
        this.pib = pib;
        this.email = email;
        this.password = password;
        this.diagnosis = diagnosis;
        this.healthIndicatorsList = healthIndicatorsList;
    }

    @Exclude
    public String getPib() {
        return pib;
    }

    @Exclude
    public String getEmail() {
        return email;
    }

    @Exclude
    public String getPassword() {
        return password;
    }
    @Exclude
    public String getDiagnosis() {
        return diagnosis;
    }

    @Exclude
    public List<HealthIndicators> getHealthIndicatorsList() {
        return healthIndicatorsList;
    }

    public void setHealthIndicatorsList(List<HealthIndicators> healthIndicatorsList) {
        this.healthIndicatorsList = healthIndicatorsList;
    }
}
