package com.example.tutron;

public class PurchaseRequest {
    private String studentEmail,tutorEmail,topic,status;

    public PurchaseRequest(String studentEmail, String tutorEmail, String topic, long requestedTime, String status) {
        this.studentEmail = studentEmail;
        this.tutorEmail = tutorEmail;
        this.topic = topic;
        this.status = status;
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
}
