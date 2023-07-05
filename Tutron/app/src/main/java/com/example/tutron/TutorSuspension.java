package com.example.tutron;



public class TutorSuspension {

    boolean isSuspended;
    boolean permanent;
    Date date;

    public TutorSuspension(boolean isSuspended, boolean permanent, Date date){
        this.permanent = permanent;
        this.date = date;
        this.isSuspended = isSuspended;
    }
    public boolean getPermanent(){
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public boolean getIsSuspended(){
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        this.isSuspended = suspended;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
