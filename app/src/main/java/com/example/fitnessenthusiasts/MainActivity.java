package com.example.fitnessenthusiasts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessenthusiasts.activities.testMainActivity;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 5000;

    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.botton_animation);

        //Hooks
        image = findViewById(R.id.imageView3);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, testMainActivity.class);
                startActivity(intent);
                finish();

            }
        }, SPLASH_TIME);

    }
}