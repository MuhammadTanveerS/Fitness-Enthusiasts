package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.Exercises;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.ExercisesModel;

import java.util.ArrayList;

public class ExercisesRecyclerViewAdapter extends RecyclerView.Adapter<ExercisesRecyclerViewAdapter.MyViewHolder>{

    Context context;
    ArrayList<ExercisesModel> list;

    public ExercisesRecyclerViewAdapter(Context context, ArrayList<ExercisesModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ExercisesRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.exercise_view_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesRecyclerViewAdapter.MyViewHolder holder, int position) {

        ExercisesModel exercisesModel = list.get(position);
        holder.exName.setText(exercisesModel.getName());
        holder.exTime.setText(exercisesModel.getTime());
        holder.exAnim.setAnimation(Exercises.ex(exercisesModel.getName()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView exName, exTime;
        LottieAnimationView exAnim;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            exName = itemView.findViewById(R.id.exName);
            exTime = itemView.findViewById(R.id.exTime);
            exAnim = itemView.findViewById(R.id.exAnim);
        }
    }
}
