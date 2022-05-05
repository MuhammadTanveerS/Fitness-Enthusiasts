package com.example.fitnessenthusiasts.activities.Common.Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.FollowModel;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.NotificationModel;
import com.example.fitnessenthusiasts.databinding.ActivityUserProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class UserProfile extends AppCompatActivity {

    ActivityUserProfileBinding binding;
    Intent intent;
    String userId, userFName, userName, userPhoto;
    int userFollowersCount;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        ;
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

        intent = getIntent();
        getDataFromIntent();
        followButton();
    }

    private void getDataFromIntent() {
        userId = intent.getStringExtra("userId");
        userFName = intent.getStringExtra("userFName");
        userName = intent.getStringExtra("userName");
        userPhoto = intent.getStringExtra("userPhoto");
        userFollowersCount = intent.getIntExtra("userFollowersCount", 0);

        binding.profileName.setText(userFName);
        Picasso.get()
                .load(userPhoto)
                .placeholder(R.drawable.placeholder_avatar)
                .into(binding.circularImageView);
        binding.noFollowers.setText(userFollowersCount + "");
        binding.profileUsername.setText(userName);
    }

    private void followButton() {

        database.getReference().child("Users").child(userId)
                .child("followers").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.followButton.setBackgroundColor(getResources().getColor(R.color.neon));
                    binding.followButton.setText("Following");
                    binding.followButton.setTextColor(getResources().getColor(R.color.white));
                    binding.followButton.setEnabled(false);

                } else {

                    binding.followButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FollowModel followModel = new FollowModel();
                            followModel.setFollowedBy(FirebaseAuth.getInstance().getUid());
                            ;
                            followModel.setFollowedAt(new Date().getTime());

                            FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                                    .getReference().child("Users").child(userId)
                                    .child("followers").child(FirebaseAuth.getInstance().getUid())
                                    .setValue(followModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    database.getReference().child("Users").child(userId)
                                            .child("followersCount").setValue(userFollowersCount + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
//
                                            binding.followButton.setBackgroundColor(getResources().getColor(R.color.neon));
                                            binding.followButton.setText("Following");
                                            binding.followButton.setTextColor(getResources().getColor(R.color.white));
                                            binding.followButton.setEnabled(false);


                                            Toast.makeText(UserProfile.this, "You Followed " + userFName, Toast.LENGTH_SHORT).show(); ///IMPROVVEESSSS

                                            NotificationModel notificationModel = new NotificationModel();
                                            notificationModel.setNotificationBy(FirebaseAuth.getInstance().getUid());
                                            notificationModel.setNotificationAt(new Date().getTime());
                                            notificationModel.setType("follow");

                                            FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                                                    .getReference().child("Notification")
                                                    .child(userId).child("notifications")
                                                    .push().setValue(notificationModel);

                                            /*
                                            ADD
                                            FOLLOW
                                            ON
                                            PROFILE
                                            ONLY
                                             */
                                        }
                                    });
                                }
                            });

                            FollowModel followModel2 = new FollowModel();
                            followModel2.setFollowed(userId);
                            followModel2.setFollowedAt(new Date().getTime());

                            FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                                    .getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("following").child(userId).setValue(followModel2);
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