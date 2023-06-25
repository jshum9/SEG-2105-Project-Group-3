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

        Intent intentRole = getIntent();
        String role = intentRole.getStringExtra("role");
        roleTextview.setText(role);

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