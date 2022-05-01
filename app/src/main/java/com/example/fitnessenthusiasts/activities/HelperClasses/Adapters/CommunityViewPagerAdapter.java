package com.example.fitnessenthusiasts.activities.HelperClasses.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fitnessenthusiasts.activities.Common.Community.CommunityDetailsFragment;
import com.example.fitnessenthusiasts.activities.Common.Notification.ImportantFragment;
import com.example.fitnessenthusiasts.activities.Common.Notification.NotificationFragment;
import com.example.fitnessenthusiasts.activities.Common.ProfileFragment;
import com.example.fitnessenthusiasts.activities.Common.WorkoutsFragment;

public class CommunityViewPagerAdapter extends FragmentPagerAdapter {

    public CommunityViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return  new CommunityDetailsFragment();
            case 1: return  new ImportantFragment();
            case 4: return new WorkoutsFragment();
            case 5: return new ProfileFragment();
            case 6: return new WorkoutsFragment();
            default: return  new NotificationFragment();
        }
    }

    @Override
    public int getCount() {
        return 8;
    }
}