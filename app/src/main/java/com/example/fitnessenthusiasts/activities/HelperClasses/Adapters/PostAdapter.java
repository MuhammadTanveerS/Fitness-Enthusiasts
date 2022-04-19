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
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.PostModel;

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

        holder.proImage.setImageResource(model.getProfileImage());
        holder.proName.setText(model.getProfileName());
        holder.postImage.setImageResource(model.getPostImage());
        holder.postStatus.setText(model.getPostStatus());
        holder.likes.setText(model.getLikeCount());
        holder.comment.setText(model.getCommentCount());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView proImage, postImage;
        TextView proName, postStatus, comment,likes;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            proImage = itemView.findViewById(R.id.post_proPic);
            postImage = itemView.findViewById(R.id.post_pic);
            proName = itemView.findViewById(R.id.post_proName);
            postStatus = itemView.findViewById(R.id.post_status);
            comment = itemView.findViewById(R.id.post_comCount);
            likes = itemView.findViewById(R.id.post_likeCount);
        }
    }
}
