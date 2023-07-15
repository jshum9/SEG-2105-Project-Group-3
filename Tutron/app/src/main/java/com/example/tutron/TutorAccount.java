package com.example.tutron;

import java.util.ArrayList;
import java.util.List;

public class TutorAccount extends User{


    static final private String TYPE = "TUTOR";
    private String  educationLevel, nativeLanguage,  description;
    private TutorSuspension suspension;

    private int numberOfLessonsGiven;
    private Double rating;

    private float averageRating;

    public TutorAccount(){}

    private List<Topic> topics;

    public TutorAccount(String firstName, String lastName, String emailAddress, String password,
                        String educationLevel, String nativeLanguage, String description) {
        super(firstName, lastName, emailAddress, password);
        this.educationLevel = educationLevel;
        this.nativeLanguage = nativeLanguage.toLowerCase();
        this.description = description;
        this.suspension = new TutorSuspension(false, false, null);
        this.numberOfLessonsGiven = 0;
        this.rating = null;
        this.topics = new ArrayList<>();
        this.averageRating = 0;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TutorSuspension getSuspension() {
        return suspension;
    }

    public void setSuspension(TutorSuspension suspension) {
        this.suspension = suspension;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getNumberOfLessonsGiven() {
        return numberOfLessonsGiven;
    }

    public void setNumberOfLessonsGiven(int numberOfLessonsGiven) {
        this.numberOfLessonsGiven = numberOfLessonsGiven;
    }

    public static String getTYPE() {
        return TYPE;
    }
}
