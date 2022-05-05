package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.example.fitnessenthusiasts.databinding.MessageUserviewLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessageUserAdapter extends RecyclerView.Adapter<MessageUserAdapter.MyViewHolder> {

    Context context;
    ArrayList<UserHelperClass> user;

    public MessageUserAdapter(Context context, ArrayList<UserHelperClass> user) {
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_userview_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserHelperClass model = user.get(position);

        holder.binding.msgProfileName.setText(model.getFullName());
        Picasso.get()
                .load(model.getProfilePhoto())
                .placeholder(R.drawable.placeholder_avatar)
                .into(holder.binding.msgProfilePic);

    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        MessageUserviewLayoutBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MessageUserviewLayoutBinding.bind(itemView);
        }
    }
}
