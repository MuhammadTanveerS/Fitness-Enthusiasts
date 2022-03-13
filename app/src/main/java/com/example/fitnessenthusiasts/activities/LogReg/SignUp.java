package com.example.fitnessenthusiasts.activities.LogReg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessenthusiasts.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    //Continue Later not Working now
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    //Variables
    ImageView backBtn;
    Button next, login;
    TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(com.example.fitnessenthusiasts.R.layout.activity_sign_up);

        //Hooks
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.title_text);

    }

    public void check(View view) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("test");
        reference.setValue("Hi");
    }

    public void callNextSignupScreen(View view) {

        Intent intent = new Intent(getApplicationContext(), SignUp2nd.class);

        //Add Transition
        Pair[] pairs = new Pair[4];

        pairs[0] = new Pair<View, String>(backBtn, "backarrow_tran");
        pairs[1] = new Pair<View, String>(next, "next_tran");
        pairs[2] = new Pair<View, String>(login, "login_signup_tran");
        pairs[3] = new Pair<View, String>(titleText, "title_tran");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
        startActivity(intent, options.toBundle());

    }
}