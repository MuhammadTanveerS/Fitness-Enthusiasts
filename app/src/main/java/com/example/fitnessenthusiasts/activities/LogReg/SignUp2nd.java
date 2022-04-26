package com.example.fitnessenthusiasts.activities.LogReg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessenthusiasts.R;

import java.util.Calendar;

public class SignUp2nd extends AppCompatActivity {

    //Variables
    ImageView backBtn;
    Button next;
    TextView titleText;

    //Data Variables
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up2nd);

        //Hooks
        backBtn = findViewById(R.id.notif_back_button);
        next = findViewById(R.id.signup_next_button);
        titleText = findViewById(R.id.title_text);
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);

    }

    public void callNextSignupScreen(View view) {

        if(!validateGender() | !validateAge()){
            return;
        }

        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
        String _gender = selectedGender.getText().toString();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String _date = day+"/"+month+"/"+year;
        String fullName,username,email,password;

        fullName = getIntent().getStringExtra("Full Name");
        username = getIntent().getStringExtra("User Name");
        email = getIntent().getStringExtra("Email");
        password = getIntent().getStringExtra("Password");


        Intent intent = new Intent(getApplicationContext(), SignUp3rd.class);

        //Send data to next activity
        intent.putExtra("gender",_gender);
        intent.putExtra("date",_date);

        intent.putExtra("Full Name",fullName);
        intent.putExtra("User Name",username);
        intent.putExtra("Email",email);
        intent.putExtra("Password",password);


        //Add Transition
//        Pair[] pairs = new Pair[3];
//
//        pairs[0] = new Pair<View, String>(backBtn, "backarrow_tran");
//        pairs[1] = new Pair<View, String>(next, "next_tran");
//        pairs[2] = new Pair<View, String>(titleText, "title_tran");

//        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2nd.this, pairs);
        Bundle b = ActivityOptions.makeSceneTransitionAnimation(SignUp2nd.this).toBundle();
        startActivity(intent, b);

    }

    //Validating functions

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 12) {
            Toast.makeText(this, "You are not Eligible to Apply", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


}