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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    //Variables
    ImageView image;
    TextView logoText,sloganText;
    TextInputLayout username,password;
    Button login_btn,signup_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(com.example.fitnessenthusiasts.R.layout.activity_login);

        //hooks
        image = findViewById(R.id.topImage);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.signup_uname);
        password = findViewById(R.id.signup_password);
        login_btn = findViewById(R.id.Login_btn);
        signup_Btn = findViewById(R.id.Signup_btn);

    }

    public void signUp(View view){
        Intent intent = new Intent(Login.this,SignUp.class);

        Pair[] pairs = new Pair[7];

        pairs[0] = new Pair<View,String>(image,"logo_image");
        pairs[1] = new Pair<View,String>(logoText,"logo_text");
        pairs[2] = new Pair<View,String>(sloganText,"logo_desc");
        pairs[3] = new Pair<View,String>(username,"user_tran");
        pairs[4] = new Pair<View,String>(password,"pass_tran");
        pairs[5] = new Pair<View,String>(login_btn,"button_tran");
        pairs[6] = new Pair<View,String>(signup_Btn,"login_signup_tran");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
        startActivity(intent,options.toBundle());

    }

    //Testing a code
    public void check(View view){
        rootNode = FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com");
        reference = rootNode.getReference("users");
        reference.setValue("Hastalavista");

//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }

}