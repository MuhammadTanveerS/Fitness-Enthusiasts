package com.example.fitnessenthusiasts.activities.Common.Messages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.MessageUserAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.FollowModel;
import com.example.fitnessenthusiasts.databinding.ActivityMessagesMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class MessagesMainActivity extends AppCompatActivity {

    ActivityMessagesMainBinding binding;
    FirebaseDatabase database;
    ArrayList<UserHelperClass> users;
    ArrayList<String>conUsers;
    MessageUserAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessagesMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));
        users = new ArrayList<>();
        adapter = new MessageUserAdapter(this,users);
        binding.msgUserRV.setAdapter(adapter);

        conUsers = new ArrayList<>();
        fetchData();
    }

    private void fetchData() {
//        database.getReference().child("Users")
//                .child(FirebaseAuth.getInstance().getUid())
//                .child("following").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
//                        FollowModel model = snapshot.getValue(FollowModel.class);
//                        Log.e("G",model.getFollowed());
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        database.getReference().child("Chats")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                String str=dataSnapshot.getKey();
                                int length = FirebaseAuth.getInstance().getUid().length();
                                String subStr=str.substring(0,length);
                                Log.e(str,subStr);

                                if(FirebaseAuth.getInstance().getUid().equals(subStr)){
                                    String userId = str.substring(length);
                                    conUsers.add(userId);
                                }

                            }
                            for(int i=0;i<conUsers.size();i++){
                                database.getReference().child("Users")
                                        .child(conUsers.get(i))
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                                users.clear();
                                                UserHelperClass user = snapshot1.getValue(UserHelperClass.class);
                                                user.setUserID(snapshot1.getKey());
//                                            Log.e("GG",user.getFullName());
                                                users.add(user);
                                                binding.progressBar.setVisibility(View.GONE);
                                                adapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
//                            Log.e("GG",conUsers.get(i));
                            }

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

    @Override
    protected void onResume() {
        super.onResume();
        String currentId = FirebaseAuth.getInstance().getUid();
        database.getReference().child("UserStatus").child(currentId).setValue("Online");

    }

    @Override
    protected void onPause() {
        super.onPause();
        String currentId = FirebaseAuth.getInstance().getUid();
        database.getReference().child("UserStatus").child(currentId).setValue("Offline");
    }

    public void viewUsersForChat(View view) {
        startActivity(new Intent(this,ViewUsers.class));
    }
}