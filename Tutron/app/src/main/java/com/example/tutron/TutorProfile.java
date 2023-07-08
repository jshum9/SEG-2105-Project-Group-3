package com.example.tutron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
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

    private TextView nameTV, emailTV, educationLevelTV, nativeLanguageTV, descriptionTV;
    private Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);
        nameTV = findViewById(R.id.tutor_name);
        emailTV = findViewById(R.id.tutor_email);
        educationLevelTV = findViewById(R.id.tutor_education_level);
        nativeLanguageTV = findViewById(R.id.tutor_native_language);
        descriptionTV = findViewById(R.id.tutor_description);
        saveBtn = findViewById(R.id.save_button);

        //Retrieve the email address of the currently logged in user
        SharedPreferences sharedPref = getSharedPreferences("login_user_email", Context.MODE_PRIVATE);
        String emailTemp = sharedPref.getString("userEmail", "");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(emailTemp).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TutorAccount tutorAccount = dataSnapshot.getValue(TutorAccount.class);

                nameTV.setText("Name: " + tutorAccount.getFirstName() + tutorAccount.getLastName());
                emailTV.setText("Email: " + tutorAccount.getEmailAddress());
                educationLevelTV.setText("Education Level: " + tutorAccount.getEducationLevel());
                nativeLanguageTV.setText("Native Language: " + tutorAccount.getNativeLanguage());
                descriptionTV.setText("Description: " + tutorAccount.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TutorProfile.this, "User information acquisition exception", Toast.LENGTH_SHORT).show();
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