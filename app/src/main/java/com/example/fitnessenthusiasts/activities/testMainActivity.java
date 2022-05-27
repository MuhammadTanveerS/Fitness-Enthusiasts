package com.example.fitnessenthusiasts.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.CommunityInfoModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class testMainActivity extends AppCompatActivity {
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(com.example.fitnessenthusiasts.R.layout.activity_test_main);

        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));
//        seedData();
    }

    private void addTrainer(){
//        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
//                .child("trainer").setValue(true);

        database.getReference().child("Trainers").child(FirebaseAuth.getInstance().getUid()).setValue("");
    }

    private void seedData(){
        String type ="For Hypertension and diabetes Patients";
        String age = "23+";
        String description1 ="\nBelly fat burning HIIT alternates intense exercises with less intense recovery periods. \n\nCombine abs workout with HIIT, you can melt away excess fat fast and make your six pack visible easily.\n";

        String description2="Duis tellus metus, elementum a lectus id, aliquet interdum mauris. Nam bibendum efficitur sollicitudin. Proin eleifend libero velit, nec fringilla dolor finibus quis. nMorbi eu libero pellentesque, rutrum metus quis, blandit est. Fusce bibendum accumsan nisi vulputate feugiat. In fermentum laoreet euismod. Praesent sem nisl, facilisis eget odio at, rhoncus scelerisque ipsum. Nulla orci dui, dignissim a risus ut, lobortis porttitor velit.\n" +
                "        \n\nNulla id lectus metus. Maecenas a lorem in odio auctor facilisis non vitae nunc. Sed malesuada volutpat massa. Praesent sit amet lacinia augue, mollis tempor dolor.";
        String image ="https://firebasestorage.googleapis.com/v0/b/fitness-enthusiasts.appspot.com/o/communities%2Fpatients-workout.jpg?alt=media&token=c7fa6c93-5f9c-48b2-b049-94823e8c5e52";
        double price = 5.99;


        CommunityInfoModel model = new CommunityInfoModel(FirebaseAuth.getInstance().getUid(),type,age,description1,description2,image,price);
        database.getReference().child("Communities").child("Community 3").child("info").setValue(model);
    }
}