package com.example.tutron;

public class AdministratorAccount extends User{

    private String userName, password;

    public AdministratorAccount(){}


    public AdministratorAccount(String userName, String password) {
        super(userName, null, null, null, password);
    }
}
