package com.example.tutron;

public class User {
    private String firstName,lastName,emailAddress,password;

    public User(){}

    public User(String firstName, String lastName, String emailAddress, String password){
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();

        //So that all email use a period and not a comma
        this.emailAddress = emailAddress.replace(',', '.');
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}