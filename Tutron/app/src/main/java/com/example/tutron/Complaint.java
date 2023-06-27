package com.example.tutron;

public class Complaint {

    private String id, complaint, studentEmail, tutorEmail;

    public Complaint(){}

    public Complaint(String id, String complaint, String studentEmail, String tutorEmail){
        this.id = id;
        this.complaint = complaint;
        this.studentEmail = studentEmail;
        this.tutorEmail = tutorEmail;
    }


    public void setId(String id){ this.id = id;}

    public String getId(){ return id;}

    public void setComplaint(String complaint){ this.complaint = complaint;}

    public String getComplaint(){ return complaint;}

    public void setStudentEmail(String studentEmail){this.studentEmail = studentEmail;}

    public String getStudentEmail(){return studentEmail;}

    public void setTutorEmail(String tutorEmail){this.tutorEmail = tutorEmail;}

    public String getTutorEmail(){return tutorEmail;}

    @Override
    public String toString(){
        return complaint;
    }


}
