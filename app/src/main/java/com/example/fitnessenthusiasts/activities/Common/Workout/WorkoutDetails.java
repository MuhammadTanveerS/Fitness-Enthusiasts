package com.example.fitnessenthusiasts.activities.Common.Workout;

import static android.icu.lang.UCharacter.toUpperCase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.LayoutTransition;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.Exercises;
import com.example.fitnessenthusiasts.activities.HelperClasses.ExercisesModel;
import com.example.fitnessenthusiasts.activities.HelperClasses.ExercisesRecyclerViewAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.WorkoutsModel;
import com.example.fitnessenthusiasts.activities.HelperClasses.WorkoutsRecyclerViewAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WorkoutDetails extends AppCompatActivity {

    //Variables
    LinearLayout woInfo;
    CardView cvWorkout;
    TextView seeMore,woDName,woDDiff,woDPoints;
    ImageView woDBg;
    CollapsingToolbarLayout collapsingToolbarLayout;
    DatabaseReference databaseReference;
    ArrayList<ExercisesModel> exercisesModel;
    ExercisesRecyclerViewAdapter exercisesRecyclerViewAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_workout_details);

        //Hooks
        woInfo = findViewById(R.id.woInfo);
        cvWorkout=findViewById(R.id.cvWorkout);
//        cvWorkout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        seeMore=findViewById(R.id.viewMore);

        woDName = findViewById(R.id.woDName);
        woDPoints = findViewById(R.id.woDPoints);
        woDDiff = findViewById(R.id.woDDiff);
        woDBg = findViewById(R.id.woDBg);
        collapsingToolbarLayout = findViewById(R.id.collapsing_detail);
        setUpData();

        recyclerView = findViewById(R.id.exRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //Check

        exercisesModel= new ArrayList<>();
        loadExercises();
        exercisesRecyclerViewAdapter = new ExercisesRecyclerViewAdapter(this,exercisesModel);
        recyclerView.setAdapter(exercisesRecyclerViewAdapter);


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
        collapsingToolbarLayout.setTitle(name.toUpperCase());

        Picasso.get()
                .load(bg)
                .into(woDBg);

    }

    public void goBack(View view) {
        finish();
    }


    private void loadExercises(){
        String workOutName = (String) getIntent().getExtras().get("key");
        //DB
        databaseReference = FirebaseDatabase.getInstance(getString(R.string.db_instance)).getReference("Workouts").child(workOutName).child("Exercises");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    for(DataSnapshot snapshot1:snapshot.getChildren()){

                        ExercisesModel data = snapshot1.getValue(ExercisesModel.class);
                        exercisesModel.add(data);
//                        Log.e("Value",data.getTime());

                    }
                    exercisesRecyclerViewAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}