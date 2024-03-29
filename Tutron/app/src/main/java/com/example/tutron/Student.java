package com.example.tutron;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Student extends AppCompatActivity {
    EditText firstName,lastName,emailAddress,password,address,cardNumber,expirationDate,cvvNumber;
    FirebaseDatabase database;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://seg-2105-group-project-f5fd7-default-rtdb.firebaseio.com/");

    Button createBtn;
    ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        emailAddress = findViewById(R.id.emailAddress);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);

        cardNumber = findViewById(R.id.cardNumber);
        expirationDate = findViewById(R.id.expDate);
        cvvNumber = findViewById(R.id.cvv);


        backBtn = findViewById(R.id.studentBackButton);
        createBtn = findViewById(R.id.studentCreateButton);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(Student.this, GetTutorOrStudent.class);
                startActivity(backIntent);
                finish();
            }

        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameTemp = firstName.getText().toString().trim();
                String lastNameTemp = lastName.getText().toString().trim();
                //Replacing "." with "," since we are using the email as a key
                String emailAddressTemp = emailAddress.getText().toString().trim().replace('.', ',');
                String emailAddressTemp2 = emailAddress.getText().toString().trim();
                String passwordTemp = password.getText().toString().trim();
                String addressTemp = address.getText().toString().trim();
                String cardNumberTemp = cardNumber.getText().toString().trim();
                String expirationDateTemp = expirationDate.getText().toString().trim();
                String cvvNumTemp = cvvNumber.getText().toString().trim();


                Boolean dataSaved = false;
                Boolean isCardNumInt = false;
                Boolean isExpNumInt = false;
                Boolean isCvvNUmInt = false;

                if (!TextUtils.isEmpty(firstNameTemp) && !TextUtils.isEmpty(lastNameTemp) && (!TextUtils.isEmpty(emailAddressTemp))
                        && !TextUtils.isEmpty(passwordTemp) && !TextUtils.isEmpty(addressTemp) && !TextUtils.isEmpty(cardNumberTemp) &&
                        !TextUtils.isEmpty(expirationDateTemp) && !TextUtils.isEmpty(cvvNumTemp)) {
                    dataSaved = true;

                } else {
                    Toast.makeText(Student.this, "Please fill out everything", Toast.LENGTH_SHORT).show();
                }
                //FLAG HERE FOR DELETED CODE



                if (dataSaved) {
                    //Check if the username has already exists in the database
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(emailAddressTemp)){
                                Toast.makeText(Student.this, "This email has already existed! Please try again", Toast.LENGTH_SHORT).show();
                            }else{


                                //FLAG HERE FOR DELETED CODE

                                //Current implementation of data into database
                                databaseReference.child("Users").child(emailAddressTemp).child("firstName").setValue(firstNameTemp);
                                databaseReference.child("Users").child(emailAddressTemp).child("lastName").setValue(lastNameTemp);
                                databaseReference.child("Users").child(emailAddressTemp).child("emailAddress").setValue(emailAddressTemp2);
                                databaseReference.child("Users").child(emailAddressTemp).child("password").setValue(passwordTemp);
                                databaseReference.child("Users").child(emailAddressTemp).child("address").setValue(addressTemp);
                                databaseReference.child("Users").child(emailAddressTemp).child("Credit_Card_Number").setValue(cardNumberTemp);
                                databaseReference.child("Users").child(emailAddressTemp).child("Expiration_Date").setValue(expirationDateTemp);
                                databaseReference.child("Users").child(emailAddressTemp).child("cvv_Number").setValue(cvvNumTemp);
                                databaseReference.child("Users").child(emailAddressTemp).child("type").setValue("Student");

                                //Possible new implementation of data into database
                                //String Users = databaseReference.push().getKey();
                                //StudentAccount student = new StudentAccount(firstNameTemp, lastNameTemp, emailAddressTemp, passwordTemp,
                                //addressTemp, cardNumberTemp, expirationDateTemp, cvvNumTemp);
                                //databaseReference.child(Users).setValue(student);

                                Toast.makeText(Student.this, "Register successful", Toast.LENGTH_SHORT).show();
                                Intent register = new Intent(Student.this, MainActivity.class);
                                startActivity(register);
                                finish();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }
}