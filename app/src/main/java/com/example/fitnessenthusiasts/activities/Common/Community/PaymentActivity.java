package com.example.fitnessenthusiasts.activities.Common.Community;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.CommunitySubscriberModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class PaymentActivity extends AppCompatActivity {
    Intent intent;
    FirebaseDatabase database;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

        intent = getIntent();
        key =intent.getStringExtra("comKey");
    }

    public void sub(View view) {

        String  userId = FirebaseAuth.getInstance().getUid();
//        String  userId = "B4JP3pkpiQYlvtzi3m6WVSOXtFN2";

        Date now = new Date();
        DateFormat Date = DateFormat.getDateInstance();
        String subDateStart = Date.format(now);

        Calendar c= Calendar.getInstance();
        c.add(Calendar.DATE, 30);
        Date d=c.getTime();
        String subDateExp = Date.format(d);

        CommunitySubscriberModel sub = new CommunitySubscriberModel(userId,subDateStart,subDateExp);
        sub.setCommunityKey(key);

        database.getReference().child("Communities").child(key)
        .child("members").child(userId).setValue(sub);

    }


}