package com.example.fitnessenthusiasts.activities.LogReg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.HomeScreen;
import com.example.fitnessenthusiasts.activities.Databases.Session;
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
    TextView weightTV,heightTV;
    NumberPicker wt,wtd,ht,htd;
    int WEIGHT,WEIGHTDec,HEIGHT,HEIGHTDec;
    double heightInch;

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

        fullName = getIntent().getStringExtra("Full Name");
        username = getIntent().getStringExtra("User Name");
        email = getIntent().getStringExtra("Email");
        password = getIntent().getStringExtra("Password");
        gender = getIntent().getStringExtra("gender");
        date = getIntent().getStringExtra("date");

        WEIGHT = 0;
        WEIGHTDec =0;
        HEIGHT =0;
        HEIGHTDec=0;

        wt = findViewById(R.id.weight);
        wtd = findViewById(R.id.weightDecimal);
        ht = findViewById(R.id.height);
        htd = findViewById(R.id.heightDecimal);
        weightTV = findViewById(R.id.weightTV);
        heightTV= findViewById(R.id.heightTV);

        wt.setMinValue(5);
        wt.setMaxValue(400);
        wt.setValue(30);
        wtd.setMinValue(0);
        wtd.setMaxValue(9);
        wtd.setValue(0);

        wt.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                WEIGHT=i1;
                weightTV.setText(WEIGHT+"."+WEIGHTDec+" KG");
            }
        });

        wtd.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                WEIGHTDec=i1;
                weightTV.setText(WEIGHT+"."+WEIGHTDec+" KG");

                //String w = WEIGHT+"."+WEIGHTDec;
                //Float winf = Float.parseFloat(w);
                //Log.e("IN FLOAT",winf+"");
            }
        });

        ht.setMinValue(2);
        ht.setMaxValue(10);
        ht.setValue(5);
        htd.setMinValue(0);
        htd.setMaxValue(11);
        htd.setValue(0);

        ht.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                HEIGHT=i1;
                heightTV.setText(HEIGHT+"'"+HEIGHTDec+" Ft");
            }
        });

        htd.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                HEIGHTDec=i1;
                heightTV.setText(HEIGHT+"'"+HEIGHTDec+" Ft");

//                double inch =HEIGHT*12;
//                inch+=HEIGHTDec;
//                Log.e("in Inches",inch+"");
            }
        });


    }

    public void Register(View view){

        heightInch =HEIGHT*12;
        heightInch+=HEIGHTDec;
        String weightstr = WEIGHT+"."+WEIGHTDec;
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.placeholder_avatar);
        Log.e("uri",uri+"");

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    UserHelperClass addNewUser = new UserHelperClass(fullName,username,email,password,date,gender);
                    addNewUser.setHeight(heightInch+"");
                    addNewUser.setWeight(weightstr);
                    addNewUser.setProfilePhoto(uri.toString());
                    String id = task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(addNewUser);

                    Session session = new Session(SignUp3rd.this);
                    session.saveSession(addNewUser);

                    Toast.makeText(SignUp3rd.this, "User Registered", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUp3rd.this, HomeScreen.class));
                }
                else{
                    Toast.makeText(SignUp3rd.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}