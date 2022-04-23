package com.example.fitnessenthusiasts.activities.Common.Other;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.CommentAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.CommentModel;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.PostModel;
import com.example.fitnessenthusiasts.databinding.ActivityCommentsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class CommentActivity extends AppCompatActivity {

    ActivityCommentsBinding binding;
    Intent intent;
    String postId,postedBy;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ArrayList<CommentModel> list;
    CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent = getIntent();

        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

        postId = intent.getStringExtra("postId");
        postedBy = intent.getStringExtra("postedBy");
        fetchData();

        list=new ArrayList<>();
        adapter=new CommentAdapter(this,list);
        binding.commentRV.setLayoutManager(new LinearLayoutManager(this));
        binding.commentRV.setAdapter(adapter);
        fetchComments();

    }


    private void fetchData() {
        database.getReference().child("Posts")
                .child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostModel post = snapshot.getValue(PostModel.class);
                Picasso.get()
                        .load(post.getPostImage())
                        .placeholder(R.drawable.image_placeholder)
                        .into(binding.postPic);
                binding.postStatus.setText(post.getPostStatus());
                binding.postLikeCount.setText(post.getPostLikes()+"");
                binding.postComCount.setText(post.getCommentCount()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("Users")
                .child(postedBy).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                Picasso.get()
                        .load(user.getProfilePhoto())
                        .placeholder(R.drawable.placeholder_avatar)
                        .into(binding.postProPic);
                binding.postProName.setText(user.getFullName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void close(View view) {
        finish();
    }

    public void postComment(View view){
        CommentModel comment = new CommentModel();
        comment.setCommentDesc(binding.textContent.getText().toString());
        comment.setCommentedAt(new Date().getTime());
        comment.setCommentedBy(FirebaseAuth.getInstance().getUid());

        database.getReference().child("Posts")
                .child(postId).child("comments")
                .push()
                .setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                database.getReference().child("Posts")
                        .child(postId).child("commentCount")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int commentCount =0;
                                if(snapshot.exists()){
                                    commentCount=snapshot.getValue(Integer.class);
                                }
                                database.getReference().child("Posts")
                                        .child(postId).child("commentCount")
                                        .setValue(commentCount+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        binding.textContent.setText("");
                                        Toast.makeText(CommentActivity.this, "COMMENTED", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }
        });
    }

    private void fetchComments() {
        database.getReference().child("Posts")
                .child(postId).child("comments")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            CommentModel commentModel = dataSnapshot.getValue(CommentModel.class);
                            list.add(commentModel);
//                            Log.e("Er",commentModel.getCommentDesc());
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}