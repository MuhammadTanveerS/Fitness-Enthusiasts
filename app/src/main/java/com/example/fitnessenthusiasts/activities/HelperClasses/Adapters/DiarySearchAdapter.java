package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.Activities.Diary.DiaryFoodDetailsActivity;
import com.example.fitnessenthusiasts.activities.Common.Activities.Diary.Nutrition;
import com.example.fitnessenthusiasts.activities.Common.Other.CommentActivity;
import com.example.fitnessenthusiasts.databinding.DiarySearchViewLayoutBinding;

import java.util.ArrayList;

public class DiarySearchAdapter extends RecyclerView.Adapter<DiarySearchAdapter.viewHolder>{

    ArrayList<Nutrition> list;
    Context context;

    public DiarySearchAdapter(ArrayList<Nutrition> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(context).inflate(R.layout.diary_search_view_layout,parent,false);
        return new DiarySearchAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Nutrition model = list.get(position);

        holder.binding.foodName.setText(model.getFood_name());
        String details = model.getNf_calories()+" cal, "+model.getServing_unit();
        holder.binding.foodDetails.setText(details);

        holder.binding.diarySearchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DiaryFoodDetailsActivity.class);
                intent.putExtra("foodId",model.getNix_item_id());
                intent.putExtra("Linner",model.getLinner());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        DiarySearchViewLayoutBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DiarySearchViewLayoutBinding.bind(itemView);
        }
    }
}
