package com.example.tutron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Student extends AppCompatActivity {
    EditText firstName,lastName,emailAddress,password,address,cardNumber,expirationDate,cvvNumber;

    Button backBtn,createBtn;
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
                Intent backIntent = new Intent(Student.this, MainActivity.class);
                startActivity(backIntent);
                finish();
            }

        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                if(!TextUtils.isEmpty(firstNameTemp) && !TextUtils.isEmpty(lastNameTemp) && (!TextUtils.isEmpty(emailAddressTemp))
                        && !TextUtils.isEmpty(passwordTemp) && !TextUtils.isEmpty(addressTemp) && !TextUtils.isEmpty(cardNumberTemp) &&
                        !TextUtils.isEmpty(expirationDateTemp) && !TextUtils.isEmpty(cvvNumTemp)) {
                    //save data code needed
                    dataSaved = true;

                }else{
                    Toast.makeText(Student.this,"Please fill out everything",Toast.LENGTH_SHORT).show();
                }

                if(!TextUtils.isEmpty(cardNumberTemp) && !TextUtils.isEmpty(expirationDateTemp) && !TextUtils.isEmpty(cvvNumTemp) ){
                    for(int i= cardNumberTemp.length(); i>=0; i--){
                        if(!Character.isDigit(cardNumberTemp.charAt(i))){
                            isCardNumInt = true;
                        }
                        isCardNumInt = false;
                    }

                    for(int i= expirationDateTemp.length(); i>=0; i--){
                        if(!Character.isDigit(expirationDateTemp.charAt(i))){
                            isExpNumInt = true;
                        }
                        isExpNumInt = false;
                    }

                    for(int i= cvvNumTemp.length(); i>=0; i--){
                        if(!Character.isDigit(cvvNumTemp.charAt(i))){
                            isCvvNUmInt = true;
                        }
                        isCvvNUmInt = false;
                    }
                }



                if(dataSaved){
                    if(!isCardNumInt){
                        Toast.makeText(Student.this,"Please enter valid card numbers",Toast.LENGTH_SHORT).show();
                    }
                    if(!isExpNumInt){
                        Toast.makeText(Student.this,"Please enter valid expiration numbers",Toast.LENGTH_SHORT).show();
                    }
                    if(!isCvvNUmInt){
                        Toast.makeText(Student.this,"Please enter valid CVV numbers",Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(Student.this,"Register successful",Toast.LENGTH_SHORT).show();
                    Intent register = new Intent(Student.this, MainActivity.class);
                    startActivity(register);
                    finish();
                }

            }
        });
    }
}