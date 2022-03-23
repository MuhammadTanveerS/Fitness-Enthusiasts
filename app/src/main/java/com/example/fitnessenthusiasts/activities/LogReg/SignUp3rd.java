package com.example.fitnessenthusiasts.activities.LogReg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class SignUp3rd extends AppCompatActivity {

    //Variables
    String fullName,username,email,password,gender,date,phoneNo;

    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up3rd);

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
        storeUserDate();
    }

    public void storeUserDate(){
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com");
        DatabaseReference reference = rootNode.getReference("Users");

        UserHelperClass addNewUser = new UserHelperClass(fullName,username,email,phoneNo,password,date,gender);
        reference.child(username).setValue(addNewUser);
    }

}