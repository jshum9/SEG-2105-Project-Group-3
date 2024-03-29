package com.example.tutron;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

public class TutorHomePage extends AppCompatActivity {

    Button logOffBtn;

    Button editProfileBtn;

    Button addTopicBtn;

    Button viewPurchaseBtn;
    ListView listViewTopics;


    FirebaseDatabase database;
    DatabaseReference databaseReference;

    List<Topic> topics;


    int numberOfTopicsVisibleToStudents = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_homepage);

        Intent intentRole = getIntent();
        String emailAddress = intentRole.getStringExtra("emailAddress");



        logOffBtn = findViewById(R.id.logOffBtn);
        addTopicBtn = findViewById(R.id.addTopicBtn);
        editProfileBtn = findViewById(R.id.editProfileBtn);
        viewPurchaseBtn = findViewById(R.id.purchaseBtn);

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
                addTopic.putExtra("numberOfTopicsVisibleToStudents", String.valueOf(numberOfTopicsVisibleToStudents));
                startActivity(addTopic);
            }
        });

        viewPurchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewPurchaseIntent = new Intent(TutorHomePage.this,TutorTopicsPurchased.class);
                viewPurchaseIntent.putExtra("emailAddress",emailAddress);
                startActivity(viewPurchaseIntent);
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
                numberOfTopicsVisibleToStudents--;
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
                numberOfTopicsVisibleToStudents--;
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
                numberOfTopicsVisibleToStudents = 0;
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    Topic topic = postSnapshot.getValue(Topic.class);
                    if (topic.getIsOffered()){
                        numberOfTopicsVisibleToStudents++;
                        topics.add(topic);
                    }
                }

                TutorTopicListView adapter = new TutorTopicListView(TutorHomePage.this, topics);
                listViewTopics.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FirebaseError", "Error fetching data: " + error.getMessage());
            }
        });
    }
}