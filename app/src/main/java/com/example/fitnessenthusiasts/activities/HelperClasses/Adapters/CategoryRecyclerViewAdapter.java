package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.MainActivity;
import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.Community.CommunityMainActivity;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.CategoryModel;

import java.util.ArrayList;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<CategoryModel> list;

    public CategoryRecyclerViewAdapter(Context context, ArrayList<CategoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CategoryRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.community_view_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerViewAdapter.MyViewHolder holder, int position) {

        CategoryModel categoryModel = list.get(position);
        holder.textView.setText(categoryModel.getCatName());
        holder.imageView.setImageResource(categoryModel.getCatImage());
        holder.relativeLayout.setBackgroundResource(categoryModel.getCatBgImage());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Selected "+ categoryModel.getCatName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), CommunityMainActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.catImage);
            textView = itemView.findViewById(R.id.catText);
            relativeLayout = itemView.findViewById(R.id.myRelativeLayout);

        }
    }

}
