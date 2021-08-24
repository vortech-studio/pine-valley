package com.vortech.pinevalleyclub.model;

import java.util.List;

public class Club {

    private String name, city, country, location, phoneNumber, followers, holes, rating;
    private int imageSrc, description;
    private boolean expanded;
    private List<Service> services;

    public Club(String name, String city, String country, int description, String location, String phoneNumber, String followers, String holes, int imageSrc, String rating, List<Service> services) {
        this.name = name;
        this.city = city;
        this.country = country;
        this.description = description;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.followers = followers;
        this.holes = holes;
        this.imageSrc = imageSrc;
        this.rating = rating;
        this.services = services;
        this.expanded = false;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public int getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFollowers() {
        return followers;
    }

    public String getHoles() {
        return holes;
    }

    public int getImageSrc() {
        return imageSrc;
    }

    public String getRating() {
        return rating;
    }

    public List<Service> getServices() {
        return services;
    }

    public boolean isExpanded() {
        return expanded;
    }

    @Override
    public String toString() {
        return "Club{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", followers=" + followers +
                ", holes=" + holes +
                ", imageSrc=" + imageSrc +
                ", rating=" + rating +
                ", expanded=" + expanded +
                '}';
    }
}
