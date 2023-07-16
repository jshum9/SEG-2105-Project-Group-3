package com.example.tutron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentMakeComplaint extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_make_complaint);

        Intent intentRole = getIntent();
        String studentEmailAddress = intentRole.getStringExtra("studentEmailAddress");
        String tutorEmailAddress = intentRole.getStringExtra("tutorEmailAddress");


        EditText editTextComplaint = findViewById(R.id.editTextComplaint);
        Button buttonSubmit = findViewById(R.id.submitBtn);
        Button buttonCancel = findViewById(R.id.cancelBtn);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Complaints");

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String complaint = editTextComplaint.getText().toString();

                if (complaint.isEmpty()){
                    Toast.makeText(StudentMakeComplaint.this, "Complaint cannot be empty!", Toast.LENGTH_SHORT).show();
                }
                else{
                    String id = databaseReference.push().getKey();
                    Complaint a = new Complaint(id, complaint, studentEmailAddress.replace(',', '.'), tutorEmailAddress.replace(',', '.'));
                    assert id != null;
                    databaseReference.child(id).setValue(a);

                    Intent intent = new Intent(StudentMakeComplaint.this, StudentHomePage.class);
                    intent.putExtra("emailAddress", studentEmailAddress);
                    startActivity(intent);
                    finish();
                }

            }
        });

        // Set a click listener for the cancel button
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentMakeComplaint.this, StudentHomePage.class);
                intent.putExtra("emailAddress", studentEmailAddress);
                startActivity(intent);
                finish();
            }
        });
    }

}