package com.example.behealthy.model;

public class Diagnosis {
    private String name;
    private String description;

    public Diagnosis(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Diagnosis() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
