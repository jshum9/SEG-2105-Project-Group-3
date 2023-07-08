package com.example.tutron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignedIn extends AppCompatActivity {
    Button continueBtn,logOffBtn;
    TextView roleTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in);

        roleTextview = findViewById(R.id.role);
        logOffBtn = findViewById(R.id.logOffBtn);
        continueBtn = findViewById(R.id.continueBtn);

        //Get the type and email address of tutor from MainActivity
        Intent intentRole = getIntent();
        String role = intentRole.getStringExtra("type");
        String emailAddress = intentRole.getStringExtra("emailAddress");
        roleTextview.setText(role);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If it's admin, go to admin page.
                if(role.equals("Administrator")){
                    Intent adminIntent = new Intent(SignedIn.this, Administrator.class);
                    startActivity(adminIntent);
                }
                //If it's tutor, go to tutor page.
                if(role.equals("TUTOR")){
                    Intent tutorIntent = new Intent(SignedIn.this, TutorProfile.class);
                    tutorIntent.putExtra("emailAddress",emailAddress);
                    startActivity(tutorIntent);
                }
            }
        });

        logOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent(SignedIn.this, MainActivity.class);
                startActivity(returnIntent);
                finish();
            }
        });
    }


}