package com.example.fitnessenthusiasts.activities.LogReg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessenthusiasts.R;

public class SignUp2nd extends AppCompatActivity {

    //Variables
    ImageView backBtn;
    Button next;
    TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up2nd);

        //Hooks
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_button);
        titleText = findViewById(R.id.title_text);
    }

    public void callNextSignupScreen(View view) {

        Intent intent = new Intent(getApplicationContext(), SignUp3rd.class);

        //Add Transition
        Pair[] pairs = new Pair[3];

        pairs[0] = new Pair<View, String>(backBtn, "backarrow_tran");
        pairs[1] = new Pair<View, String>(next, "next_tran");
        pairs[2] = new Pair<View, String>(titleText, "title_tran");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2nd.this, pairs);
        startActivity(intent, options.toBundle());

    }

}