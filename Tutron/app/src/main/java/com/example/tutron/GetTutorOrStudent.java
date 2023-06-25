package com.example.tutron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetTutorOrStudent extends AppCompatActivity {
    Button backBtn;
    Button tutorBtn;
    Button studentBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_tutor_or_student);



        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(GetTutorOrStudent.this, MainActivity.class);
                startActivity(backIntent);
                finish();
            }
        });

        tutorBtn = findViewById(R.id.tutorSelectBtn);
        tutorBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent backIntent = new Intent(GetTutorOrStudent.this, Tutor.class);
                startActivity(backIntent);
                finish();
            }
        });

        studentBtn = findViewById(R.id.studentSelectBtn);
        studentBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent backIntent = new Intent(GetTutorOrStudent.this, Student.class);
                startActivity(backIntent);
                finish();
            }
        });

    }
}