package com.example.tutron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class StudentHomePage extends AppCompatActivity {

    Button logOffBtn;

    Button editProfileBtn;

    Button addTopicBtn;

    Button editReviewsBtn;

    FirebaseDatabase database;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        Intent intentRole = getIntent();
        String emailAddress = intentRole.getStringExtra("emailAddress");

        //TODO firebase implementation
//        database = FirebaseDatabase.getInstance();
//        databaseReference = database.getReference("Users/" + emailAddress + "/Topics");

        logOffBtn = findViewById(R.id.logOffBtn);
        addTopicBtn = findViewById(R.id.addTopicBtn);
        //TODO editProfileBtn
        editProfileBtn = findViewById(R.id.editProfileBtn);
        //TODO editReviewBtn
        editReviewsBtn = findViewById(R.id.editReviewsBtn);

        logOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(StudentHomePage.this, MainActivity.class);
                startActivity(backIntent);
                finish();
            }
        });

        addTopicBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent addTopic = new Intent(StudentHomePage.this, StudentTopicManagement.class);
                addTopic.putExtra("emailAddress", emailAddress);
                startActivity(addTopic);
            }
        });




    }
}