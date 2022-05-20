package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.Activities.PlanMainActivity;
import com.example.fitnessenthusiasts.activities.Common.Other.CommentActivity;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.PlansModel;
import com.example.fitnessenthusiasts.databinding.PlansViewLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.MyViewHolder>{

    Context context;
    ArrayList<PlansModel> list;

    public PlansAdapter(Context context, ArrayList<PlansModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.plans_view_layout,parent,false);
        return new PlansAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PlansModel model = list.get(position);
        holder.binding.planName.setText(model.getPlanName());
        Picasso.get()
                .load(model.getPlanImage())
                .placeholder(R.drawable.image_placeholder)
                .into(holder.binding.planImage);
        String planDaysAndType = model.getPlanDays()+" days ● "+model.getPlanType();
        holder.binding.planDaysAndType.setText(planDaysAndType);

        holder.binding.planCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlanMainActivity.class);
                intent.putExtra("planName",model.getPlanName());
                intent.putExtra("planImage",model.getPlanImage());
                intent.putExtra("planDetails",model.getPlanDetails());
                intent.putExtra("planDaysAndType",planDaysAndType);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        PlansViewLayoutBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = PlansViewLayoutBinding.bind(itemView);
        }
    }
}
