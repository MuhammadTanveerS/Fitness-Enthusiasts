package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fitnessenthusiasts.activities.Common.Notification.ImportantFragment;
import com.example.fitnessenthusiasts.activities.Common.Notification.NotificationFragment;

public class NotificationViewPagerAdapter extends FragmentPagerAdapter {
    public NotificationViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return  new NotificationFragment();
            case 1: return  new ImportantFragment();
            default: return  new NotificationFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if(position==0){
            title = "NOTIFICATION";
        }else if(position==1){
            title = "IMPORTANT";
        }
        return title;
    }
}
