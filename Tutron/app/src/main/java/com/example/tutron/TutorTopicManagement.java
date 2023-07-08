package com.example.tutron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TutorTopicManagement extends AppCompatActivity {

    Button backBtn;

    Button createTopicBtn;


    ListView listViewTopics;


    FirebaseDatabase database;
    DatabaseReference databaseReference;
    List<Topic> topics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_topic_management);

        Intent intentRole = getIntent();
        String emailAddress = intentRole.getStringExtra("emailAddress");

        backBtn = findViewById(R.id.backBtn);
        createTopicBtn = findViewById(R.id.addTopicBtn);

        listViewTopics = findViewById(R.id.listViewTopics);
        topics = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users/" + emailAddress + "/Topics");


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(TutorTopicManagement.this, TutorHomePage.class);
                backIntent.putExtra("emailAddress", emailAddress);
                startActivity(backIntent);
                finish();
            }
        });

        createTopicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createTopic = new Intent(TutorTopicManagement.this, TutorCreateTopic.class);
                createTopic.putExtra("emailAddress", emailAddress);
                startActivity(createTopic);
                finish();
            }
        });

    }

    protected void onStart(){
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                topics.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    Topic topic = postSnapshot.getValue(Topic.class);
                    if (!topic.getIsOffered()){
                        topics.add(topic);
                    }

                }

                TopicList adapter = new TopicList(TutorTopicManagement.this, topics);
                listViewTopics.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}