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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.fitnessenthusiasts.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

public class WorkoutDetails extends AppCompatActivity {

    //Variables
    LinearLayout woInfo;
    CardView cvWorkout;
    TextView seeMore,woDName,woDDiff,woDPoints;
    ImageView woDBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_workout_details);

        //Hooks
        woInfo = findViewById(R.id.woInfo);
        cvWorkout=findViewById(R.id.cvWorkout);
        cvWorkout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        seeMore=findViewById(R.id.viewMore);

        woDName = findViewById(R.id.woDName);
        woDPoints = findViewById(R.id.woDPoints);
        woDDiff = findViewById(R.id.woDDiff);
        woDBg = findViewById(R.id.woDBg);
        setUpData();

    }


    public void expandInfo(View view) {
        int v = (woInfo.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        int u = (seeMore.getVisibility() == View.VISIBLE)? View.GONE: View.VISIBLE;

        TransitionManager.beginDelayedTransition(cvWorkout, new AutoTransition());
        woInfo.setVisibility(v);
        seeMore.setVisibility(u);

    }

    private void setUpData(){
        String name = (String) getIntent().getExtras().get("name");
        String diff = (String) getIntent().getExtras().get("diff");
        String points = (String) getIntent().getExtras().get("points");
        String bg = (String) getIntent().getExtras().get("bg");

        woDName.setText(name);
        woDPoints.setText(points);
        woDDiff.setText(diff);

        Picasso.get()
                .load(bg)
                .into(woDBg);

    }
}