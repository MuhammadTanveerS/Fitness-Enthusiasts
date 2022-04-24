package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.Other.CommentActivity;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.NotificationModel;
import com.example.fitnessenthusiasts.databinding.NotificationViewLayoutBinding;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewHolder>{

    ArrayList<NotificationModel> list;
    Context context;

    public NotificationAdapter(ArrayList<NotificationModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View  view= LayoutInflater.from(context).inflate(R.layout.notification_view_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        NotificationModel model = list.get(position);
        String type = model.getType();

        FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                .getReference().child("Users").child(model.getNotificationBy())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                        Picasso.get()
                                .load(user.getProfilePhoto())
                                .placeholder(R.drawable.placeholder_avatar)
                                .into(holder.binding.profilePic);
                        String time = TimeAgo.using(model.getNotificationAt());
                        holder.binding.notificationTime.setText(time);

                        if(type.equals("like")){
                            holder.binding.notification.setText(Html.fromHtml("<b>"+user.getFullName()+"</b>"+" liked your post."));
                        }else if(type.equals("comment")){
                            holder.binding.notification.setText(Html.fromHtml("<b>"+user.getFullName()+"</b>"+" commented on your post."));
                        }else{
                            holder.binding.notification.setText(Html.fromHtml("<b>"+user.getFullName()+"</b>"+" started following you."));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.binding.notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!type.equals("follow")){

                    FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                            .getReference().child("Notification")
                            .child(model.getPostedBy()).child("notifications")
                            .child(model.getNotificationID()).child("checkIfOpen")
                            .setValue(true);

                    holder.binding.notificationLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    Intent intent = new Intent(context, CommentActivity.class);
                    intent.putExtra("postId",model.getPostID());
                    intent.putExtra("postedBy",model.getPostedBy());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
        Boolean checkIfOpen = model.isCheckIfOpen();
        if(checkIfOpen){
            holder.binding.notificationLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else{ }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        NotificationViewLayoutBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = NotificationViewLayoutBinding.bind(itemView);

        }
    }
}
