package com.example.tutron;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentSelectTutor extends AppCompatActivity {

    private TextView nameTextView;
    private TextView selfIntroductionTextView;
    private TextView topicsTaughtTitleTextView;
    private ListView topicsTaughtListView;

    private String tutorEmailAddress,studentEmailAddress;

    private FirebaseDatabase database;
    private DatabaseReference tutorReference;
    private DatabaseReference databaseReference;

    private ArrayList<Topic> topics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_select_tutor);

        // Initialize the views
        nameTextView = findViewById(R.id.nameTextView);
        selfIntroductionTextView = findViewById(R.id.selfIntroductionTextView);
        topicsTaughtListView = findViewById(R.id.topicsTaughtListView);
        topics = new ArrayList<>();


        Intent intentRole = getIntent();
        tutorEmailAddress = intentRole.getStringExtra("tutorEmailAddress");
        studentEmailAddress = intentRole.getStringExtra("studentEmailAddress");

        database = FirebaseDatabase.getInstance();
        tutorReference = database.getReference("Users/" + tutorEmailAddress);
        databaseReference = database.getReference("PurchaseRequest");

        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentSelectTutor.this, StudentTopicManagement.class);
                intent.putExtra("emailAddress", studentEmailAddress);
                startActivity(intent);
                finish();
            }
        });

        tutorReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstName = dataSnapshot.child("firstName").getValue(String.class);
                String lastName = dataSnapshot.child("lastName").getValue(String.class);
                String description = dataSnapshot.child("description").getValue(String.class);

                nameTextView.setText(Character.toUpperCase(firstName.charAt(0)) + firstName.substring(1)  + " " + Character.toUpperCase(lastName.charAt(0)) + lastName.substring(1));
                selfIntroductionTextView.setText(description);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
                // ...
            }
        });

        tutorReference.child("Topics").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                topics.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    Topic topic = postSnapshot.getValue(Topic.class);
                    if (topic.getIsOffered()){
                        topics.add(topic);
                    }

                }

                StudentSelectTutorTopicListViewAdapter adapter = new StudentSelectTutorTopicListViewAdapter(StudentSelectTutor.this, topics);
                topicsTaughtListView.setAdapter(adapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        onItemLongClick();




    }

    private void onItemLongClick() {
        topicsTaughtListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Topic topic = topics.get(position);

                showRemoveStagedTopicDialog(topic);
            }
        });
    }

    private void showRemoveStagedTopicDialog(Topic topic){
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_student_long_click_topic_to_schedule, null);
        dialogBuilder.setView(dialogView);

        final Button viewReviewsButton = (Button) dialogView.findViewById(R.id.viewReviewsButton);
        final Button scheduleALesson = (Button) dialogView.findViewById(R.id.scheduleLessonButton);

        final AlertDialog dialog = dialogBuilder.create();

        dialog.show();



        viewReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewReviewIntent = new Intent(StudentSelectTutor.this, Student_read_review.class);
                viewReviewIntent.putExtra("tutorEmailAddress", tutorEmailAddress);
                viewReviewIntent.putExtra("topicName", topic.getName());
                viewReviewIntent.putExtra("studentEmailAddress",studentEmailAddress);
                startActivity(viewReviewIntent);
                finish();
            }
        });


        scheduleALesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = databaseReference.push().getKey();
                PurchaseRequest purchaseRequest = new PurchaseRequest(id,studentEmailAddress,tutorEmailAddress,topic.getName(),System.currentTimeMillis(),"Pending");
                assert id != null;
                databaseReference.child(id).setValue(purchaseRequest);
                Toast.makeText(StudentSelectTutor.this, "Purchase the lesson successfully!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

}
