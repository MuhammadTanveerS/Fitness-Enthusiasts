package com.example.fitnessenthusiasts.activities.LogReg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
//Continue Later not Working now
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.fitnessenthusiasts.R.layout.activity_sign_up);
    }

    public void check(View view){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("test");
        reference.setValue("Hi");



    }
}