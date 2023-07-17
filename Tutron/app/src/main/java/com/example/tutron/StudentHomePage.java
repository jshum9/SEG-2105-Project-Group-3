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
import android.widget.Toast;

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

    List<Topic> ownedTopics;

    List<PurchaseRequest> purchaseRequests;
    ListView listViewOwnedTopics;

    ListView listViewPurchaseRequest;


    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference ownedTopicsDatabaseReference;
    DatabaseReference reviewsRef;

    private String emailAddress;

    DatabaseReference purchaseRequestRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        Intent intentRole = getIntent();
        emailAddress = intentRole.getStringExtra("emailAddress");

        ownedTopics = new ArrayList<>();
        purchaseRequests = new ArrayList<>();
        listViewOwnedTopics = findViewById(R.id.listViewLearningTopics);
        listViewPurchaseRequest = findViewById(R.id.listViewPurchaseRequests);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users/" + emailAddress);
        ownedTopicsDatabaseReference = databaseReference.child("OwnedTopics");
        reviewsRef = database.getReference().child("Reviews");
        purchaseRequestRef = database.getReference("PurchaseRequest");




        logOffBtn = findViewById(R.id.logOffBtn);
        addTopicBtn = findViewById(R.id.addTopicBtn);
        editProfileBtn = findViewById(R.id.editProfileBtn);
        //TODO editReviewBtn
        editReviewsBtn = findViewById(R.id.editReviewsBtn);

        listViewOwnedTopicsOnItemClick(emailAddress);

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

    private void listViewOwnedTopicsOnItemClick(String studentEmail){
        listViewOwnedTopics.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Topic ownedTopic = ownedTopics.get(position);

                showReviewOrComplainDialog(ownedTopic, studentEmail);
            }
        });
    }

    private void showReviewOrComplainDialog(Topic topic, String studentEmail){
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_student_complain_or_review, null);
        dialogBuilder.setView(dialogView);

        final Button complainBtn = (Button) dialogView.findViewById(R.id.complainBtn);
        final Button reviewBtn = (Button) dialogView.findViewById(R.id.reviewBtn);

        final AlertDialog dialog = dialogBuilder.create();

        dialog.show();

        complainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent makeComplaint = new Intent(StudentHomePage.this, StudentMakeComplaint.class);
                makeComplaint.putExtra("studentEmailAddress", studentEmail);
                makeComplaint.putExtra("tutorEmailAddress", topic.getTutorEmail());
                startActivity(makeComplaint);
            }
        });

        //TODO: What happens when Student wants to make a review?
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reviewsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean flag = false;
                        for (DataSnapshot reviewSnapshot : snapshot.getChildren()){
                            if (reviewSnapshot.child("studentEmail").getValue(String.class).equals(studentEmail) && reviewSnapshot.child("tutorEmail").getValue(String.class).equals(topic.getTutorEmail().replace('.', ',')) && reviewSnapshot.child("tutorTopic").getValue(String.class).equals(topic.getDescription())){
                                flag = true;


                            }
                        }

                        if (flag == true){
                            Toast.makeText(StudentHomePage.this, "You've already written a review! Click on \"Edit Reviews\" if you would like to edit it.", Toast.LENGTH_SHORT).show();
                        }

                        else{
                            Intent makeReview = new Intent(StudentHomePage.this, StudentMakeReview.class);
                            makeReview.putExtra("studentEmailAddress", studentEmail);
                            makeReview.putExtra("tutorEmailAddress", topic.getTutorEmail());
                            makeReview.putExtra("topicName", topic.getDescription());
                            makeReview.putExtra("reviewDate",System.currentTimeMillis());
                            startActivity(makeReview);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        ownedTopicsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ownedTopics.clear();
                for(DataSnapshot topicSnapshot: snapshot.getChildren()){
                    Topic ownedTopic = topicSnapshot.getValue(Topic.class);
                    ownedTopics.add(ownedTopic);
                }

                StudentOwnedTopicListView adapter = new StudentOwnedTopicListView(StudentHomePage.this, ownedTopics);
                listViewOwnedTopics.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FirebaseError", "Error fetching data: " + error.getMessage());
                Toast.makeText(StudentHomePage.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        purchaseRequestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                purchaseRequests.clear();
                for(DataSnapshot requestSnapshot: snapshot.getChildren()){
                    PurchaseRequest request = requestSnapshot.getValue(PurchaseRequest.class);
                    if(request.getStudentEmail().equals(emailAddress)){
                        purchaseRequests.add(request);
                    }

                }
                PurchaseRequestList adapter = new PurchaseRequestList(StudentHomePage.this,purchaseRequests);
                listViewPurchaseRequest.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FirebaseError", "Error fetching data: " + error.getMessage());
                Toast.makeText(StudentHomePage.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }



}