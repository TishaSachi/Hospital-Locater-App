package com.gtisha.hospitallocaterapp;

public class Hospital {
    private String id;
    private String name;
    private String location;
    private String logoUrl;  // Use this to store the image URL

    // Default constructor required for Firebase
    public Hospital() {}

    public Hospital(String id, String name, String location, String logoUrl) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.logoUrl = logoUrl;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}

