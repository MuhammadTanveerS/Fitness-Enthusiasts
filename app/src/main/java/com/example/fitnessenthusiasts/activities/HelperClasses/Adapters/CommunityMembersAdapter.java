package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.CommunitySubscriberModel;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.LeaderboardModel;
import com.example.fitnessenthusiasts.databinding.CommunityMembersViewLayoutBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class CommunityMembersAdapter extends RecyclerView.Adapter<CommunityMembersAdapter.MyViewHolder> {
    Context context;
    ArrayList<CommunitySubscriberModel> model;

    public CommunityMembersAdapter(Context context, ArrayList<CommunitySubscriberModel> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public CommunityMembersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.community_members_view_layout,parent,false);
        return new CommunityMembersAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityMembersAdapter.MyViewHolder holder, int position) {
        CommunitySubscriberModel sub = model.get(position);

        FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                .getReference().child("Users").child(sub.getUserId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                            holder.binding.profileName.setText(user.getFullName());
                            Picasso.get()
                                    .load(user.getProfilePhoto())
                                    .placeholder(R.drawable.placeholder_avatar)
                                    .into(holder.binding.profilePic);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                .getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                            if(!user.getTrainer()){
                                holder.binding.btnRemove.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.binding.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                        .getReference().child("Communities").child(sub.getCommunityKey())
                        .child("members").child(sub.getUserId()).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(context, "User Removed", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Failed to Remove", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CommunityMembersViewLayoutBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CommunityMembersViewLayoutBinding.bind(itemView);
        }
    }
}
