package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.Activities.Diary.Nutrition;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.BadgeModel;
import com.example.fitnessenthusiasts.databinding.BadgeViewLayoutBinding;
import com.example.fitnessenthusiasts.databinding.DiarySearchViewLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.viewHolder>{

    ArrayList<BadgeModel> list;
    Context context;

    public BadgeAdapter(ArrayList<BadgeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(context).inflate(R.layout.badge_view_layout,parent,false);
        return new BadgeAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        BadgeModel model = list.get(position);

        holder.binding.badgeName.setText(model.getName());
        holder.binding.badgeDetails.setText(model.getDetails());

        Picasso.get()
                .load(model.getImage())
                .into(holder.binding.badgePic);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        BadgeViewLayoutBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = BadgeViewLayoutBinding.bind(itemView);
        }
    }
}
