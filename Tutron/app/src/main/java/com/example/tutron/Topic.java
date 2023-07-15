package com.example.tutron;

public class Topic {
    private String name;
    private int yearsOfExperience;
    private String description;

    private boolean isOffered;

    private float rating;

    public Topic(){}

    public Topic(String name, int yearsOfExperience, String description) {
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
        this.description = description;
        this.isOffered = false;
        this.rating = 0;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setIsOffered(boolean offered) {
        this.isOffered = offered;
    }

    public boolean getIsOffered(){
        return isOffered;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
