package com.example.fitnessenthusiasts.activities.Common.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.databinding.FragmentCommunityLeaderboardBinding;
import com.example.fitnessenthusiasts.databinding.FragmentPlansBinding;


public class PlansFragment extends Fragment {
    FragmentPlansBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlansBinding.inflate(inflater,container, false);



        return binding.getRoot();
    }
}