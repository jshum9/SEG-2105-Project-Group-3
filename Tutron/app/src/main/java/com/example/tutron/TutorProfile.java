package com.example.tutron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TutorProfile extends AppCompatActivity {

    private EditText firstName, lastName, email, educationLevel, nativeLanguage, description;
    private Button saveBtn;
    private ImageButton backBtn;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://seg-2105-group-project-f5fd7-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_edit_profile);

        //Get the username from SignedIn page.
        Intent nameIntent = getIntent();
        String emailAddress = nameIntent.getStringExtra("emailAddress");

        firstName = findViewById(R.id.tutor_first_name);
        lastName = findViewById(R.id.tutor_last_name);
        email = findViewById(R.id.tutor_email);
        educationLevel = findViewById(R.id.tutor_education_level);
        nativeLanguage = findViewById(R.id.tutor_native_language);
        description = findViewById(R.id.tutor_description);
        saveBtn = findViewById(R.id.save_button);
        backBtn = findViewById(R.id.back_Btn);



       //Set each Text value.
        databaseReference.child("Users").child(emailAddress).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            //Get tutor information from database
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firstName.setText("First Name: " +snapshot.child("firstName").getValue(String.class));
                lastName.setText("Last Name: " +snapshot.child("lastName").getValue(String.class));
                email.setText("Email: "+snapshot.child("emailAddress").getValue(String.class));
                educationLevel.setText("Education Level: " +snapshot.child("educationLevel").getValue(String.class));
                nativeLanguage.setText("Native Language: "+snapshot.child("nativeLanguage").getValue(String.class));
                description.setText("Description: " + snapshot.child("description").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(TutorProfile.this, TutorHomePage.class);
                backIntent.putExtra("emailAddress",email.toString());
                startActivity(backIntent);
                finish();
            }
        });



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Edit and save user information
                Toast.makeText(TutorProfile.this, "Edit and save user information", Toast.LENGTH_SHORT).show();
            }
        });

        firstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(TutorProfile.this);
                new AlertDialog.Builder(TutorProfile.this)
                        .setTitle("Edit First Name")
                        .setView(input)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String updatedFirstName = input.getText().toString().trim();
                                if (updatedFirstName.isEmpty()) {
                                    Toast.makeText(TutorProfile.this, "The input cannot be empty!", Toast.LENGTH_SHORT).show();
                                    return;
                                }


                                firstName.setText("Name: " + updatedFirstName);
                                Toast.makeText(TutorProfile.this,"Changed Successfully!",Toast.LENGTH_SHORT).show();
                                databaseReference.child("Users").child(emailAddress).child("firstName").setValue(updatedFirstName);
                                databaseReference.child("Users").child(emailAddress).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        firstName.setText("First Name: " + snapshot.child("firstName").getValue(String.class));
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        lastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(TutorProfile.this);
                new AlertDialog.Builder(TutorProfile.this)
                        .setTitle("Edit Last Name")
                        .setView(input)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String updatedLastName = input.getText().toString().trim();
                                if (updatedLastName.isEmpty()) {
                                    Toast.makeText(TutorProfile.this, "The input cannot be empty!", Toast.LENGTH_SHORT).show();
                                    return;
                                }


                                lastName.setText("Name: " + updatedLastName);
                                Toast.makeText(TutorProfile.this,"Changed Successfully!",Toast.LENGTH_SHORT).show();
                                databaseReference.child("Users").child(emailAddress).child("lastName").setValue(updatedLastName);
                                databaseReference.child("Users").child(emailAddress).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        lastName.setText("Last Name: " + snapshot.child("lastName").getValue(String.class));
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(TutorProfile.this);
                new AlertDialog.Builder(TutorProfile.this)
                        .setTitle("Edit Email Address")
                        .setView(input)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String updatedEmail = input.getText().toString().trim();
                                boolean isExist = false;
                                if (updatedEmail.isEmpty()) {
                                    Toast.makeText(TutorProfile.this, "The input cannot be empty!", Toast.LENGTH_SHORT).show();
                                    return;
                                }


                                databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.hasChild(updatedEmail)){
                                            Toast.makeText(TutorProfile.this,"The email exists!",Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                                email.setText("Native Language: " + updatedEmail);
                                Toast.makeText(TutorProfile.this,"Changed Successfully!",Toast.LENGTH_SHORT).show();
                                databaseReference.child("Users").child(emailAddress).child("emailAddress").setValue(updatedEmail);
                                databaseReference.child("Users").child(emailAddress).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        email.setText("Email: " + snapshot.child("emailAddress").getValue(String.class));
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        educationLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(TutorProfile.this);
                new AlertDialog.Builder(TutorProfile.this)
                        .setTitle("Edit Education Level")
                        .setView(input)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String updatedEL = input.getText().toString().trim();
                                if (updatedEL.isEmpty()) {
                                    Toast.makeText(TutorProfile.this, "The input cannot be empty!", Toast.LENGTH_SHORT).show();
                                    return;
                                }


                                educationLevel.setText("Name: " + updatedEL);
                                Toast.makeText(TutorProfile.this,"Changed Successfully!",Toast.LENGTH_SHORT).show();
                                databaseReference.child("Users").child(emailAddress).child("educationLevel").setValue(updatedEL);
                                databaseReference.child("Users").child(emailAddress).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        educationLevel.setText("Education Level: " + snapshot.child("educationLevel").getValue(String.class));
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        nativeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(TutorProfile.this);
                new AlertDialog.Builder(TutorProfile.this)
                        .setTitle("Edit Native Language")
                        .setView(input)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String updatedNL = input.getText().toString().trim();
                                if (updatedNL.isEmpty()) {
                                    Toast.makeText(TutorProfile.this, "The input cannot be empty!", Toast.LENGTH_SHORT).show();
                                    return;
                                }


                                nativeLanguage.setText("Native Language: " + updatedNL);
                                Toast.makeText(TutorProfile.this,"Changed Successfully!",Toast.LENGTH_SHORT).show();
                                databaseReference.child("Users").child(emailAddress).child("nativeLanguage").setValue(updatedNL);
                                databaseReference.child("Users").child(emailAddress).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        nativeLanguage.setText("Native Language: " + snapshot.child("nativeLanguage").getValue(String.class));
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(TutorProfile.this);
                new AlertDialog.Builder(TutorProfile.this)
                        .setTitle("Edit Description")
                        .setView(input)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String updatedDescription = input.getText().toString().trim();
                                if (updatedDescription.isEmpty()) {
                                    Toast.makeText(TutorProfile.this, "The input cannot be empty!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if(updatedDescription.length() > 600){
                                    Toast.makeText(TutorProfile.this,"The description can't be greater than 600 characters!",Toast.LENGTH_SHORT).show();
                                    return;
                                }


                                description.setText("Description: " + updatedDescription);
                                Toast.makeText(TutorProfile.this,"Changed Successfully!",Toast.LENGTH_SHORT).show();
                                databaseReference.child("Users").child(emailAddress).child("description").setValue(updatedDescription);
                                databaseReference.child("Users").child(emailAddress).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        description.setText("Description: " + snapshot.child("description").getValue(String.class));
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }
}