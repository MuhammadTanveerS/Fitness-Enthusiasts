package com.example.fitnessenthusiasts.activities.Common.Community;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.CommunityInfoModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommunityTrainerProfileFragment extends Fragment {

    View view;
    CircleImageView avatar;
    TextView name,username;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    FirebaseDatabase database;
    String key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        linearLayout = view.findViewById(R.id.profileLayout);
        linearLayout.setVisibility(View.GONE);

        key=((CommunityMainActivity)getActivity()).key;

        avatar = view.findViewById(R.id.circularImageView);
        name = view.findViewById(R.id.profileName);
        username = view.findViewById(R.id.profileUsername);

        loadData();

        // Inflate the layout for this fragment
        return view;
    }

    private void loadData() {

        database.getReference().child("Communities").child(key).child("info")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            CommunityInfoModel model = snapshot.getValue(CommunityInfoModel.class);
                            database.getReference().child("Users").child(model.getTrainer()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()) {
                                        UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                                        name.setText(user.getFullName());
                                        username.setText(user.getUsername());
                                        Picasso.get()
                                                .load(user.getProfilePhoto())
                                                .placeholder(R.drawable.placeholder_avatar)
                                                .into(avatar);

                                        progressBar.setVisibility(View.GONE);
                                        linearLayout.setVisibility(View.VISIBLE);
                                    }                                }

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