package com.example.tutron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tutor extends AppCompatActivity {

    Button backTutorBtn;
    Button createTutorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        backTutorBtn = findViewById(R.id.tutorBackButton);
        backTutorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(Tutor.this, GetTutorOrStudent.class);
                startActivity(backIntent);
                finish();
            }
        });

        createTutorBtn = findViewById(R.id.tutorCreateButton);
        createTutorBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent backIntent = new Intent(Tutor.this, MainActivity.class);
                startActivity(backIntent);
                finish();
            }
        });
    }
}