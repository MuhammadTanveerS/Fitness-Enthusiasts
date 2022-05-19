package com.example.fitnessenthusiasts.activities.Common.Community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.LeaderboardAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.LeaderboardModel;
import com.example.fitnessenthusiasts.databinding.FragmentActivityBinding;
import com.example.fitnessenthusiasts.databinding.FragmentCommunityBinding;
import com.example.fitnessenthusiasts.databinding.FragmentCommunityLeaderboardBinding;

import java.util.ArrayList;


public class CommunityLeaderboardFragment extends Fragment {

    FragmentCommunityLeaderboardBinding binding;
    ArrayList<LeaderboardModel> leaderboardModels;
    LeaderboardAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCommunityLeaderboardBinding.inflate(inflater,container, false);

        leaderboardModels = new ArrayList<>();
        adapter = new LeaderboardAdapter(getContext(), leaderboardModels);
        binding.leaderboardRV.setAdapter(adapter);
        loadData();

        return binding.getRoot();
    }

    private void loadData() {

        leaderboardModels.add(new LeaderboardModel("GAGFASFDA",150));
        leaderboardModels.add(new LeaderboardModel("GAGFASFDA",140));
        leaderboardModels.add(new LeaderboardModel("GAGFASFDA",130));

    }
}