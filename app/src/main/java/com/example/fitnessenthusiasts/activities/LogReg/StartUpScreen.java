package com.example.fitnessenthusiasts.activities.LogReg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessenthusiasts.MainActivity;
import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.Activities.PlanMainActivity;
import com.example.fitnessenthusiasts.activities.Common.HomeScreen;
import com.example.fitnessenthusiasts.activities.Databases.Session;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StartUpScreen extends AppCompatActivity {

    //Variables
//    ImageView image;
//    TextView slogan;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    FirebaseDatabase database;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_up_screen);



        //Hooks
//        image = findViewById(R.id.topImage);
//        slogan = findViewById(R.id.slogan_name);
        dialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Loggin In");
        dialog.setMessage("Please Wait ...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

    }

    public void login(View view){
        Intent intent = new Intent(getApplicationContext(), Login.class);

//        Pair[] pairs = new Pair[1];
//        pairs[0] = new Pair<View,String>(findViewById(R.id.btn_login),"transition_login");
        //pairs[1] = new Pair<View,String>(slogan,"logo_text");

        Bundle b = ActivityOptions.makeSceneTransitionAnimation(StartUpScreen.this).toBundle();
        startActivity(intent,b);
    }

    public void signup(View view){
        Intent intent = new Intent(getApplicationContext(), SignUp.class);

//        Pair[] pairs = new Pair[1];
//        pairs[0] = new Pair<View,String>(findViewById(R.id.btn_signup),"transition_signup");
////        pairs[1] = new Pair<View,String>(slogan,"logo_text");

        Bundle b = ActivityOptions.makeSceneTransitionAnimation(StartUpScreen.this).toBundle();
        startActivity(intent,b);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser!=null){
            dialog.show();
            database.getReference().child("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){

                        UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                        Session session = new Session(StartUpScreen.this);
                        Log.e("1",user.getFullName());
                        session.saveSession(user);

                        /*
                        USE
                        THIS
                        For
                        LATER
                         */
//                        if(user.getTrainer()){
//                            dialog.dismiss();
//                            Log.e("ss","it Exixst");
//                            startActivity(new Intent(StartUpScreen.this, PlanMainActivity.class));
//                        }else{
//                            dialog.dismiss();
//                            Log.e("gg","it dont Exixst");
//                            startActivity(new Intent(StartUpScreen.this, HomeScreen.class));
//                        }

                        dialog.dismiss();
                        startActivity(new Intent(StartUpScreen.this, HomeScreen.class));

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }



}