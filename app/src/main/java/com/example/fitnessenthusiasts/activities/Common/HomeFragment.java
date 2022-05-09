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

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.SPManager;
import com.example.fitnessenthusiasts.activities.Databases.Session;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.PostAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.FollowModel;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.PostModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    View view;
    CircleImageView avatar;
    ShimmerRecyclerView postRV;
    ArrayList<PostModel> postModels;
    RelativeLayout writePost;
    FirebaseDatabase database;
    FirebaseAuth auth;
    PostAdapter postAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        postRV = view.findViewById(R.id.postsRV);
        postRV.showShimmerAdapter();

        writePost = view.findViewById(R.id.writePost);
        avatar = view.findViewById(R.id.circular);
        LoadImage();

        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

        postModels = new ArrayList<>();
        postAdapter = new PostAdapter(postModels,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
//        linearLayoutManager.setReverseLayout(true);
        postRV.setLayoutManager(linearLayoutManager);
//        postRV.setHasFixedSize(true);

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
                    Collections.reverse(postModels);
                }
                postRV.setAdapter(postAdapter);
                postRV.hideShimmerAdapter();
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    //For TESTING
    //For TESTING
    //For TESTING
    private void fetchPosts2() {


        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        for(DataSnapshot dataSnapshot1:snapshot1.getChildren()){
                            FollowModel fModel = dataSnapshot1.getValue(FollowModel.class);
                            Log.e("Followed",fModel.getFollowed());

                            database.getReference().child("Posts").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    postModels.clear();
                                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                        PostModel post = dataSnapshot.getValue(PostModel.class);
                                        post.setPostId(dataSnapshot.getKey());

                                        if(post.getPostedBy().equals(fModel.getFollowed())){
                                            Log.e("GGGGG",fModel.getFollowed());
                                            postModels.add(post);
                                            Collections.reverse(postModels);
                                        }


                                    }
                                    postRV.setAdapter(postAdapter);
                                    postRV.hideShimmerAdapter();
                                    postAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}