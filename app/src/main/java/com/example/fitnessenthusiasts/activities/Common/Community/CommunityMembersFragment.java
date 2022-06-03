package com.example.fitnessenthusiasts.activities.Common.Community;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.CommunityMembersAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.CommunitySubscriberModel;
import com.example.fitnessenthusiasts.databinding.FragmentCommunityMembersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommunityMembersFragment extends Fragment {
    FragmentCommunityMembersBinding binding;
    FirebaseDatabase database;
    String key;
    ArrayList<CommunitySubscriberModel> subscriberModels;
    CommunityMembersAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCommunityMembersBinding.inflate(inflater,container, false);
        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

        key=((CommunityMainActivity)getActivity()).key;
        subscriberModels = new ArrayList<>();
        subscriberModels = new ArrayList<>();
        adapter = new CommunityMembersAdapter(getContext(),subscriberModels);
        binding.memberRV.setAdapter(adapter);

        fetchData();

        return binding.getRoot();
    }

    private void fetchData() {
        database.getReference().child("Communities").child(key)
                .child("members").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subscriberModels.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.exists()){
                        CommunitySubscriberModel model = dataSnapshot.getValue(CommunitySubscriberModel.class);
                        subscriberModels.add(model);
                        Log.e("s",model.getUserId());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}