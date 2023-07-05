package com.example.tutron;

public class Date {
    int year, month, day;
    public Date(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public boolean isGreaterThen(Date date){
        if (year > date.getYear()){
            return true;
        }

        else if(month > date.getMonth()){
            return true;
        }

        else if (day > date.getDay()){
            return true;
        }

        else{
            return false;
        }

    }

    @Override
    public String toString(){
        return year + "/" + month + "/" + day;
    }
}
