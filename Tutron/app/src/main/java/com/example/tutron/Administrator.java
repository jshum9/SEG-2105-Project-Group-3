package com.example.tutron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Administrator extends AppCompatActivity {

    Button backBtn;
    ListView listViewComplaints;
    List<Complaint> complaints;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        backBtn = findViewById(R.id.backBtn);
        listViewComplaints = findViewById(R.id.listViewComplaints);
        complaints = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Complaints");

        onItemLongClick();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(Administrator.this,MainActivity.class);
                startActivity(backIntent);
                finish();
            }
        });
    }

    private void onItemLongClick() {
        listViewComplaints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Complaint complaint = complaints.get(position);
                showSuspendDismissDialog(complaint.getId(), complaint.getComplaint(), complaint.getTutorEmail(), complaint.getStudentEmail());
            }
        });
    }

    private void showSuspendDismissDialog(String complaintId, String complaint, String tutor, String student){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_process_complaint, null);
        dialogBuilder.setView(dialogView);

        final TextView complaintText = (TextView) dialogView.findViewById(R.id.textComplaint);
        final Spinner yearSpinner = (Spinner) dialogView.findViewById(R.id.yearSpinner);
        final Spinner monthSpinner = (Spinner) dialogView.findViewById(R.id.monthSpinner);
        final Spinner daySpinner = (Spinner) dialogView.findViewById(R.id.daySpinner);
        final Button suspend = (Button) dialogView.findViewById(R.id.suspendBtn);
        final Button dismiss = (Button) dialogView.findViewById(R.id.dismissBtn);
        final Button back = (Button) dialogView.findViewById(R.id.backBtn);

        //dialogBuilder.setMessage(complaint).setMessage(tutor).setMessage(student);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        //TODO: figure out how to implement the spinners to select date

        //Suspension Button
        //TODO: complete function so that admin can suspend tutor
        suspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Dismiss Button
        //TODO: complete function so that admin can dismiss complaint
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Back Button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    protected void onStart(){
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaints.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    Complaint complaint = postSnapshot.getValue(Complaint.class);
                    complaints.add(complaint);
                }

                ComplaintList adapter = new ComplaintList(Administrator.this, complaints);
                listViewComplaints.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}