package com.example.tutron;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TutorProfile extends AppCompatActivity {

    private EditText firstName, lastName, educationLevel, nativeLanguage, description;

    private TextView numberOfLessonsGiven;
    private Button saveBtn;
    private ImageButton backBtn;

    private String username = "";

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://seg-2105-group-project-f5fd7-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_edit_profile);

        //Get the username from SignedIn page.
        Intent nameIntent = getIntent();
        String emailAddress = nameIntent.getStringExtra("emailAddress");

        numberOfLessonsGiven = findViewById(R.id.numberOfLessonsGivenTextView);
        databaseReference.child("Users").child(emailAddress).child("numberOfLessonsGiven").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int numberOfLessons = dataSnapshot.getValue(Integer.class);
                    String numberOfLessonsString = String.valueOf(numberOfLessons);
                    numberOfLessonsGiven.append(numberOfLessonsString);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur
            }
        });

        firstName = findViewById(R.id.tutor_first_name);
        lastName = findViewById(R.id.tutor_last_name);
        educationLevel = findViewById(R.id.student_address);
        nativeLanguage = findViewById(R.id.tutor_native_language);
        description = findViewById(R.id.tutor_description);
        saveBtn = findViewById(R.id.save_button);
        backBtn = findViewById(R.id.back_Btn);



       //Set each Text value.
        databaseReference.child("Users").child(emailAddress).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            //Get tutor information from database
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firstName.setText(snapshot.child("firstName").getValue(String.class));
                lastName.setText(snapshot.child("lastName").getValue(String.class));
                educationLevel.setText(snapshot.child("educationLevel").getValue(String.class));
                nativeLanguage.setText(snapshot.child("nativeLanguage").getValue(String.class));
                description.setText(snapshot.child("description").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Back to the Tutor Home page
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(TutorProfile.this, TutorHomePage.class);
                backIntent.putExtra("emailAddress",emailAddress);
                startActivity(backIntent);
                finish();
            }
        });



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Edit and save user information
                String firstNameTemp = firstName.getText().toString().trim();
                String lastNameTemp = lastName.getText().toString().trim();
                String educationTemp = educationLevel.getText().toString().trim();
                String languageTemp = nativeLanguage.getText().toString().trim();
                String descriptionTemp = description.getText().toString();
                Boolean dataSaved = false;

                if (!TextUtils.isEmpty(firstNameTemp) && !TextUtils.isEmpty(lastNameTemp)
                        && !TextUtils.isEmpty(educationTemp)
                        && !TextUtils.isEmpty(languageTemp) && !TextUtils.isEmpty(descriptionTemp)) {
                    dataSaved = true;
                } else {
                    Toast.makeText(TutorProfile.this, "Failed. All fields must be filled in.", Toast.LENGTH_SHORT).show();
                }
                if(dataSaved){
                    //Check if the username has already existed.
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(descriptionTemp.length() > 600){
                                Toast.makeText(TutorProfile.this,"The description cannot be longer than 600 characters!",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            updateUserInfo(emailAddress, firstNameTemp, lastNameTemp, educationTemp, languageTemp, descriptionTemp);
                            Toast.makeText(TutorProfile.this, "Edit and save user information successfully!", Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }

            //Update information of the user.
            private void updateUserInfo(String email, String firstName, String lastName, String education, String language, String description) {
                databaseReference.child("Users").child(email).child("firstName").setValue(firstName);
                databaseReference.child("Users").child(email).child("lastName").setValue(lastName);
                databaseReference.child("Users").child(email).child("educationLevel").setValue(education);
                databaseReference.child("Users").child(email).child("nativeLanguage").setValue(language);
                databaseReference.child("Users").child(email).child("description").setValue(description);
            }

            //Clone old user info to new and delete the old one
            private void cloneAndDeleteOldUser(String newEmail, String oldEmail, String firstName, String lastName, String education, String language, String description) {
                databaseReference.child("Users").child(oldEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        databaseReference.child("Users").child(newEmail).setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError != null) {
                                    System.out.println("Copy failed");
                                } else {
                                    // Remove old data
                                    databaseReference.child("Users").child(oldEmail).removeValue();
                                    // Update new info
                                    updateUserInfo(newEmail, firstName, lastName, education, language, description);
                                    Toast.makeText(TutorProfile.this, "Edit and save user information successfully!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Copy failed: " + databaseError.getMessage());
                    }
                });
            }

        });




    }
}