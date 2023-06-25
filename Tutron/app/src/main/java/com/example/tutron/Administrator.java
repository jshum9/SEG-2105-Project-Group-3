package com.example.tutron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Administrator extends AppCompatActivity {

    Button backBtn;
    ListView listViewComplaints;
    List<Complaint> complaints;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        backBtn = findViewById(R.id.backBtn);
        listViewComplaints = findViewById(R.id.listViewComplaints);
        complaints = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Complaints");

        //////////////////////TEST CASE DELETE BEFORE SUBMISSION/////////////////////
        String id = databaseReference.push().getKey();
        Complaint complaint = new Complaint("This is a test complaint. I don't like my Tutor, he sucks. Give me new one! I want my money BACK!!!! I got bad grade.... Bla bla..adfajdf;lajdfl;ajdfljal;kdjfal;sjdflajdfl;jasdl;kfjals;djf;alksdjfla;ksdjflkajsdl;fkjasld;jfal;ksdjfl;kajsdlf;kjasldk;fjalksdjflkajsdlfkjalksdjflak;sdjfl;ajsdfl;kajsdfl;kjasdlkfjalds;kjfal;sdjflajdfl;kajdslf;kjasdl;kfjal;sdjfl;ajsd;flakjdsfl;kajsd;lfkjasdl;kfjaldks;jfl;kasdjflasjf");
        databaseReference.child(id).setValue(complaint);
        /////////////////////////////////////////////////////////////////////////////


    }

    protected void onStart(){
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaints.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    Complaint complaint = postSnapshot.getValue(Complaint.class);
                    complaints.add(complaint);
                }

                ComplaintList adapter = new ComplaintList(Administrator.this, complaints);
                listViewComplaints.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}