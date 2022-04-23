package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.CommentModel;
import com.example.fitnessenthusiasts.databinding.CommentViewLayoutBinding;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder>{

    Context context;
    ArrayList<CommentModel> list;

    public CommentAdapter(Context context, ArrayList<CommentModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_view_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CommentModel comment = list.get(position);;
        String time = TimeAgo.using(comment.getCommentedAt());
        holder.binding.commentDesc.setText(comment.getCommentDesc());
        holder.binding.commentTime.setText(time);



        FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com")
                .getReference().child("Users").child(comment.getCommentedBy())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                        Picasso.get()
                                .load(user.getProfilePhoto())
                                .placeholder(R.drawable.placeholder_avatar)
                                .into(holder.binding.commentProPic);
                        holder.binding.commentProName.setText(user.getFullName());

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

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        CommentViewLayoutBinding binding;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=CommentViewLayoutBinding.bind(itemView);
        }
    }
}
