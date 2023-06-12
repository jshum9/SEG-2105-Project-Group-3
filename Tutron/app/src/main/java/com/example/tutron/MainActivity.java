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
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                    Query checkUserDatabase = reference.orderByChild("username").equalTo(userNameTemp);
                    checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                //userNameTemp.setError(null);
                                String passwordFromDB = snapshot.child(userNameTemp).child("password").getValue(String.class);

                                if (passwordFromDB.equals(userPasswordTemp)){
                                    //userNameTemp.setError(null);
                                    String nameFromDB = snapshot.child(userNameTemp).child("name").getValue(String.class);

                                    Intent intent = new Intent(MainActivity.this, SignedIn.class);

                                    intent.putExtra("username", nameFromDB);
                                    intent.putExtra("password", passwordFromDB);
                                    Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();

                                } else{
                                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                }
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