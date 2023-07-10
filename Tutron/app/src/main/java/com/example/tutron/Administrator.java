package com.example.tutron;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Administrator extends AppCompatActivity {

    Button backBtn;
    ListView listViewComplaints;
    List<Complaint> complaints;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    //DatePickerDialog datePickerDialog;
    //Button dateButton;
    //Button pickDateBtn;
    //TextView selectedDateTV;
    Calendar selectedDate;
    TextView dateSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        backBtn = findViewById(R.id.backBtn);
        listViewComplaints = findViewById(R.id.listViewComplaints);
        complaints = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Complaints");

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

    private void onItemLongClick() {
        listViewComplaints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Complaint complaint = complaints.get(position);
                showSuspendOrDismissDialog(complaint.getId(), complaint.getComplaint(), complaint.getTutorEmail(), complaint.getStudentEmail());
                //String selectedComplaint = complaint.getComplaint();
                //Intent intent = new Intent(Administrator.this, ProcessComplaint.class);
                //intent.putExtra()
            }
        });
    }

    private void showSuspendOrDismissDialog(String complaintId, String complaint, String tutor, String student){
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
        //final Button datePicker = (Button) dialogView.findViewById(R.id.datePickerBtn);

        //dialogBuilder.setMessage(complaint).setMessage(tutor).setMessage(student);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.setMessage("Complaint: " + complaint + "\n\nTutor: " + tutor + "\nStudent: " + student);
        dialog.show();

        //Suspension Button

        suspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuspensionOptionsDialog(tutor, complaintId);
                dialog.dismiss();
            }
        });

        //Dismiss Button
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(complaintId).removeValue();
                dialog.dismiss();
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

    private void showSuspensionOptionsDialog(String tutor, String complaintId ) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_suspension, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.setMessage("Do you want to temporarily suspend or permanently suspend?");
        dialog.show();

        final Button tempSuspend = (Button) dialogView.findViewById(R.id.tempBtn);
        final Button permSuspend = (Button) dialogView.findViewById(R.id.permSuspendBtn);
        final Button back = (Button) dialogView.findViewById(R.id.backBtn);

        tempSuspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTempSuspensionDialog(tutor, complaintId);
                dialog.dismiss();
            }
        });

        //TODO: what happens when you click on permanent suspension
        permSuspend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                databaseReference.getParent().child("Users").child(tutor.replace('.', ',')).child("suspension").child("isSuspended").setValue(true);
                databaseReference.getParent().child("Users").child(tutor.replace('.', ',')).child("suspension").child("permanent").setValue(true);

                //getting rid of all complaints with same tutor
                removeEntriesWithCommonChild("tutorEmail", tutor);
                dialog.dismiss();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void showTempSuspensionDialog(String tutor, String complaintId) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_temporary_suspension, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.setMessage("What day do you want the suspension to be over?");
        dialog.show();

        final Button selectDate = (Button) dialogView.findViewById(R.id.datePickerBtn);
        final Button back = (Button) dialogView.findViewById(R.id.backBtn);
        //pickDateBtn = findViewById(R.id.datePickerBtn);
        //*****************TextView selectedDate = findViewById(R.id.selectedDate);
        selectedDate = Calendar.getInstance();

        //TODO: what happens when you want to select a date
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dateButton = findViewById(R.id.datePickerBtn);
                //dateButton.setText(getTodayDate());
                //initDatePicker();
                //openDatePicker(v);
                /*final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Administrator.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //selectedDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();*/
                showDatePicker(tutor, complaintId);
                dialog.dismiss();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void showDatePicker(String tutor, String complaintId) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, monthOfYear);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date suspensionEndDate = new Date(year, monthOfYear + 1, dayOfMonth);

                databaseReference.getParent().child("Users").child(tutor.replace('.', ',')).child("suspension").child("date").setValue(suspensionEndDate);
                databaseReference.getParent().child("Users").child(tutor.replace('.', ',')).child("suspension").child("isSuspended").setValue(true);
                databaseReference.child(complaintId).removeValue();
            }
        }, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
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

    private void removeEntriesWithCommonChild(String childName, String childValue) {
        // Create a query to find all entries with the common child and the specified value
        Query query = databaseReference.orderByChild(childName).equalTo(childValue);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                    entrySnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors if necessary
            }
        });
    }





    //********************************************************************

    /*private void showDateOptionsDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_temporary_suspension, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.setMessage("What day do you want the suspension to be over?");
        dialog.show();
    }

    //TODO: getting the date rough work

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

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, dayOfMonth);
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
    }*/



}