package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.PostModel;
import com.example.fitnessenthusiasts.databinding.PostsViewLayoutBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    ArrayList<PostModel> list;
    Context context;

    public PostAdapter(ArrayList<PostModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.posts_view_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PostModel model = list.get(position);
        Picasso.get()
                .load(model.getPostImage())
                .fit()
                .placeholder(R.drawable.image_placeholder)
                .into(holder.binding.postPic);
        holder.binding.postLikeCount.setText(model.getPostLikes()+"");
        String status = model.getPostStatus();
        if(status.equals("")){
            holder.binding.postStatus.setVisibility(View.GONE);
        }else{
            holder.binding.postStatus.setText(status);
            holder.binding.postStatus.setVisibility(View.VISIBLE);
        }


        FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                .getReference().child("Users")
                .child(model.getPostedBy()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                Picasso.get()
                        .load(user.getProfilePhoto())
                        .placeholder(R.drawable.placeholder_avatar)
                        .into(holder.binding.postProPic);
                holder.binding.postProName.setText(user.getFullName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                .getReference().child("Posts").child(model.getPostId())
                .child("likes").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            holder.binding.postLikeIC.setImageResource(R.drawable.ic_like_filled);
                        }
                        else{
                            holder.binding.likeLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                                            .getReference().child("Posts")
                                            .child(model.getPostId()).child("likes")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                                                    .getReference().child("Posts")
                                                    .child(model.getPostId()).child("postLikes")
                                                    .setValue(model.getPostLikes()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    holder.binding.postLikeIC.setImageResource(R.drawable.ic_like_filled);
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        PostsViewLayoutBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = PostsViewLayoutBinding.bind(itemView);

        }
    }
}
