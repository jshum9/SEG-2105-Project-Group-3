package com.example.tutron;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class TutorTopicsPurchased extends AppCompatActivity {

    Button backBtn;

    List<PurchaseRequest> purchaseRequests1;

    ListView listViewPurchaseRequest1;

    private String tutorEmailAddress;

    FirebaseDatabase database;

    DatabaseReference requestReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_topics_purchased);

        backBtn = findViewById(R.id.purchase_backBtn);

        purchaseRequests1 = new ArrayList<>();
        listViewPurchaseRequest1 = findViewById(R.id.student_purchase_request_list);

        database = FirebaseDatabase.getInstance();
        requestReference = database.getReference("PurchaseRequest");

        Intent emailIntent = getIntent();
        tutorEmailAddress = emailIntent.getStringExtra("emailAddress");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(TutorTopicsPurchased.this, TutorHomePage.class);
                backIntent.putExtra("emailAddress",tutorEmailAddress);
                startActivity(backIntent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        requestReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                purchaseRequests1.clear();
                for(DataSnapshot requestSnapshot: snapshot.getChildren()){
                    PurchaseRequest request = requestSnapshot.getValue(PurchaseRequest.class);
                    if(request.getTutorEmail().equals(tutorEmailAddress)){
                        purchaseRequests1.add(request);
                    }

                }
                PurchaseRequestList adapter = new PurchaseRequestList(TutorTopicsPurchased.this,purchaseRequests1);
                listViewPurchaseRequest1.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FirebaseError", "Error fetching data: " + error.getMessage());
                Toast.makeText(TutorTopicsPurchased.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}