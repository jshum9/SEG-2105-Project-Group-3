package com.example.tutron;

public class StudentAccount extends User{
    private String address,cardNumber,expNumber,cvvNumber;

    public StudentAccount(){}


    public StudentAccount(String nickName, String firstName, String lastName, String emailAddress, String password,
                          String address, String cardNumber, String expNumber, String cvvNumber) {
        super(nickName, firstName, lastName, emailAddress, password);
        this.address =address;
        this.cardNumber = cardNumber;
        this.expNumber =expNumber;
        this.cvvNumber = cvvNumber;
    }
}