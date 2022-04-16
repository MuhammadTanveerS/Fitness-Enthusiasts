package com.example.fitnessenthusiasts.activities.HelperClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.Workout.WorkoutDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WorkoutsRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutsRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<WorkoutsModel> list;

    public WorkoutsRecyclerViewAdapter(Context context, ArrayList<WorkoutsModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public WorkoutsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.workouts_view_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutsRecyclerViewAdapter.MyViewHolder holder, int position) {

        WorkoutsModel workoutsModel = list.get(position);
        holder.woName.setText(workoutsModel.name);
        holder.woDiff.setText(workoutsModel.diff);
        holder.woPoints.setText(workoutsModel.points);
        Picasso.get()
                .load(workoutsModel.bg)
                .into(holder.woBg);

        //Sending data
        holder.workoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WorkoutDetails.class);
                intent.putExtra("name",workoutsModel.name);
                intent.putExtra("diff",workoutsModel.diff);
                intent.putExtra("points",workoutsModel.points);
                intent.putExtra("bg",workoutsModel.bg);
                intent.putExtra("key",workoutsModel.key);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView woName,woDiff,woPoints;
        ImageView woBg;
        CardView workoutCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            woName = itemView.findViewById(R.id.woName);
            woDiff = itemView.findViewById(R.id.woDiff);
            woPoints = itemView.findViewById(R.id.woPoints);
            woBg = itemView.findViewById(R.id.woBg);
            workoutCard = itemView.findViewById(R.id.workoutCard);
        }
    }

}
