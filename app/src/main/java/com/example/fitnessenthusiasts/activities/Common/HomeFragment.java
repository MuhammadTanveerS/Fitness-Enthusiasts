package com.example.fitnessenthusiasts.activities.Common;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.SPManager;
import com.example.fitnessenthusiasts.activities.HelperClasses.PostAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.PostModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    View view;
    CircleImageView avatar;
    RecyclerView postRV;
    ArrayList<PostModel> postModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        avatar = view.findViewById(R.id.circular);
        LoadImage();

        postRV = view.findViewById(R.id.postsRV);
        postModels = new ArrayList<>();


//        postModels.add(new PostModel(R.drawable.avatar_female,R.drawable.fitness_stats,
//                "Marilyn Monroe","Checking stats!", "15","5"));
//        postModels.add(new PostModel(R.drawable.avatar_male,R.drawable.chest_b,
//                "Sean Connery","Doing chest workouts.", "52","22"));
//        postModels.add(new PostModel(R.drawable.avatar_male,R.drawable.ic_workingout,
//                "Clark Gable","Just Working Out!", "5","2"));


        PostAdapter postAdapter = new PostAdapter(postModels,getContext());
        postRV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        postRV.setHasFixedSize(true);
        postRV.setAdapter(postAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    private void LoadImage(){
        SPManager spManager = new SPManager(view.getContext());
        HashMap<String, String> userDetails = spManager.getUserDetails();
        String image = userDetails.get(SPManager.S_IMAGE);

        Picasso.get()
                .load(image)
                .into(avatar);
    }

}