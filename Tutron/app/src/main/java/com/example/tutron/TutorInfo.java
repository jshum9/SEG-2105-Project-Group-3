package com.example.tutron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TutorInfo extends AppCompatActivity {
    Button backBtn;
    TextView suspensionTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_info);

        backBtn = findViewById(R.id.backBtn);
        suspensionTime = findViewById(R.id.suspensionTime);

        suspensionTime.setText(getIntent().getStringExtra("suspensionTime"));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(TutorInfo.this, MainActivity.class);
                startActivity(backIntent);
                finish();
            }
        });
    }
}