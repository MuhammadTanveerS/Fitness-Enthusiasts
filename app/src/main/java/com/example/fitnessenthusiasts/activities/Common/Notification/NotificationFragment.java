package com.example.fitnessenthusiasts.activities.Common.Notification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.NotificationAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.NotificationViewPagerAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.PostAdapter;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.NotificationModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class NotificationFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<NotificationModel> notificationModels;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = view.findViewById(R.id.notifRecyclerView);

        notificationModels = new ArrayList<>();
        notificationModels.add(new NotificationModel(R.drawable.avatar_male,"David liked your post.","Just Now"));
        notificationModels.add(new NotificationModel(R.drawable.avatar_female,"Angelina commented on your post.","2m"));
        notificationModels.add(new NotificationModel(R.drawable.avatar_female2,"Rachel commented on your post.","3m"));
        notificationModels.add(new NotificationModel(R.drawable.avatar_male2,"Kevin liked your post.","3m"));
        notificationModels.add(new NotificationModel(R.drawable.avatar_female,"Angelina liked your post.","6m"));
        notificationModels.add(new NotificationModel(R.drawable.avatar_female2,"Rachel liked your post.","8m"));

        NotificationAdapter notificationAdapter = new NotificationAdapter(notificationModels,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(notificationAdapter);

        return view;
    }
}