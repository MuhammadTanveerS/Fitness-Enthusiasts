package com.example.fitnessenthusiasts.activities.Common.Community;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.LeaderboardAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.CommunitySubscriberModel;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.LeaderboardModel;
import com.example.fitnessenthusiasts.databinding.FragmentActivityBinding;
import com.example.fitnessenthusiasts.databinding.FragmentCommunityBinding;
import com.example.fitnessenthusiasts.databinding.FragmentCommunityLeaderboardBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class CommunityLeaderboardFragment extends Fragment {

    FragmentCommunityLeaderboardBinding binding;
    ArrayList<LeaderboardModel> leaderboardModels, top3;
    LeaderboardAdapter adapter;
    FirebaseDatabase database;
    String key;
    Boolean isTrainer;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCommunityLeaderboardBinding.inflate(inflater,container, false);

        key=((CommunityMainActivity)getActivity()).key;
        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));
        top3 = new ArrayList<>();

        leaderboardModels = new ArrayList<>();
        adapter = new LeaderboardAdapter(getContext(), leaderboardModels);
        binding.leaderboardRV.setAdapter(adapter);

        isTrainer=((CommunityMainActivity)getActivity()).isTrainer;

        if(!isTrainer){
            binding.toggleComp.setVisibility(View.GONE);
        }

        loadData();

        return binding.getRoot();
    }

    private void loadData() {

        database.getReference().child("Communities").child(key)
                .child("members").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.exists()){
                        CommunitySubscriberModel model = dataSnapshot.getValue(CommunitySubscriberModel.class);
                        LeaderboardModel leaderboardModel = new LeaderboardModel(model.getUserId(),model.getPoints());
                        leaderboardModels.add(leaderboardModel);
                    }
                }
                //Sorting the points
                Collections.sort(leaderboardModels, new Comparator<LeaderboardModel>() {
                    @Override
                    public int compare(LeaderboardModel leaderboardModel, LeaderboardModel t1) {
                        return leaderboardModel.getPoints() - t1.getPoints();
                    }
                });
                Collections.reverse(leaderboardModels);

                for(int i=0; i<3;i++){
                    if(!leaderboardModels.isEmpty()){
                        top3.add(leaderboardModels.get(i));
                    }
                }

                setTop3();

                if(leaderboardModels.size() > 0){
                    leaderboardModels.remove(0);
                }
                if(leaderboardModels.size() > 0){
                    leaderboardModels.remove(0);
                }
                if(leaderboardModels.size() > 0){
                    leaderboardModels.remove(0);
                }

                adapter.notifyDataSetChanged();

                for(LeaderboardModel l : leaderboardModels){
                    Log.e("lead", l.getPoints()+"");

                }

//                for(LeaderboardModel l : top3){
//                    Log.e("", l.getPoints()+"");
//                    Log.e("f",leaderboardModels.toString());
//                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        leaderboardModels.add(new LeaderboardModel("GAGFASFDA",150));
//        leaderboardModels.add(new LeaderboardModel("GAGFASFDA",140));
//        leaderboardModels.add(new LeaderboardModel("GAGFASFDA",130));

    }

    private void setTop3() {

        if(top3.size() > 0){
            binding.points1.setText(top3.get(0).getPoints()+"");

            database.getReference().child("Users")
                    .child(top3.get(0).getUserId())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                                binding.username1.setText("@"+user.getUsername());
                                Picasso.get()
                                        .load(user.getProfilePhoto())
                                        .placeholder(R.drawable.placeholder_avatar)
                                        .into(binding.photo1);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }


        if(top3.size() > 1){
            binding.points2.setText(top3.get(1).getPoints()+"");
            database.getReference().child("Users")
                    .child(top3.get(1).getUserId())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                                binding.username2.setText("@"+user.getUsername());
                                Picasso.get()
                                        .load(user.getProfilePhoto())
                                        .placeholder(R.drawable.placeholder_avatar)
                                        .into(binding.photo2);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }


        if(top3.size() > 2){
            binding.points3.setText(top3.get(2).getPoints()+"");
            database.getReference().child("Users")
                    .child(top3.get(2).getUserId())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                                binding.username3.setText("@"+user.getUsername());
                                Picasso.get()
                                        .load(user.getProfilePhoto())
                                        .placeholder(R.drawable.placeholder_avatar)
                                        .into(binding.photo3);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }

    }
}