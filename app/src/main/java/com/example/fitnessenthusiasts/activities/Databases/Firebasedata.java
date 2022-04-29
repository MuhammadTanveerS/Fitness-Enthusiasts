package com.example.fitnessenthusiasts.activities.Databases;

import android.app.Application;

import com.example.fitnessenthusiasts.R;
import com.google.firebase.database.FirebaseDatabase;

public class Firebasedata extends Application {
    @Override
    public void onCreate() {
        FirebaseDatabase.getInstance(getString(R.string.db_instance)).setPersistenceEnabled(true);
        super.onCreate();
    }
}
