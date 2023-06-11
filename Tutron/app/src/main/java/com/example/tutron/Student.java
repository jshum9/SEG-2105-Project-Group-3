package com.example.tutron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Student extends AppCompatActivity {
    EditText firstName,lastName,emailAddress,password,address;
    Button backBtn,createBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        emailAddress = findViewById(R.id.emailAddress);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);

        backBtn = findViewById(R.id.studentBackButton);
        createBtn = findViewById(R.id.studentCreateButton);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(Student.this, MainActivity.class);
                startActivity(backIntent);
                finish();
            }

        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameTemp = firstName.getText().toString().trim();
                String lastNameTemp = lastName.getText().toString().trim();
                String emailAddressTemp = emailAddress.getText().toString().trim();
                String passwordTemp = password.getText().toString().trim();
                String addressTemp = address.getText().toString().trim();
                Boolean dataSaved = false;

                if(!TextUtils.isEmpty(firstNameTemp) && !TextUtils.isEmpty(lastNameTemp) && (!TextUtils.isEmpty(emailAddressTemp))
                        && !TextUtils.isEmpty(passwordTemp) && !TextUtils.isEmpty(addressTemp)){
                    //save data code needed
                    dataSaved = true;
                }
                if(dataSaved){

                    Intent register = new Intent(Student.this, MainActivity.class);
                    startActivity(register);
                    finish();
                }

            }
        });
    }
}