package com.example.fitnessenthusiasts.activities.Common.Community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.CommunityViewPagerAdapter;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class CommunityMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_community_main);

        setUpNav();
    }

    private void setUpNav() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_vertical_ntb);

        viewPager.setAdapter(new CommunityViewPagerAdapter(getSupportFragmentManager()));

        final String[] colors = getResources().getStringArray(R.array.vertical_ntb);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_vertical);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_home),
                        Color.parseColor(colors[0]))
                        .title("home")
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_home))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_sixth),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("leaderboard")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_workout),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_workout))
                        .title("workouts")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_dietplan),
                        Color.parseColor(colors[3]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_dietplan))
                        .title("plans")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_chat),
                        Color.parseColor(colors[4]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_chat))
                        .title("ic_fifth")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_trainer),
                        Color.parseColor(colors[5]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_trainer))
                        .title("ic_sixth")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_exit),
                        Color.parseColor(colors[6]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_exit))
                        .title("ic_seventh")
                        .build()
        );


        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
}
}