package com.example.tutron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignedIn extends AppCompatActivity {
    Button returnHomeBtn,logOffBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in);

        returnHomeBtn = findViewById(R.id.returnBtn);
        logOffBtn = findViewById(R.id.logOffBtn);

        logOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent(SignedIn.this, View.class);
                startActivity(returnIntent);
                finish();
            }
        });
    }
}