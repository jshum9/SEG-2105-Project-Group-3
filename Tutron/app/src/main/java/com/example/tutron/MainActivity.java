package com.example.tutron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    EditText userName,userPassword;
    Button loginBtn,registerBtn;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://seg-2105-group-project-f5fd7-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Get the user name and password
                //Replacing "." with "," since we are using the email as a key in our database
                String userNameTemp = userName.getText().toString().trim().replace(".", ",");
                String userPasswordTemp = userPassword.getText().toString().trim();
                if(!TextUtils.isEmpty(userNameTemp) && !TextUtils.isEmpty(userPasswordTemp) ){
                    //Something needs to be done here to tell the user is a student or a tutor.
                    //Code needed

                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if user name is in database
                            if(snapshot.hasChild(userNameTemp)){
                                //user name exists in database
                                String getPassword = snapshot.child(userNameTemp).child("password").getValue(String.class);
                                if (getPassword.equals(userPasswordTemp)){
                                    String role = snapshot.child(userNameTemp).child("type").getValue(String.class);
                                    if(role.equals("TUTOR")){
                                        if(snapshot.child(userNameTemp).hasChild("suspension")){
                                            boolean status = snapshot.child(userNameTemp).child("suspension").child("isSuspended").getValue(boolean.class);
                                            if(status == false){
                                                Toast.makeText(MainActivity. this, "Login successful!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(MainActivity.this, SignedIn.class);
                                                intent.putExtra("type",role);
                                                intent.putExtra("emailAddress",userNameTemp);
                                                startActivity(intent);
                                            } else {
                                                if (snapshot.child(userNameTemp).child("suspension").hasChild("permanent") && snapshot.child(userNameTemp).child("suspension").child("permanent").getValue(boolean.class) == true){
                                                    Intent intent = new Intent(MainActivity.this, TutorInfo.class);
                                                    String suspensionType = "Permanent";
                                                    intent.putExtra("suspensionTime", suspensionType);
                                                    startActivity(intent);
                                                }

                                                else{
                                                    Date suspensionDate = new Date(snapshot.child(userNameTemp).child("suspension").child("date").child("year").getValue(int.class), snapshot.child(userNameTemp).child("suspension").child("date").child("month").getValue(int.class), snapshot.child(userNameTemp).child("suspension").child("date").child("day").getValue(int.class));
                                                    Calendar cal = Calendar.getInstance();
                                                    Date today = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));

                                                    if (suspensionDate.isGreaterThen(today)){
                                                        Intent intent = new Intent(MainActivity.this, TutorInfo.class);
                                                        String suspensionType = "You may log back in on: " + suspensionDate.toString();
                                                        intent.putExtra("suspensionTime", suspensionType);
                                                        startActivity(intent);
                                                    }


                                                    else{

                                                        databaseReference.child("Users").child(userNameTemp).child("suspension").child("isSuspended").setValue(false);
                                                        Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(MainActivity.this, SignedIn.class);
                                                        intent.putExtra("type",role);
                                                        intent.putExtra("emailAddress",userNameTemp);
                                                        startActivity(intent);
                                                    }





                                                }

                                            }

                                        }
                                    }

                                    //The system is dealing with a student
                                    else  {
                                        Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, SignedIn.class);
                                        intent.putExtra("type",role);
                                        intent.putExtra("emailAddress",userNameTemp);
                                        startActivity(intent);
                                    }

                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Wrong user name or password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Wrong user name or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else{
                    Toast.makeText(MainActivity.this,"User name or password cannot be empty.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Register Button
        //Go to register page starting from choosing role as a student or a tutor.
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GetTutorOrStudent.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
//test 