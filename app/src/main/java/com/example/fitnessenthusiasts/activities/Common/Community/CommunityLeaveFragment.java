package com.example.fitnessenthusiasts.activities.Common.Community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.databinding.FragmentCommunityLeaveBinding;
import com.example.fitnessenthusiasts.databinding.FragmentCommunityMembersBinding;
import com.google.firebase.database.FirebaseDatabase;


public class CommunityLeaveFragment extends Fragment {

    FragmentCommunityLeaveBinding binding;
    FirebaseDatabase database;

    String key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCommunityLeaveBinding.inflate(inflater,container, false);

        key=((CommunityMainActivity)getActivity()).key;
        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });


        return binding.getRoot();
    }
}