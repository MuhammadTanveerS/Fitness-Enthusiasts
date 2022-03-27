package com.example.fitnessenthusiasts.activities.Common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.CategoriesGet;
import com.example.fitnessenthusiasts.activities.HelperClasses.CategoryModel;
import com.example.fitnessenthusiasts.activities.HelperClasses.CategoryRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;


public class CommunityFragment extends Fragment {

    ArrayList<CategoryModel> categoryModels;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;

    ProgressBar progressBar;

    int[] categoryImages = {R.drawable.fitness_med, R.drawable.fitness_bike,R.drawable.fitness_yoga
    ,R.drawable.fitness_stats,R.drawable.fitness_hike,R.drawable.fitness_runner};
    int[] categoryBgImages = {R.drawable.category_bg,R.drawable.category_bg,R.drawable.category_bg,
            R.drawable.category_bg,R.drawable.category_bg,R.drawable.category_bg};
    int c=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_community, container, false);



        //Testing Fragment
//        TextView textView = view.findViewById(R.id.txt);
//        Button button = view.findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                textView.setText("HHHHHHHH");
//            }
//        });



        /*
        Recycler
        View
         */
        recyclerView = view.findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        categoryModels = new ArrayList<>();
        setUpCommunityModels();
        categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(view.getContext(),categoryModels);
        recyclerView.setAdapter(categoryRecyclerViewAdapter);

        progressBar = view.findViewById(R.id.progressBar);




        // Inflate the layout for this fragment
        return view;

    }

    private void setUpCommunityModels(){

        ArrayList<String> catNames = new ArrayList<>();

        //DB
        databaseReference = FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com").getReference("Communities");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    catNames.clear();
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {
                        CategoriesGet i =dataSnapshot1.getValue(CategoriesGet.class);
                        String t = i.getCatName();
                        categoryModels.add(new CategoryModel(t,categoryImages[i.getCatBgImage()],categoryBgImages[i.getCatImage()]));
                        progressBar.setVisibility(View.GONE);
                    }
                    categoryRecyclerViewAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        String[] comNames = {"Comm1","Hello","Comm22"};
        for(int i=0;i<comNames.length;i++){
//            categoryModels.add(new CategoryModel(comNames[i],categoryImages[i],categoryBgImages[i]));
            //categoryModels.add(new CategoryModel(catNames.get(i),categoryImages[i],categoryBgImages[i]));

        }
    }

}