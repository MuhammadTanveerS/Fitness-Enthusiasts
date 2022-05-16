package com.example.fitnessenthusiasts.activities.Common.Messages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.MessageUserAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.MessageUserAdapter2;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.FollowModel;
import com.example.fitnessenthusiasts.databinding.ActivityMessagesMainBinding;
import com.example.fitnessenthusiasts.databinding.ActivityViewUsersBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewUsers extends AppCompatActivity {

    ActivityViewUsersBinding binding;
    FirebaseDatabase database;
    ArrayList<UserHelperClass> users;
    MessageUserAdapter2 adapter;
    ArrayList<String> followUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityViewUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));
        users = new ArrayList<>();
        adapter = new MessageUserAdapter2(this,users);
        binding.msgUserRV.setAdapter(adapter);
        followUsers = new ArrayList<>();

        fetchData();
    }

    private void fetchData() {
        binding.progressBar.setVisibility(View.VISIBLE);
        database.getReference().child("Users")
                .child(FirebaseAuth.getInstance().getUid())
                .child("following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    users.clear();
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        FollowModel model = dataSnapshot.getValue(FollowModel.class);
                        followUsers.add(model.getFollowed());
                    }
                }
                for(int i=0; i<followUsers.size();i++){
                    database.getReference().child("Users")
                            .child(followUsers.get(i))
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                    UserHelperClass user = snapshot1.getValue(UserHelperClass.class);
                                    user.setUserID(snapshot1.getKey());
                                    users.add(user);
                                    Log.e("gggg",user.getFullName());
                                    binding.progressBar.setVisibility(View.GONE);
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void msgFinish(View view) {
        finish();
    }
}