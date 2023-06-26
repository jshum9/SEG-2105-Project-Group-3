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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText userName,userPassword;
    Button loginBtn,registerBtn;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://seg-2105-group-project-f5fd7-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Get the user name and password
                String userNameTemp = userName.getText().toString().trim();
                String userPasswordTemp = userPassword.getText().toString().trim();
                if(!TextUtils.isEmpty(userNameTemp) && !TextUtils.isEmpty(userPasswordTemp) ){
                    //Something needs to be done here to tell the user is a student or a tutor.
                    //Code needed

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if user name is in database
                            if(snapshot.hasChild(userNameTemp)){
                                //user name exists in database
                                String getPassword = snapshot.child(userNameTemp).child("password").getValue(String.class);
                                if (getPassword.equals(userPasswordTemp)){
                                    String role = snapshot.child(userNameTemp).child("type").getValue(String.class);
                                    if(role.equals("Tutor")){
                                        if(snapshot.child(userNameTemp).hasChild("status")){
                                            String status = snapshot.child(userNameTemp).child("status").getValue(String.class);
                                            if(status.equals("Active")){
                                                Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(MainActivity.this, SignedIn.class);
                                                intent.putExtra("role",role);
                                                startActivity(intent);
                                            } else if(status.equals("Suspend")) {
                                                Toast.makeText(MainActivity.this, "Your account has been suspended!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(MainActivity.this, TutorInfo.class);
                                                startActivity(intent);

                                            }else{
                                                Toast.makeText(MainActivity.this, "Your account has been dismissed!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(MainActivity.this, TutorInfo.class);
                                                startActivity(intent);
                                        }

                                        }
                                    }

                                    else  {
                                        Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, SignedIn.class);
                                        intent.putExtra("role",role);
                                        startActivity(intent);
                                    }

                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else{
                    Toast.makeText(MainActivity.this,"User name or password cannot be empty.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Register Button
        //Go to register page starting from choosing role as a student or a tutor.
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GetTutorOrStudent.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
//test 