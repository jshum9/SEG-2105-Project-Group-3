package com.example.tutron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tutor extends AppCompatActivity {
    EditText tutorUserName, firstName, lastName, educationLevel, emailAddress, password, language, description;
    Button backTutorBtn;
    Button createTutorBtn;
    FirebaseDatabase database;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://seg-2105-group-project-f5fd7-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        tutorUserName = findViewById(R.id.tutorUserName);
        firstName = findViewById(R.id.tutorFirstName);
        lastName = findViewById(R.id.tutorLastName);
        educationLevel = findViewById(R.id.educationLevel);
        emailAddress = findViewById(R.id.tutorEmailAddress);
        password = findViewById(R.id.tutorPassword);
        language = findViewById(R.id.nativeLanguage);
        description = findViewById(R.id.description);

        backTutorBtn = findViewById(R.id.tutorBackButton);
        backTutorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(Tutor.this, GetTutorOrStudent.class);
                startActivity(backIntent);
                finish();
            }
        });

        createTutorBtn = findViewById(R.id.tutorCreateButton);
        createTutorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameTemp = tutorUserName.getText().toString().trim();
                String firstNameTemp = firstName.getText().toString().trim();
                String lastNameTemp = lastName.getText().toString().trim();
                String educationTemp = educationLevel.getText().toString().trim();
                String emailTemp = emailAddress.getText().toString().trim();
                String passwordTemp = password.getText().toString().trim();
                String languageTemp = language.getText().toString().trim();
                String descriptionTemp = description.getText().toString().trim();
                Boolean dataSaved = false;

                if (!TextUtils.isEmpty(userNameTemp) && !TextUtils.isEmpty(firstNameTemp) && !TextUtils.isEmpty(lastNameTemp)
                        && !TextUtils.isEmpty(educationTemp) && !TextUtils.isEmpty(emailTemp) && !TextUtils.isEmpty(passwordTemp)
                        && !TextUtils.isEmpty(languageTemp) && !TextUtils.isEmpty(descriptionTemp)) {
                    dataSaved = true;
                } else {
                    Toast.makeText(Tutor.this, "Failed. All fields must be filled in.", Toast.LENGTH_SHORT).show();
                }
                if(dataSaved){
                    databaseReference.child("users").child(userNameTemp).child("first name").setValue(firstNameTemp);
                    databaseReference.child("users").child(userNameTemp).child("last name").setValue(lastNameTemp);
                    databaseReference.child("users").child(userNameTemp).child("education").setValue(educationTemp);
                    databaseReference.child("users").child(userNameTemp).child("email").setValue(emailTemp);
                    databaseReference.child("users").child(userNameTemp).child("password").setValue(passwordTemp);
                    databaseReference.child("users").child(userNameTemp).child("language").setValue(languageTemp);
                    databaseReference.child("users").child(userNameTemp).child("description").setValue(descriptionTemp);
                    databaseReference.child("users").child(userNameTemp).child("user type").setValue("Tutor");

                    //database = FirebaseDatabase.getInstance();
                    //reference = database.getReference("users");
                    //HelperClass helperClass = new HelperClass(userNameTemp, passwordTemp, "Tutor");
                    //reference.child(userNameTemp).setValue(helperClass);

                    Toast.makeText(Tutor.this, "Successful!", Toast.LENGTH_SHORT).show();
                    Intent register = new Intent(Tutor.this, MainActivity.class);
                    startActivity(register);
                    finish();
                }
            }
        });
    }
}