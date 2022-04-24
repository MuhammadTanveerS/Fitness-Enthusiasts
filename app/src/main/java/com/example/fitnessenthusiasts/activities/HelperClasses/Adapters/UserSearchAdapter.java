package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.FollowModel;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.NotificationModel;
import com.example.fitnessenthusiasts.databinding.UsersearchViewLayoutBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.viewHolder> {

    Context context;
    ArrayList<UserHelperClass> list;

    public UserSearchAdapter(Context context, ArrayList<UserHelperClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.usersearch_view_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        UserHelperClass user = list.get(position);
        Picasso.get()
                .load(user.getProfilePhoto())
                .placeholder(R.drawable.placeholder_avatar)
                .into(holder.binding.profilePic);
        holder.binding.profileName.setText(user.getFullName());
        holder.binding.profileFollowers.setText("4");

        FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                .getReference().child("Users").child(user.getUserID())
                .child("followers").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    holder.binding.followButton.setBackground(ContextCompat.getDrawable(context,R.drawable.neon_btn_radius));
                    holder.binding.followButton.setText("Following");
                    holder.binding.followButton.setEnabled(false);

                }
                else{

                    holder.binding.followButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            notifyDataSetChanged();

                            FollowModel followModel= new FollowModel();
                            followModel.setFollowedBy(FirebaseAuth.getInstance().getUid());;
                            followModel.setFollowedAt(new Date().getTime());

                            FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                                    .getReference().child("Users").child(user.getUserID())
                                    .child("followers").child(FirebaseAuth.getInstance().getUid())
                                    .setValue(followModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                                            .getReference().child("Users").child(user.getUserID())
                                            .child("followersCount").setValue(user.getFollowersCount()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            holder.binding.followButton.setBackground(ContextCompat.getDrawable(context,R.drawable.neon_btn_radius));
                                            holder.binding.followButton.setText("Following");
                                            holder.binding.followButton.setEnabled(false);
                                            Toast.makeText(context, "You Followed "+user.getFullName(),Toast.LENGTH_SHORT).show(); ///IMPROVVEESSSS

                                            NotificationModel notificationModel = new NotificationModel();
                                            notificationModel.setNotificationBy(FirebaseAuth.getInstance().getUid());
                                            notificationModel.setNotificationAt(new Date().getTime());
                                            notificationModel.setType("follow");

                                            FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                                                    .getReference().child("Notification")
                                                    .child(user.getUserID()).child("notifications")
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

    public class viewHolder extends RecyclerView.ViewHolder{

        UsersearchViewLayoutBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding = UsersearchViewLayoutBinding.bind(itemView);
        }
    }
}
