package com.example.fitnessenthusiasts.activities.Common.Community;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.CommunityInfoModel;
import com.example.fitnessenthusiasts.databinding.FragmentActivityBinding;
import com.example.fitnessenthusiasts.databinding.FragmentCommunityDetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;

public class CommunityDetailsFragment extends Fragment {

    FABRevealLayout fabRevealLayout;
    FragmentCommunityDetailsBinding binding;
    FirebaseDatabase database;
    String key,name;


    public CommunityDetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCommunityDetailsBinding.inflate(inflater,container, false);

        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

//        fabRevealLayout = view.findViewById(R.id.fab_reveal_layout);
        fabRevealLayout = binding.fabRevealLayout;
        configureFABReveal(fabRevealLayout);

        key=((CommunityMainActivity)getActivity()).key;
        name=((CommunityMainActivity)getActivity()).name;
        fetchDate();
        return binding.getRoot();
    }

    private void fetchDate() {
        database.getReference().child("Communities").child(key).child("info")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            CommunityInfoModel model = snapshot.getValue(CommunityInfoModel.class);

                            binding.comName.setText(name);
                            binding.comAge.setText(model.getAgeLimit());
                            binding.comDes1.setText(model.getDescription1());
                            binding.comDes2.setText(model.getDescription2());
                            binding.comPrice.setText("$"+model.getPrice()+"/month");
                            binding.comType.setText(model.getType());
                            binding.comBtnSub.setText("Subscribe \n $"+model.getPrice()+"/m");

                            Picasso.get()
                                    .load(model.getImage())
                                    .placeholder(R.drawable.image_placeholder)
                                    .into(binding.comImage);


                            database.getReference().child("Users").child(model.getTrainer())
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            UserHelperClass model2 = snapshot.getValue(UserHelperClass.class);

                                            binding.comTrainerName.setText(model2.getFullName());
                                            Picasso.get()
                                                    .load(model2.getProfilePhoto())
                                                    .placeholder(R.drawable.image_placeholder)
                                                    .into(binding.comTrainerImage);
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

    private void configureFABReveal(FABRevealLayout fabRevealLayout) {
        fabRevealLayout.setOnRevealChangeListener(new OnRevealChangeListener() {
            @Override
            public void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView) {}

            @Override
            public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {
                backTransition();
            }
        });
    }

    private void backTransition() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fabRevealLayout.revealMainView();
            }
        },2000);
    }
}