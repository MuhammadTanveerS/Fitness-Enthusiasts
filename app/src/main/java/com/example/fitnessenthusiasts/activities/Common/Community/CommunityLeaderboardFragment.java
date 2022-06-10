package com.example.fitnessenthusiasts.activities.Common.Community;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessenthusiasts.R;
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

                leaderboardModels.remove(0);
                leaderboardModels.remove(0);
                leaderboardModels.remove(0);
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
        binding.points1.setText(top3.get(0).getPoints()+"");
        binding.points2.setText(top3.get(1).getPoints()+"");
        binding.points3.setText(top3.get(2).getPoints()+"");
    }
}