package com.example.fitnessenthusiasts.activities.Common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.WorkoutsModel;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.WorkoutsRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class WorkoutsFragment extends Fragment {

    ArrayList<WorkoutsModel> workoutsModel;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    WorkoutsRecyclerViewAdapter workoutsRecyclerViewAdapter;
    ProgressBar progressBar;
    String workoutType;
    CardView cardioCV, fullBodyCV, yogaCV, armsCV, chestCV, legsCV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workouts, container, false);

        workoutType = "all";

        recyclerView = view.findViewById(R.id.workoutRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        workoutsModel = new ArrayList<>();
        setUpWorkoutModels();
        workoutsRecyclerViewAdapter = new WorkoutsRecyclerViewAdapter(view.getContext(),workoutsModel);
        recyclerView.setAdapter(workoutsRecyclerViewAdapter);

        progressBar = view.findViewById(R.id.progressBar);

        cardioCV = view.findViewById(R.id.cardioCV);
        fullBodyCV = view.findViewById(R.id.fullBodyCV);
        yogaCV = view.findViewById(R.id.yogaCV);
        armsCV = view.findViewById(R.id.armsCV);
        chestCV = view.findViewById(R.id.chestCV);
        legsCV = view.findViewById(R.id.legsCV);

        cardioCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTypeWorkout("Cardio");
            }
        });

        fullBodyCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTypeWorkout("Full Body");
            }
        });

        yogaCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTypeWorkout("Yoga");
            }
        });

        armsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTypeWorkout("Arms");
            }
        });

        chestCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTypeWorkout("Chest");
            }
        });

        legsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTypeWorkout("Legs");
            }
        });

        return view;
    }

    private void setUpWorkoutModels(){
        //DB
        databaseReference = FirebaseDatabase.getInstance(getString(R.string.db_instance)).getReference("Workouts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workoutsModel.clear();
                if(snapshot.exists()){

                    for(DataSnapshot snapshot1:snapshot.getChildren()){

                        WorkoutsModel data = snapshot1.getValue(WorkoutsModel.class);
                        data.setKey(snapshot1.getKey());
                        workoutsModel.add(data);
//                        Log.e("RRROO",data.getType());
                        progressBar.setVisibility(View.GONE);

                    }
                    workoutsRecyclerViewAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setTypeWorkout(String t){
        workoutType = t;

        databaseReference = FirebaseDatabase.getInstance(getString(R.string.db_instance)).getReference("Workouts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workoutsModel.clear();
                if(snapshot.exists()){
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        WorkoutsModel data = snapshot1.getValue(WorkoutsModel.class);
                        if(data.getType().equals(workoutType)){
                            data.setKey(snapshot1.getKey());
                            workoutsModel.add(data);
                        }
                    }
                    workoutsRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}