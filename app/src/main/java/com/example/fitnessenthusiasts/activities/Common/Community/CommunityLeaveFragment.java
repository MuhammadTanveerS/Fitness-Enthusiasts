package com.example.fitnessenthusiasts.activities.Common.Community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.databinding.FragmentCommunityLeaveBinding;
import com.example.fitnessenthusiasts.databinding.FragmentCommunityMembersBinding;


public class CommunityLeaveFragment extends Fragment {

    FragmentCommunityLeaveBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCommunityLeaveBinding.inflate(inflater,container, false);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return binding.getRoot();
    }
}