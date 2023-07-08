package com.example.tutron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TutorProfile extends AppCompatActivity {

    private TextView name, email, educationLevel, nativeLanguage, description;
    private Button saveBtn;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://seg-2105-group-project-f5fd7-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_edit_profile);

        //Get the username from SignedIn page.
        Intent nameIntent = getIntent();
        String emailAddress = nameIntent.getStringExtra("emailAddress");

        name = findViewById(R.id.tutor_name);
        email = findViewById(R.id.tutor_email);
        educationLevel = findViewById(R.id.tutor_education_level);
        nativeLanguage = findViewById(R.id.tutor_native_language);
        description = findViewById(R.id.tutor_description);
        saveBtn = findViewById(R.id.save_button);



       //Set each Text value.
        databaseReference.child("Users").child(emailAddress).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            //Get tutor information from database
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText("Name: " +snapshot.child("firstName").getValue(String.class) + " " + snapshot.child("lastName").getValue(String.class));
                email.setText("Email: "+snapshot.child("emailAddress").getValue(String.class));
                educationLevel.setText("Education Level: " +snapshot.child("educationLevel").getValue(String.class));
                nativeLanguage.setText("Native Language: "+snapshot.child("nativeLanguage").getValue(String.class));
                description.setText("Description: " + snapshot.child("description").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Edit and save user information
                Toast.makeText(TutorProfile.this, "Edit and save user information", Toast.LENGTH_SHORT).show();
            }
        });
    }
}