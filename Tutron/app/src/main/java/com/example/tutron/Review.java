package com.example.tutron;

import androidx.annotation.NonNull;

import java.util.Date;

public class Review {

    private String id, review, studentEmail, tutorEmail, tutorTopic;
    private Date reviewDate;
    private double numberOfStars;

    public Review(){}

    public Review(String id, String review, String studentEmail, String tutorEmail, String tutorTopic, double numberOfStars, Date reviewDate){
        this.id = id;
        this.review = review;
        this.studentEmail = studentEmail;
        this.tutorEmail = tutorEmail;
        this.tutorTopic = tutorTopic;
        this.numberOfStars = numberOfStars;
        this.reviewDate = reviewDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getTutorEmail() {
        return tutorEmail;
    }

    public void setTutorEmail(String tutorEmail) {
        this.tutorEmail = tutorEmail;
    }

    public String getTutorTopic() {
        return tutorTopic;
    }

    public void setTutorTopic(String tutorTopic) {
        this.tutorTopic = tutorTopic;
    }

    public double getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(double numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    @NonNull
    @Override
    public String toString() {
        return review;
    }
}
