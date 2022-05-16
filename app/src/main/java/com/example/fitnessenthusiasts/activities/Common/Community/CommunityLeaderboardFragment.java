package com.example.fitnessenthusiasts.activities.Common.Community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.databinding.FragmentActivityBinding;
import com.example.fitnessenthusiasts.databinding.FragmentCommunityBinding;
import com.example.fitnessenthusiasts.databinding.FragmentCommunityLeaderboardBinding;


public class CommunityLeaderboardFragment extends Fragment {

    FragmentCommunityLeaderboardBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCommunityLeaderboardBinding.inflate(inflater,container, false);



        return binding.getRoot();
    }
}