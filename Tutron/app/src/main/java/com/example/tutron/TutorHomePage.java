package com.example.tutron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class TutorHomePage extends AppCompatActivity {

    Button logOffBtn;

    //TODO: what happens when we click on editProfileBtn
    Button editProfileBtn;

    Button addTopicBtn;
    ListView listViewTopics;


    FirebaseDatabase database;
    DatabaseReference databaseReference;

    List<Topic> topics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_homepage);

        Intent intentRole = getIntent();
        String emailAddress = intentRole.getStringExtra("emailAddress");

        logOffBtn = findViewById(R.id.logOffBtn);
        addTopicBtn = findViewById(R.id.addTopicBtn);
        editProfileBtn = findViewById(R.id.editProfileBtn);

        listViewTopics = findViewById(R.id.listViewTopics);
        topics = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users/" + emailAddress + "/Topics");

        onItemLongClick();

        logOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(TutorHomePage.this, MainActivity.class);
                startActivity(backIntent);
                finish();
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(TutorHomePage.this, TutorProfile.class);
                editIntent.putExtra("emailAddress",emailAddress);
                startActivity(editIntent);
            }
        });

        addTopicBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent addTopic = new Intent(TutorHomePage.this, TutorTopicManagement.class);
                addTopic.putExtra("emailAddress", emailAddress);
                startActivity(addTopic);
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
        final View dialogView = inflater.inflate(R.layout.activity_tutor_manage_teaching_topic, null);
        dialogBuilder.setView(dialogView);

        final Button deleteTopic = (Button) dialogView.findViewById(R.id.deleteTopicBtn);
        final Button cancel = (Button) dialogView.findViewById(R.id.cancelBtn);
        final Button hideTopic = (Button) dialogView.findViewById(R.id.hideTopicBtn);


        final AlertDialog dialog = dialogBuilder.create();
        dialog.setMessage("Topic: " + topic.getName() + "\nYears of Experience: " + topic.getYearsOfExperience() + "\nDescription: " + topic.getDescription());
        dialog.show();

        //removeTopic Button

        deleteTopic.setOnClickListener(new View.OnClickListener() {
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

        hideTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(topic.getName()).child("isOffered").setValue(false);
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
                    if (topic.getIsOffered()){
                        topics.add(topic);
                    }
                }

                TopicList adapter = new TopicList(TutorHomePage.this, topics);
                listViewTopics.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FirebaseError", "Error fetching data: " + error.getMessage());
            }
        });
    }
}