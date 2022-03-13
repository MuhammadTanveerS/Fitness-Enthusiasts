package com.example.fitnessenthusiasts.activities.LogReg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessenthusiasts.MainActivity;
import com.example.fitnessenthusiasts.R;

public class StartUpScreen extends AppCompatActivity {

    //Variables
    ImageView image;
    TextView slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up_screen);

        //Hooks
        image = findViewById(R.id.topImage);
        slogan = findViewById(R.id.slogan_name);

    }

    public void login(View view){
        Intent intent = new Intent(StartUpScreen.this, Login.class);

        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View,String>(image,"logo_image");
        pairs[1] = new Pair<View,String>(slogan,"logo_text");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartUpScreen.this,pairs);
        startActivity(intent,options.toBundle());
    }

    public void signup(View view){
        Intent intent = new Intent(StartUpScreen.this, SignUp.class);

        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View,String>(image,"logo_image");
        pairs[1] = new Pair<View,String>(slogan,"logo_text");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartUpScreen.this,pairs);
        startActivity(intent,options.toBundle());
    }
}