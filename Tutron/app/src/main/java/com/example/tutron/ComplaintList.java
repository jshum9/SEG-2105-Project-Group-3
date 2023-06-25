package com.example.tutron;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ComplaintList extends ArrayAdapter<Complaint> {

    private Activity context;

    List<Complaint> complaints;

    public ComplaintList(Activity context, List<Complaint> complaints) {
        super(context, R.layout.layout_complaint, complaints);
        this.context = context;
        this.complaints = complaints;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_complaint, null, true);

        TextView textViewComplaint = listViewItem.findViewById(R.id.textViewComplaint);
        TextView textViewStudent = listViewItem.findViewById(R.id.textViewStudent);
        TextView textViewTutor = listViewItem.findViewById(R.id.textViewTutor);

        Complaint complaint = complaints.get(position);
        textViewComplaint.setText(complaint.getComplaint());
        textViewStudent.append(complaint.getStudentEmail());
        textViewTutor.append(complaint.getTutorEmail());
        return listViewItem;
    }

}
