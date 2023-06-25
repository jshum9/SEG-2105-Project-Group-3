package com.example.tutron;

public class Complaint {

    private String id, complaint;

    public Complaint(){}

    public Complaint(String id, String complaint){
        this.id = id;
        this.complaint = complaint;
    }

    public Complaint(String complaint){
        this.complaint = complaint;
    }

    public void setId(String id){ this.id = id;}

    public String getId(){ return id;}

    public void setComplaint(String complaint){ this.complaint = complaint;}

    public String getComplaint(){ return complaint;}
}
