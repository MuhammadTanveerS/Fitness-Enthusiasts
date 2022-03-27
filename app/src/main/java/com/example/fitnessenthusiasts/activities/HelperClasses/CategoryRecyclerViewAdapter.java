package com.example.fitnessenthusiasts.activities.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessenthusiasts.R;

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
        holder.textView.setText(categoryModel.catName);
        holder.imageView.setImageResource(categoryModel.getCatImage());
        holder.relativeLayout.setBackgroundResource(categoryModel.getCatBgImage());

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
