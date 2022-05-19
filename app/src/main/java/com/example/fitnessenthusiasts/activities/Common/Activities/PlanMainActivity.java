package com.example.fitnessenthusiasts.activities.Common.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.databinding.ActivityPlanMainBinding;
import com.example.fitnessenthusiasts.databinding.ActivitySettingsBinding;

public class PlanMainActivity extends AppCompatActivity {
    ActivityPlanMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityPlanMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}