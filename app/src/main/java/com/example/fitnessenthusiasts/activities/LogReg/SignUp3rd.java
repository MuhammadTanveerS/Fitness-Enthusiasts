package com.example.fitnessenthusiasts.activities.LogReg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.HomeScreen;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class SignUp3rd extends AppCompatActivity {

    //Variables
    String fullName,username,email,password,gender,date,phoneNo;

    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber;
    FirebaseAuth auth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up3rd);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

        //Hooks
        countryCodePicker= findViewById(R.id.numberpicker);
        phoneNumber = findViewById(R.id.phone);

        fullName = getIntent().getStringExtra("Full Name");
        username = getIntent().getStringExtra("User Name");
        email = getIntent().getStringExtra("Email");
        password = getIntent().getStringExtra("Password");
        gender = getIntent().getStringExtra("gender");
        date = getIntent().getStringExtra("date");



    }

    public void Register(View view){
        phoneNo= "+"+countryCodePicker.getFullNumber()+ (phoneNumber.getEditText().getText().toString().trim());
//        storeUserDate();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    UserHelperClass addNewUser = new UserHelperClass(fullName,username,email,password,date,gender);
                    String id = task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(addNewUser);
                    Toast.makeText(SignUp3rd.this, "DATA SAVED", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUp3rd.this, HomeScreen.class));
                }
                else{
                    Toast.makeText(SignUp3rd.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}