package com.example.tutron;

public class User {
    private String nickName,firstName,lastName,emailAddress,password;

    public User(){}

    public User(String nickName, String firstName, String lastName, String emailAddress, String password){
        this.nickName = nickName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}