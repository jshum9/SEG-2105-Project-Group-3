package com.example.tutron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProcessComplaint extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://seg-2105-group-project-f5fd7-default-rtdb.firebaseio.com/");

    Button suspendBtn,dismissBtn,backBtn;
    //We need to get Tutor id from xml files.
    //I don't figure it out how.
    String tutorId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_complaint);

        suspendBtn = findViewById(R.id.suspendBtn);
        dismissBtn = findViewById(R.id.dismissBtn);
        backBtn = findViewById(R.id.backBtn);

        suspendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("users").child(tutorId).child("status").setValue("Suspend");
            }
        });

        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("users").child(tutorId).child("status").setValue("Dismiss");
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(ProcessComplaint.this, Administrator.class);
                startActivity(backIntent);
                finish();
            }
        });
    }
}