package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.LeaderboardModel;
import com.example.fitnessenthusiasts.databinding.LeaderboardViewLayoutBinding;

import java.util.ArrayList;


public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder> {

    Context context;
    ArrayList<LeaderboardModel> user;

    public LeaderboardAdapter(Context context, ArrayList<LeaderboardModel> user) {
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.leaderboard_view_layout,parent,false);
        return new LeaderboardAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LeaderboardModel model = user.get(position);

        holder.binding.points.setText(model.getPoints()+"");

    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        LeaderboardViewLayoutBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = LeaderboardViewLayoutBinding.bind(itemView);
        }
    }
}
