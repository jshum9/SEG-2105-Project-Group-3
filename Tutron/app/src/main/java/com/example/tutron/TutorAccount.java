package com.example.tutron;

public class TutorAccount extends User{
    private String educationLevel, nativeLanguage,  description;

    public TutorAccount(){}

    public TutorAccount(String firstName, String lastName, String emailAddress, String password,
                        String educationLevel, String nativeLanguage, String description) {
        super(firstName, lastName, emailAddress, password);
        this.educationLevel = educationLevel;
        this.nativeLanguage = nativeLanguage;
        this.description = description;
    }
}
