package com.example.tutron;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TutorCreateTopic extends AppCompatActivity {
    Button saveBtn;
    Button cancelBtn;
    EditText topicNameEditText;
    EditText yearsOfExperienceEditText;
    EditText topicDescriptionEditText;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    int numberOfTopicsVisibleToStudents = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_create_topic);

        Intent intentRole = getIntent();
        String emailAddress = intentRole.getStringExtra("emailAddress");
        String numberOfTopicsVisibleToStudentsStr = intentRole.getStringExtra("numberOfTopicsVisibleToStudents");


        if (!numberOfTopicsVisibleToStudentsStr.isEmpty()){
            numberOfTopicsVisibleToStudents = Integer.parseInt(numberOfTopicsVisibleToStudentsStr);
        }


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users/" + emailAddress + "/Topics");

        saveBtn = findViewById(R.id.saveBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        topicNameEditText = findViewById(R.id.topicName);
        yearsOfExperienceEditText = findViewById(R.id.yearsOfExperience);
        topicDescriptionEditText = findViewById(R.id.topicDescription);


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(TutorCreateTopic.this, TutorTopicManagement.class);
                backIntent.putExtra("emailAddress", emailAddress);
                backIntent.putExtra("numberOfTopicsVisibleToStudents", String.valueOf(numberOfTopicsVisibleToStudents));
                startActivity(backIntent);
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topicName = topicNameEditText.getText().toString().trim().toLowerCase();
                String yearsOfExperienceStr = yearsOfExperienceEditText.getText().toString().trim();
                String topicDescription = topicDescriptionEditText.getText().toString();

                if (!topicName.isEmpty() && !topicDescription.isEmpty() && !yearsOfExperienceStr.isEmpty()){
                    Topic c = new Topic(topicName, Integer.parseInt(yearsOfExperienceStr), topicDescription);
                    databaseReference.child(topicName).setValue(c);
                    Intent backIntent = new Intent(TutorCreateTopic.this, TutorTopicManagement.class);
                    backIntent.putExtra("emailAddress", emailAddress);
                    backIntent.putExtra("numberOfTopicsVisibleToStudents", String.valueOf(numberOfTopicsVisibleToStudents));
                    startActivity(backIntent);
                    finish();
                } else{
                    Toast.makeText(TutorCreateTopic.this, "Make sure all field are filled out!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}