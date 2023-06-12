package com.example.tutron;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Student extends AppCompatActivity {
    EditText userName,firstName,lastName,emailAddress,password,address,cardNumber,expirationDate,cvvNumber;
    FirebaseDatabase database;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://seg-2105-group-project-f5fd7-default-rtdb.firebaseio.com/");

    Button backBtn,createBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        userName = findViewById(R.id.userStudentName);
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
                String userNameTemp = userName.getText().toString().trim();
                String firstNameTemp = firstName.getText().toString().trim();
                String lastNameTemp = lastName.getText().toString().trim();
                String emailAddressTemp = emailAddress.getText().toString().trim();
                String passwordTemp = password.getText().toString().trim();
                String addressTemp = address.getText().toString().trim();

                String cardNumberTemp = cardNumber.getText().toString().trim();
                String expirationDateTemp = expirationDate.getText().toString().trim();
                String cvvNumTemp = cardNumber.getText().toString().trim();


                Boolean dataSaved = false;
                Boolean isCardNumInt = false;
                Boolean isExpNumInt = false;
                Boolean isCvvNUmInt = false;

                if (!TextUtils.isEmpty(firstNameTemp) && !TextUtils.isEmpty(lastNameTemp) && (!TextUtils.isEmpty(emailAddressTemp))
                        && !TextUtils.isEmpty(passwordTemp) && !TextUtils.isEmpty(addressTemp) && !TextUtils.isEmpty(cardNumberTemp) &&
                        !TextUtils.isEmpty(expirationDateTemp) && !TextUtils.isEmpty(cvvNumTemp) &&
                        !TextUtils.isEmpty(userNameTemp)) {
                    dataSaved = true;

                } else {
                    Toast.makeText(Student.this, "Please fill out everything", Toast.LENGTH_SHORT).show();
                }

                if (!TextUtils.isEmpty(cardNumberTemp) && !TextUtils.isEmpty(expirationDateTemp) && !TextUtils.isEmpty(cvvNumTemp)) {
                    for (int i = cardNumberTemp.length(); i >= 0; i--) {
                        if (!Character.isDigit(cardNumberTemp.charAt(i))) {
                            isCardNumInt = true;
                        }
                        isCardNumInt = false;
                    }

                    for (int i = expirationDateTemp.length(); i >= 0; i--) {
                        if (!Character.isDigit(expirationDateTemp.charAt(i))) {
                            isExpNumInt = true;
                        }
                        isExpNumInt = false;
                    }

                    for (int i = cvvNumTemp.length(); i >= 0; i--) {
                        if (!Character.isDigit(cvvNumTemp.charAt(i))) {
                            isCvvNUmInt = true;
                        }
                        isCvvNUmInt = false;
                    }
                }


                if (dataSaved) {
                    if(!isCardNumInt){
                    Toast.makeText(Student.this,"Please enter valid card numbers",Toast.LENGTH_SHORT).show();
                    }
                    if(!isExpNumInt){
                    Toast.makeText(Student.this,"Please enter valid expiration numbers",Toast.LENGTH_SHORT).show();
                    }
                    if(!isCvvNUmInt) {
                        Toast.makeText(Student.this, "Please enter valid CVV numbers", Toast.LENGTH_SHORT).show();
                    }
                    databaseReference.child("users").child(userNameTemp).child("first name").setValue(firstNameTemp);
                    databaseReference.child("users").child(userNameTemp).child("last name").setValue(lastNameTemp);
                    databaseReference.child("users").child(userNameTemp).child("email").setValue(emailAddressTemp);
                    databaseReference.child("users").child(userNameTemp).child("password").setValue(passwordTemp);
                    databaseReference.child("users").child(userNameTemp).child("address").setValue(addressTemp);
                    databaseReference.child("users").child(userNameTemp).child("Credit Card Number").setValue(cardNumberTemp);
                    databaseReference.child("users").child(userNameTemp).child("Expiration Date").setValue(expirationDateTemp);
                    databaseReference.child("users").child(userNameTemp).child("cvv Number").setValue(cvvNumTemp);


                     Toast.makeText(Student.this, "Register successful", Toast.LENGTH_SHORT).show();
                     Intent register = new Intent(Student.this, MainActivity.class);
                     startActivity(register);
                     finish();
                }
            }
        });
    }
}