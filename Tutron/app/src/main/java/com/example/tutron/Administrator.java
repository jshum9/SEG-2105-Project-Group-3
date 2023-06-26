package com.example.tutron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Administrator extends AppCompatActivity {

    Button backBtn;
    ListView listViewComplaints;
    List<Complaint> complaints;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    DatePickerDialog datePickerDialog;
    Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        backBtn = findViewById(R.id.backBtn);
        listViewComplaints = findViewById(R.id.listViewComplaints);
        complaints = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Complaints");
        initDatePicker();
        //dateButton = findViewById(R.id.datePickerBtn);
        dateButton.setText(getTodayDate());
        onItemLongClick();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(Administrator.this,MainActivity.class);
                startActivity(backIntent);
                finish();
            }
        });
    }

    private String getTodayDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(dayOfMonth, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, dayOfMonth);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month){
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";
        //DEFAULT CASE
        return "DEFUALT";
    }

    public void openDatePicker(View view){
        datePickerDialog.show();
    }


    private void onItemLongClick() {
        listViewComplaints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Complaint complaint = complaints.get(position);
                showSuspendDismissDialog(complaint.getId(), complaint.getComplaint(), complaint.getTutorEmail(), complaint.getStudentEmail());
                //String selectedComplaint = complaint.getComplaint();
                //Intent intent = new Intent(Administrator.this, ProcessComplaint.class);
                //intent.putExtra()
            }
        });
    }

    private void showSuspendDismissDialog(String complaintId, String complaint, String tutor, String student){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_process_complaint, null);
        dialogBuilder.setView(dialogView);

        //final TextView complaintText = (TextView) dialogView.findViewById(R.id.textComplaint);
        //final Spinner yearSpinner = (Spinner) dialogView.findViewById(R.id.yearSpinner);
        //final Spinner monthSpinner = (Spinner) dialogView.findViewById(R.id.monthSpinner);
        //final Spinner daySpinner = (Spinner) dialogView.findViewById(R.id.daySpinner);
        final Button suspend = (Button) dialogView.findViewById(R.id.suspendBtn);
        final Button dismiss = (Button) dialogView.findViewById(R.id.dismissBtn);
        final Button back = (Button) dialogView.findViewById(R.id.backBtn);
        final Button datePicker = (Button) dialogView.findViewById(R.id.datePickerBtn);

        //dialogBuilder.setMessage(complaint).setMessage(tutor).setMessage(student);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.setMessage("Complaint: " + complaint + "\nTutor: " + tutor + "\nStudent: " + student);
        dialog.show();

        //TODO: figure out how to implement the spinners to select date

        //Suspension Button
        //TODO: complete function so that admin can suspend tutor
        suspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Dismiss Button
        //TODO: complete function so that admin can dismiss complaint
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Back Button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
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