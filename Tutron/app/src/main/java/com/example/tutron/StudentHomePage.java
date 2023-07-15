package com.example.tutron;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentHomePage extends AppCompatActivity {

    Button logOffBtn;

    Button editProfileBtn;

    Button addTopicBtn;

    Button editReviewsBtn;

    List<Topic> topics;
    ListView listViewTopics;
    int numberOfTopicsVisibleToStudents = 0;

    FirebaseDatabase database;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        Intent intentRole = getIntent();
        String emailAddress = intentRole.getStringExtra("emailAddress");

        topics = new ArrayList<>();
        listViewTopics = findViewById(R.id.listViewLearningTopics);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users");

        //TODO firebase implementation
//        database = FirebaseDatabase.getInstance();
//        databaseReference = database.getReference("Users/" + emailAddress + "/Topics");

        logOffBtn = findViewById(R.id.logOffBtn);
        addTopicBtn = findViewById(R.id.addTopicBtn);
        editProfileBtn = findViewById(R.id.editProfileBtn);
        //TODO editReviewBtn
        editReviewsBtn = findViewById(R.id.editReviewsBtn);

        onItemClick();

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

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfile = new Intent(StudentHomePage.this, StudentProfile.class);
                editProfile.putExtra("emailAddress",emailAddress);
                startActivity(editProfile);
            }
        });






    }

    private void onItemClick(){
        listViewTopics.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Topic topic = topics.get(position);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                topics.clear();
                numberOfTopicsVisibleToStudents = 0;
                for(DataSnapshot userSnapshot: snapshot.getChildren()){
                    String userType = userSnapshot.child("type").getValue(String.class);
                    if("TUTOR".equals(userType)){
                        for(DataSnapshot topicSnapshot: userSnapshot.child("Topics").getChildren()){
                            Topic topic = topicSnapshot.getValue(Topic.class);
                            if(topic.getIsOffered()){
                                numberOfTopicsVisibleToStudents++;
                                topics.add(topic);
                        }
                    }

                    }
                }

                TopicList adapter = new TopicList(StudentHomePage.this,topics);
                listViewTopics.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FirebaseError", "Error fetching data: " + error.getMessage());
            }
        });
    }
}