package com.example.fitnessenthusiasts.activities.Common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workouts, container, false);

        recyclerView = view.findViewById(R.id.workoutRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        workoutsModel = new ArrayList<>();
        setUpWorkoutModels();
        workoutsRecyclerViewAdapter = new WorkoutsRecyclerViewAdapter(view.getContext(),workoutsModel);
        recyclerView.setAdapter(workoutsRecyclerViewAdapter);

        progressBar = view.findViewById(R.id.progressBar);

        return view;
    }

    private void setUpWorkoutModels(){
        //DB
        databaseReference = FirebaseDatabase.getInstance(getString(R.string.db_instance)).getReference("Workouts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    for(DataSnapshot snapshot1:snapshot.getChildren()){

                        WorkoutsModel data = snapshot1.getValue(WorkoutsModel.class);
                        data.setKey(snapshot1.getKey());
                        workoutsModel.add(data);
//                        Log.e("ERRROO",snapshot1.getKey());
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
}