package com.example.tutron;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// SearchActivity.java
public class StudentTopicManagement extends AppCompatActivity {

    private EditText editTextTutorName;
    private EditText editTextLanguage;
    private EditText editTextTopic;

    private Button searchBtn;
    private Button backBtn;

    private List<TutorAccount> tutors;

    private ListView tutorSearchResults;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_topic_management);

        Intent intentRole = getIntent();
        String emailAddress = intentRole.getStringExtra("emailAddress");


        editTextTutorName = findViewById(R.id.editTextTutorName);
        editTextLanguage = findViewById(R.id.editTextLanguage);
        editTextTopic = findViewById(R.id.editTextTopic);
        searchBtn = findViewById(R.id.searchBtn);
        backBtn = findViewById(R.id.backBtn);

        tutors = new ArrayList<>();
        tutorSearchResults = findViewById(R.id.tutorSearchResults);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users");

        onItemLongClick(emailAddress);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(StudentTopicManagement.this, StudentHomePage.class);
                backIntent.putExtra("emailAddress", emailAddress);
                startActivity(backIntent);
                finish();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tutorName = editTextTutorName.getText().toString().trim().toLowerCase();
                String language = editTextLanguage.getText().toString().trim().toLowerCase();
                String topic = editTextTopic.getText().toString().trim().toLowerCase();

                if (tutorName.isEmpty() && language.isEmpty() && topic.isEmpty()) {
                    Toast.makeText(StudentTopicManagement.this, "Please specify at least 1 search field", Toast.LENGTH_SHORT).show();
                } else {
                    searchTutors(tutorName, language, topic);
                }

            }
        });
    }

    private void onItemLongClick(String emailAddress) {
        tutorSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TutorAccount tutor = tutors.get(position);
                Intent intent = new Intent(StudentTopicManagement.this, StudentSelectTutor.class);
                intent.putExtra("tutorEmailAddress", tutor.getEmailAddress().replace('.', ','));
                intent.putExtra("studentEmailAddress", emailAddress);
                startActivity(intent);
            }
        });
    }



    private void searchTutors(String tutorName, String language, String topic) {

        Query query = databaseReference;
        query = query.orderByChild("type").equalTo("TUTOR");


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    tutors.clear();


                    if (!tutorName.isEmpty() && !language.isEmpty() && !topic.isEmpty()) {
                        for (DataSnapshot tutorSnapshot : dataSnapshot.getChildren()) {
                            if (tutorSnapshot.child("suspension").child("isSuspended").getValue(boolean.class) == false){
                                TutorAccount tutor = new TutorAccount(tutorSnapshot.child("firstName").getValue(String.class), tutorSnapshot.child("lastName").getValue(String.class), tutorSnapshot.child("emailAddress").getValue(String.class), "", "", tutorSnapshot.child("nativeLanguage").getValue(String.class), "");
                                if (tutor.getFirstName().equals(tutorName) && tutor.getNativeLanguage().equals(language) && tutorSnapshot.hasChild("Topics") && tutorSnapshot.child("Topics").hasChild(topic)) {
                                    if (tutorSnapshot.child("Topics").child(topic).child("isOffered").getValue(boolean.class) == true) {
                                        tutors.add(tutor);
                                    }
                                }
                            }
                        }

                        StudentShowTutorAccountListViewAdapter adapter = new StudentShowTutorAccountListViewAdapter(StudentTopicManagement.this, tutors);
                        tutorSearchResults.setAdapter(adapter);
                    } else if (!tutorName.isEmpty() && !language.isEmpty() && topic.isEmpty()) {
                        for (DataSnapshot tutorSnapshot : dataSnapshot.getChildren()) {
                            if (tutorSnapshot.child("suspension").child("isSuspended").getValue(boolean.class) == false){
                                TutorAccount tutor = new TutorAccount(tutorSnapshot.child("firstName").getValue(String.class), tutorSnapshot.child("lastName").getValue(String.class), tutorSnapshot.child("emailAddress").getValue(String.class), "", "", tutorSnapshot.child("nativeLanguage").getValue(String.class), "");
                                if (tutor.getFirstName().equals(tutorName) && tutor.getNativeLanguage().equals(language)) {
                                    tutors.add(tutor);
                                }
                            }

                        }

                        StudentShowTutorAccountListViewAdapter adapter = new StudentShowTutorAccountListViewAdapter(StudentTopicManagement.this, tutors);
                        tutorSearchResults.setAdapter(adapter);
                    } else if (!tutorName.isEmpty() && language.isEmpty() && !topic.isEmpty()) {
                        for (DataSnapshot tutorSnapshot : dataSnapshot.getChildren()) {
                            if (tutorSnapshot.child("suspension").child("isSuspended").getValue(boolean.class) == false){
                                TutorAccount tutor = new TutorAccount(tutorSnapshot.child("firstName").getValue(String.class), tutorSnapshot.child("lastName").getValue(String.class), tutorSnapshot.child("emailAddress").getValue(String.class), "", "", tutorSnapshot.child("nativeLanguage").getValue(String.class), "");
                                if (tutor.getFirstName().equals(tutorName) && tutorSnapshot.hasChild("Topics") && tutorSnapshot.child("Topics").hasChild(topic)) {
                                    if (tutorSnapshot.child("Topics").child(topic).child("isOffered").getValue(boolean.class) == true) {
                                        tutors.add(tutor);
                                    }
                                }
                            }

                        }

                        StudentShowTutorAccountListViewAdapter adapter = new StudentShowTutorAccountListViewAdapter(StudentTopicManagement.this, tutors);
                        tutorSearchResults.setAdapter(adapter);
                    } else if (tutorName.isEmpty() && !language.isEmpty() && !topic.isEmpty()) {
                        for (DataSnapshot tutorSnapshot : dataSnapshot.getChildren()) {
                            if (tutorSnapshot.child("suspension").child("isSuspended").getValue(boolean.class) == false){
                                TutorAccount tutor = new TutorAccount(tutorSnapshot.child("firstName").getValue(String.class), tutorSnapshot.child("lastName").getValue(String.class), tutorSnapshot.child("emailAddress").getValue(String.class), "", "", tutorSnapshot.child("nativeLanguage").getValue(String.class), "");
                                if (tutor.getNativeLanguage().equals(language) && tutorSnapshot.hasChild("Topics") && tutorSnapshot.child("Topics").hasChild(topic)) {
                                    if (tutorSnapshot.child("Topics").child(topic).child("isOffered").getValue(boolean.class) == true) {
                                        tutors.add(tutor);
                                    }
                                }
                            }

                        }

                        StudentShowTutorAccountListViewAdapter adapter = new StudentShowTutorAccountListViewAdapter(StudentTopicManagement.this, tutors);
                        tutorSearchResults.setAdapter(adapter);
                    } else if (!tutorName.isEmpty() && language.isEmpty() && topic.isEmpty()) {
                        for (DataSnapshot tutorSnapshot : dataSnapshot.getChildren()) {
                            if (tutorSnapshot.child("suspension").child("isSuspended").getValue(boolean.class) == false){
                                TutorAccount tutor = new TutorAccount(tutorSnapshot.child("firstName").getValue(String.class), tutorSnapshot.child("lastName").getValue(String.class), tutorSnapshot.child("emailAddress").getValue(String.class), "", "", tutorSnapshot.child("nativeLanguage").getValue(String.class), "");
                                if (tutor.getFirstName().equals(tutorName)) {
                                    tutors.add(tutor);
                                }
                            }

                        }

                        StudentShowTutorAccountListViewAdapter adapter = new StudentShowTutorAccountListViewAdapter(StudentTopicManagement.this, tutors);
                        tutorSearchResults.setAdapter(adapter);
                    } else if (tutorName.isEmpty() && !language.isEmpty() && topic.isEmpty()) {
                        for (DataSnapshot tutorSnapshot : dataSnapshot.getChildren()) {
                            if (tutorSnapshot.child("suspension").child("isSuspended").getValue(boolean.class) == false){
                                TutorAccount tutor = new TutorAccount(tutorSnapshot.child("firstName").getValue(String.class), tutorSnapshot.child("lastName").getValue(String.class), tutorSnapshot.child("emailAddress").getValue(String.class), "", "", tutorSnapshot.child("nativeLanguage").getValue(String.class), "");
                                if (tutor.getNativeLanguage().equals(language)) {
                                    tutors.add(tutor);
                                }
                            }

                        }

                        StudentShowTutorAccountListViewAdapter adapter = new StudentShowTutorAccountListViewAdapter(StudentTopicManagement.this, tutors);
                        tutorSearchResults.setAdapter(adapter);
                    } else if (tutorName.isEmpty() && language.isEmpty() && !topic.isEmpty()) {
                        for (DataSnapshot tutorSnapshot : dataSnapshot.getChildren()) {
                            if (tutorSnapshot.child("suspension").child("isSuspended").getValue(boolean.class) == false){
                                TutorAccount tutor = new TutorAccount(tutorSnapshot.child("firstName").getValue(String.class), tutorSnapshot.child("lastName").getValue(String.class), tutorSnapshot.child("emailAddress").getValue(String.class), "", "", tutorSnapshot.child("nativeLanguage").getValue(String.class), "");
                                if (tutorSnapshot.hasChild("Topics") && tutorSnapshot.child("Topics").hasChild(topic)) {
                                    if (tutorSnapshot.child("Topics").child(topic).child("isOffered").getValue(boolean.class) == true) {
                                        tutors.add(tutor);
                                    }
                                }
                            }

                        }

                        StudentShowTutorAccountListViewAdapter adapter = new StudentShowTutorAccountListViewAdapter(StudentTopicManagement.this, tutors);
                        tutorSearchResults.setAdapter(adapter);
                    }


                    if (tutors.isEmpty()) {
                        Toast.makeText(StudentTopicManagement.this, "We could not find a tutor who matches your search!", Toast.LENGTH_SHORT).show();
                        tutors.clear();
                        StudentShowTutorAccountListViewAdapter adapter = new StudentShowTutorAccountListViewAdapter(StudentTopicManagement.this, tutors);
                        tutorSearchResults.setAdapter(adapter);
                    }

                } else {
                    Toast.makeText(StudentTopicManagement.this, "We could not find a tutor who matches your search!", Toast.LENGTH_SHORT).show();
                    tutors.clear();
                    StudentShowTutorAccountListViewAdapter adapter = new StudentShowTutorAccountListViewAdapter(StudentTopicManagement.this, tutors);
                    tutorSearchResults.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors during the search
            }
        });
    }



}
