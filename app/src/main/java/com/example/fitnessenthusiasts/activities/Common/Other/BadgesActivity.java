package com.example.fitnessenthusiasts.activities.Common.Other;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.Activities.Diary.Nutrition;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.BadgeAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.DiarySearchAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.BadgeModel;
import com.example.fitnessenthusiasts.databinding.ActivityBadgesBinding;
import com.example.fitnessenthusiasts.databinding.ActivityCommentsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BadgesActivity extends AppCompatActivity {

    ActivityBadgesBinding binding;
    BadgeAdapter adapter;
    ArrayList<BadgeModel> badgeModels;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityBadgesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

        badgeModels = new ArrayList<>();
        adapter = new BadgeAdapter(badgeModels,this);
        binding.badgesRV.setAdapter(adapter);

        viewBadges();
    }

    private void viewBadges() {
        database.getReference().child("Badges").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Continue Later
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void close(View view) {
        finish();
    }
}