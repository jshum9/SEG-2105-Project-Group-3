package com.example.tutron;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class StudentMakeReview extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    DatabaseReference usersDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_make_review);

        Intent intentRole = getIntent();
        String studentEmailAddress = intentRole.getStringExtra("studentEmailAddress");
        String tutorEmailAddress = intentRole.getStringExtra("tutorEmailAddress");
        String topicName = intentRole.getStringExtra("topicName");
        Long reviewDate = intentRole.getLongExtra("reviewDate",-1);
        java.util.Date resultDate = new Date(reviewDate);

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        EditText editTextReview = findViewById(R.id.editTextReview);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        Button buttonCancel = findViewById(R.id.buttonCancel);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Reviews");
        usersDatabaseReference = database.getReference("Users");


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected rating and review text
                float rating = ratingBar.getRating();
                String review = editTextReview.getText().toString();

                if (review.isEmpty()){
                    Toast.makeText(StudentMakeReview.this, "Review cannot be empty!", Toast.LENGTH_SHORT).show();
                }

                else{
                    String id = databaseReference.push().getKey();
                    Review b = new Review(id, review, studentEmailAddress, tutorEmailAddress.replace('.', ','), topicName, ratingBar.getRating(),resultDate);
                    assert id != null;
                    databaseReference.child(id).setValue(b);


                    Intent intent = new Intent(StudentMakeReview.this, StudentHomePage.class);
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
                Intent intent = new Intent(StudentMakeReview.this, StudentHomePage.class);
                intent.putExtra("emailAddress", studentEmailAddress);
                startActivity(intent);
                finish();
            }
        });
    }

}