package com.example.fitnessenthusiasts.activities.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.SPManager;

import java.util.HashMap;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //Testing Session
        TextView textView = findViewById(R.id.testTV);

        SPManager spManager = new SPManager(this);
        HashMap<String,String > userDetails = spManager.getUserDetails();

        String fullName = userDetails.get(SPManager.S_FULLNAME);
        String email = userDetails.get(SPManager.S_EMAIL);
        String gender = userDetails.get(SPManager.S_GENDER);

        textView.setText(fullName + "\n" +email+"\n"+gender);

    }
}