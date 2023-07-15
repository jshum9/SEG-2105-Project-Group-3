package com.example.tutron;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfile extends AppCompatActivity {
    private EditText firstName, lastName, address;

    private Button saveBtn;

    private ImageButton backBtn;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://seg-2105-group-project-f5fd7-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        Intent nameIntent = getIntent();
        String emailAddress = nameIntent.getStringExtra("emailAddress");

        firstName = findViewById(R.id.student_first_name);
        lastName = findViewById(R.id.student_last_name);
        address = findViewById(R.id.student_address);
        saveBtn = findViewById(R.id.student_saveBtn);
        backBtn = findViewById(R.id.student_backBtn);

        databaseReference.child("Users").child(emailAddress).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firstName.setText(snapshot.child("firstName").getValue(String.class));
                lastName.setText(snapshot.child("lastName").getValue(String.class));
                address.setText(snapshot.child("address").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Back to student Home Page
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(StudentProfile.this, StudentHomePage.class);
                backIntent.putExtra("emailAddress",emailAddress);
                startActivity(backIntent);
                finish();
            }
        });

        //Update student info
        saveBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameTemp = firstName.getText().toString().trim();
                String lastNameTemp = lastName.getText().toString().trim();
                String addressTemp = address.getText().toString().trim();
                Boolean dataSaved = false;

                if (!TextUtils.isEmpty(firstNameTemp) && !TextUtils.isEmpty(lastNameTemp)
                        && !TextUtils.isEmpty(addressTemp)) {
                    dataSaved = true;

                } else {
                    Toast.makeText(StudentProfile.this, "Please fill out everything", Toast.LENGTH_SHORT).show();
                }
                if (dataSaved) {
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            updateUserInfo(emailAddress, firstNameTemp, lastNameTemp, addressTemp);
                            Toast.makeText(StudentProfile.this, "Edit and save user information successfully!", Toast.LENGTH_SHORT).show();

                        }


                            @Override
                            public void onCancelled (@NonNull DatabaseError error){

                            }
                        });
                    }
                }


            //Update information of the user.
            private void updateUserInfo(String email, String firstName, String lastName, String address) {
                databaseReference.child("Users").child(email).child("firstName").setValue(firstName);
                databaseReference.child("Users").child(email).child("lastName").setValue(lastName);
                databaseReference.child("Users").child(email).child("address").setValue(address);
            }

            private void cloneAndDeleteOldUser(String newEmail, String oldEmail, String firstName, String lastName,String emailAddress,String address){
                databaseReference.child("Users").child(oldEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child("Users").child(newEmail).setValue(snapshot.getValue(), new DatabaseReference.CompletionListener(){
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if (error != null) {
                                    System.out.println("Copy failed");
                                }else{
                                    databaseReference.child("Users").child(oldEmail).removeValue();
                                    updateUserInfo(newEmail,firstName,lastName,address);
                                    Toast.makeText(StudentProfile.this, "Edit and save user information successfully!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Copy failed: " + error.getMessage());
                    }
                });
            }
            }));
        }
    }
