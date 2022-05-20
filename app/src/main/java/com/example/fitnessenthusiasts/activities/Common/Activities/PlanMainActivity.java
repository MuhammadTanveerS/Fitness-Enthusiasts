package com.example.fitnessenthusiasts.activities.Common.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.databinding.ActivityPlanMainBinding;
import com.example.fitnessenthusiasts.databinding.ActivitySettingsBinding;
import com.squareup.picasso.Picasso;

public class PlanMainActivity extends AppCompatActivity {
    ActivityPlanMainBinding binding;
    Intent intent;
    String planName,planImage,planDetails,planDaysAndType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityPlanMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent = getIntent();
        planName = intent.getStringExtra("planName");
        planImage = intent.getStringExtra("planImage");
        planDetails = intent.getStringExtra("planDetails");
        planDaysAndType = intent.getStringExtra("planDaysAndType");
        setData();
    }

    private void setData() {
        binding.planName.setText(planName);
        binding.planDetails.setText(planDetails);
        binding.planDaysAndType.setText(planDaysAndType);
        Picasso.get()
                .load(planImage)
                .placeholder(R.drawable.image_placeholder)
                .into(binding.planImage);
    }

    public void finish(View view) {
        finish();
    }
}