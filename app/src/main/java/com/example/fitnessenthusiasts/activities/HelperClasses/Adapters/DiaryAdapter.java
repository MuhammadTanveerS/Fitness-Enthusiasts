package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.Activities.Diary.Nutrition;
import com.example.fitnessenthusiasts.activities.Common.Activities.Diary.Nutrition2;
import com.example.fitnessenthusiasts.databinding.DiaryFoodViewLayoutBinding;
import com.example.fitnessenthusiasts.databinding.DiarySearchViewLayoutBinding;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.viewHolder> {

    ArrayList<Nutrition2> list;
    Context context;

    public DiaryAdapter(ArrayList<Nutrition2> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(context).inflate(R.layout.diary_food_view_layout,parent,false);
        return new DiaryAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Nutrition2 model = list.get(position);

        holder.binding.foodName.setText(model.getFood_name());
        holder.binding.foodCalories.setText(model.getNf_calories()+" cal");
        holder.binding.foodAmount.setText(model.getServing_qty()+" "+model.getServing_unit());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        DiaryFoodViewLayoutBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DiaryFoodViewLayoutBinding.bind(itemView);
        }
    }
}
