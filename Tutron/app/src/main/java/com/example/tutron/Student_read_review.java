package com.example.tutron;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class Student_read_review extends AppCompatActivity {
    private Button backBtn;
    private ListView listViewReview;
    private List<Review> reviews;
    private FirebaseDatabase database;

    DatabaseReference databaseReference;
    private String tutorEmailAddress, topicName, studentEmailAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_read_review);

        backBtn = findViewById(R.id.student_read_review_backBtn);
        reviews = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Reviews");
        listViewReview = findViewById(R.id.reviewListView);


        //Get tutor email and topic name that student clicked.
        Intent intent = getIntent();
        tutorEmailAddress = intent.getStringExtra("tutorEmailAddress");
        topicName = intent.getStringExtra("topicName");
        studentEmailAddress = intent.getStringExtra("studentEmailAddress");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(Student_read_review.this, StudentSelectTutor.class);
                backIntent.putExtra("tutorEmailAddress",tutorEmailAddress);
                backIntent.putExtra("studentEmailAddress",studentEmailAddress);
                startActivity(backIntent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reviews.clear();
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    Review review = postSnapshot.getValue(Review.class);

                    if(review.getTutorEmail().equals(tutorEmailAddress) && review.getTutorTopic().equals(topicName)){
                        reviews.add(review);
                    }

                }
                ReviewList adapter = new ReviewList(Student_read_review.this,reviews);
                listViewReview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
