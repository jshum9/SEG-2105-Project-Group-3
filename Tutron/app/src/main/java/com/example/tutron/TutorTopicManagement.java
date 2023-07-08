package com.example.tutron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

        onItemLongClick();


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

    private void onItemLongClick() {
        listViewTopics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Topic topic = topics.get(position);
                showRemoveStagedTopicDialog(topic);
            }
        });
    }

    private void showRemoveStagedTopicDialog(Topic topic){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_tutor_manage_staged_topic, null);
        dialogBuilder.setView(dialogView);

        final Button removeTopic = (Button) dialogView.findViewById(R.id.removeTopicBtn);
        final Button cancel = (Button) dialogView.findViewById(R.id.cancelBtn);
        final Button addTopic = (Button) dialogView.findViewById(R.id.addTopicBtn);


        final AlertDialog dialog = dialogBuilder.create();
        dialog.setMessage("Topic: " + topic.getName() + "\nYears of Experience: " + topic.getYearsOfExperience() + "\nDescription: " + topic.getDescription());
        dialog.show();

        //removeTopic Button

        removeTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(topic.getName()).removeValue();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        addTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(topic.getName()).child("isOffered").setValue(true);
                dialog.dismiss();
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