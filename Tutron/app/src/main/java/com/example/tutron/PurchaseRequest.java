package com.example.tutron;

import androidx.annotation.NonNull;

public class PurchaseRequest {
    private String id,studentEmail,tutorEmail,topic,status;
    private long requestDate;

    public PurchaseRequest(){}

    public PurchaseRequest(String id,String studentEmail, String tutorEmail, String topic, long requestedDate, String status) {
        this.id = id;
        this.studentEmail = studentEmail;
        this.tutorEmail = tutorEmail;
        this.topic = topic;
        this.status = status;
        this.requestDate = requestedDate;
    }

    public void setRequestDate(long requestDate) {
        this.requestDate = requestDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setTutorEmail(String tutorEmail) {
        this.tutorEmail = tutorEmail;
    }

    public long getRequestDate() {
        return requestDate;
    }

    public String getStatus() {
        return status;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getTopic() {
        return topic;
    }

    public String getTutorEmail() {
        return tutorEmail;
    }

    @NonNull
    @Override
    public String toString() {
        return topic;
    }
}
