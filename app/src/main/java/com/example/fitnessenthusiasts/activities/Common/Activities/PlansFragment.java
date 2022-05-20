package com.example.fitnessenthusiasts.activities.Common.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.PlansAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.PlansModel;
import com.example.fitnessenthusiasts.databinding.FragmentCommunityLeaderboardBinding;
import com.example.fitnessenthusiasts.databinding.FragmentPlansBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PlansFragment extends Fragment {
    FragmentPlansBinding binding;
    PlansAdapter adapter;
    ArrayList<PlansModel> plansModels;
    FirebaseDatabase database;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlansBinding.inflate(inflater,container, false);

        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

        plansModels = new ArrayList<>();
        adapter = new PlansAdapter(getContext(),plansModels);
        binding.plansRV.setAdapter(adapter);
        loadData();


        return binding.getRoot();
    }

    private void loadData() {
        database.getReference().child("Plans")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        plansModels.clear();
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                            PlansModel model = dataSnapshot.getValue(PlansModel.class);
                            plansModels.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    //USE THIS TO ADD NEW PLANS
    private void addPlan() {

        String details = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam efficitur ipsum in placerat molestie." +
                "  Fusce quis mauris a enim sollicitudin\\n\\nLorem ipsum dolor sit" +
                " amet, consectetur adipiscing elit. Etiam efficitur ipsum in placerat molestie. " +
                " Fusce quis mauris a enim sollicitudin\"";

        String image = "https://firebasestorage.googleapis.com/v0/b/fitness-enthusiasts.appspot.com/o/plans%2Feat_green_diet.jpg?alt=media&token=d72326d8-4fbe-4309-b2d0-324419e2185c";

        PlansModel model = new PlansModel("Eat Green",details,image,"Daily",14);
        database.getReference().child("Plans").child("Plan 3").setValue(model);
    }

}