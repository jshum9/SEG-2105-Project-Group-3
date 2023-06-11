package com.example.tutron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                boolean userData = false;
                //check if the input is null
                if(!TextUtils.isEmpty(userNameTemp) && !TextUtils.isEmpty(userPasswordTemp) ){
                    //Something needs to be done here to tell the user is a student or a tutor.
                    //Code needed
                    if(userNameTemp.equals("xli532@uottawa.ca") && userPasswordTemp.equals("123456")){
                        userData = true;
                    }
                    if (userData) {
                        Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,SignedIn.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(MainActivity.this,"User name or password cannot be empty.",Toast.LENGTH_SHORT).show();
                }

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
        });
    }
}