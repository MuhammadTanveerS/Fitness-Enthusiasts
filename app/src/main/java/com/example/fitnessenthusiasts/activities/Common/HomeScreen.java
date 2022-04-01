package com.example.fitnessenthusiasts.activities.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.SPManager;
import com.example.fitnessenthusiasts.activities.testMainActivity;
import com.example.fitnessenthusiasts.databinding.ActivityHomeScreenBinding;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Navigation Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navIcon;

    View header;
    TextView menuName;

    ActivityHomeScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_home_screen);
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        //Testing Session

        SPManager spManager = new SPManager(this);
        HashMap<String, String> userDetails = spManager.getUserDetails();

        String fullName = userDetails.get(SPManager.S_FULLNAME);
        String email = userDetails.get(SPManager.S_EMAIL);
        String gender = userDetails.get(SPManager.S_GENDER);
        String image = userDetails.get(SPManager.S_IMAGE);




       //textView.setText(fullName + "\n" + email + "\n" + gender);

        //Hooks
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);
        navIcon = findViewById(R.id.nav_icon);

        navigationBar();


        //binding bottom nav
        binding.bottomNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.community:
                    replaceFragment(new CommunityFragment());
                    break;
            }

            return true;
        });

        //Menu
        header = navigationView.getHeaderView(0);
        menuName = header.findViewById(R.id.menu_name);
        menuName.setText(fullName);

        ImageView menuImage = header.findViewById(R.id.avatar_img);
        Picasso.get()
                .load(image)
                .into(menuImage);


    }

    //Method for navigation drawer
    private void navigationBar() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        navIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.nav_home:
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.step_counter:
                Intent intent = new Intent(getApplicationContext(), testMainActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_settings:
                startActivity(new Intent(getApplicationContext(), Settings.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
        }

        return true;
    }

    //Method for replacing fragments
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }




}