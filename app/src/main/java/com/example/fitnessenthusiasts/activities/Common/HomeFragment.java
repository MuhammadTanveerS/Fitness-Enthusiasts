package com.example.fitnessenthusiasts.activities.Common;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.SPManager;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.PostAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.PostModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    View view;
    CircleImageView avatar;
    RecyclerView postRV;
    ArrayList<PostModel> postModels;
    RelativeLayout writePost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        writePost = view.findViewById(R.id.writePost);
        avatar = view.findViewById(R.id.circular);
        LoadImage();

        postRV = view.findViewById(R.id.postsRV);
        postModels = new ArrayList<>();


        postModels.add(new PostModel(R.drawable.avatar_female,R.drawable.testphoto1,
                "Joe March","Checking stats!", "15","5"));
        postModels.add(new PostModel(R.drawable.avatar_male,R.drawable.chest_b,
                "Chris Walker","Doing chest workouts.", "52","22"));
        postModels.add(new PostModel(R.drawable.avatar_male2,R.drawable.testphoto2,
                "Tim Drake","About to hit the GYM!!!", "5","2"));
        postModels.add(new PostModel(R.drawable.avatar_female2,R.drawable.testphoto3,
                "Elise Velcoro","Just Working Out!", "56","35"));


        PostAdapter postAdapter = new PostAdapter(postModels,getContext());
        postRV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        postRV.setHasFixedSize(true);
        postRV.setAdapter(postAdapter);

        viewPost();


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

    private void viewPost(){
        writePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fragment_in,R.anim.fragment_out);
                fragmentTransaction.replace(R.id.frame_layout,new PostFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

}