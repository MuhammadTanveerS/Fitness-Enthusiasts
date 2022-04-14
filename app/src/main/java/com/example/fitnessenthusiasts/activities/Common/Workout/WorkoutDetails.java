package com.example.fitnessenthusiasts.activities.Common.Workout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;

import android.animation.LayoutTransition;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.fitnessenthusiasts.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class WorkoutDetails extends AppCompatActivity {

    LinearLayout woInfo;
    CardView cvWorkout;
    TextView seeMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);

        woInfo = findViewById(R.id.woInfo);
        cvWorkout=findViewById(R.id.cvWorkout);
        cvWorkout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        seeMore=findViewById(R.id.viewMore);

    }

    public void expandInfo(View view) {
        int v = (woInfo.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        int u = (seeMore.getVisibility() == View.VISIBLE)? View.GONE: View.VISIBLE;

        TransitionManager.beginDelayedTransition(cvWorkout, new AutoTransition());
        woInfo.setVisibility(v);
        seeMore.setVisibility(u);

    }
}