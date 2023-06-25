package com.example.tutron;

public class User {
    private String firstName,lastName,emailAddress,password;

    public User(){}

    public User(String firstName, String lastName, String emailAddress, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}