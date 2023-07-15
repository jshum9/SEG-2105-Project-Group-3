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
    private EditText firstName, lastName, email, address;

    private Button saveBtn;

    private ImageButton backBtn;
    private String username = "";

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://seg-2105-group-project-f5fd7-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        Intent nameIntent = getIntent();
        String emailAddress = nameIntent.getStringExtra("emailAddress");

        firstName = findViewById(R.id.student_first_name);
        lastName = findViewById(R.id.student_last_name);
        email = findViewById(R.id.student_email);
        address = findViewById(R.id.student_address);
        saveBtn = findViewById(R.id.student_saveBtn);
        backBtn = findViewById(R.id.student_backBtn);

        databaseReference.child("Users").child(emailAddress).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firstName.setText(snapshot.child("firstName").getValue(String.class));
                lastName.setText(snapshot.child("lastName").getValue(String.class));
                email.setText(snapshot.child("emailAddress").getValue(String.class));
                address.setText(snapshot.child("address").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(StudentProfile.this, StudentHomePage.class);
                backIntent.putExtra("emailAddress",username);
                startActivity(backIntent);
                finish();
            }
        });

        saveBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameTemp = firstName.getText().toString().trim();
                String lastNameTemp = lastName.getText().toString().trim();
                //Replacing "." with "," since we are using the email as a key
                String emailAddressTemp = email.getText().toString().trim().replace('.', ',');
                String emailAddressTemp2 = email.getText().toString().trim();
                String addressTemp = address.getText().toString().trim();
                Boolean dataSaved = false;

                if (!TextUtils.isEmpty(firstNameTemp) && !TextUtils.isEmpty(lastNameTemp) && (!TextUtils.isEmpty(emailAddressTemp))
                        && !TextUtils.isEmpty(addressTemp)) {
                    dataSaved = true;

                } else {
                    Toast.makeText(StudentProfile.this, "Please fill out everything", Toast.LENGTH_SHORT).show();
                }
                if (dataSaved) {
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(emailAddressTemp)) {
                                // If the new email id is the same as the old email id, then update the user info
                                if (emailAddressTemp.equals(emailAddress)) {
                                    updateUserInfo(emailAddressTemp, firstNameTemp, lastNameTemp, emailAddressTemp2, addressTemp);
                                    Toast.makeText(StudentProfile.this, "Edit and save user information successfully!", Toast.LENGTH_SHORT).show();
                                    username = emailAddressTemp;
                                } else {
                                    Toast.makeText(StudentProfile.this, "This email has already existed! Please try again", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // If the new email id does not exist in the database, then copy the user data to the new email id and remove the old data
                                cloneAndDeleteOldUser(emailAddressTemp, emailAddress, firstNameTemp, lastNameTemp, emailAddressTemp2, addressTemp);
                                username = emailAddressTemp;
                            }
                        }


                            @Override
                            public void onCancelled (@NonNull DatabaseError error){

                            }
                        });
                    }
                }

            private void updateUserInfo(String email, String firstName, String lastName, String emailAddress,String address) {
                databaseReference.child("Users").child(email).child("firstName").setValue(firstName);
                databaseReference.child("Users").child(email).child("lastName").setValue(lastName);
                databaseReference.child("Users").child(email).child("address").setValue(address);
                databaseReference.child("Users").child(email).child("emailAddress").setValue(emailAddress);
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
                                    updateUserInfo(newEmail,firstName,lastName,emailAddress,address);
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
