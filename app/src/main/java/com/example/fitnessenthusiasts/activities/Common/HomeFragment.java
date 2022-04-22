package com.example.fitnessenthusiasts.activities.Common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.SPManager;
import com.example.fitnessenthusiasts.activities.Databases.Session;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.PostAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.PostModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    FirebaseDatabase database;
    FirebaseAuth auth;
    PostAdapter postAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        writePost = view.findViewById(R.id.writePost);
        avatar = view.findViewById(R.id.circular);
        LoadImage();

        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

        postRV = view.findViewById(R.id.postsRV);
        postModels = new ArrayList<>();


        postAdapter = new PostAdapter(postModels,getContext());
        postRV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        postRV.setHasFixedSize(true);
        postRV.setAdapter(postAdapter);

        fetchPosts();
        viewPost();


        // Inflate the layout for this fragment
        return view;
    }



    private void LoadImage(){
        Session session = new Session(view.getContext());

        Picasso.get()
                .load(session.getPhoto())
                .placeholder(R.drawable.placeholder_avatar)
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

    private void fetchPosts() {
        database.getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postModels.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    PostModel post = dataSnapshot.getValue(PostModel.class);
                    post.setPostId(dataSnapshot.getKey());
                    postModels.add(post);
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}