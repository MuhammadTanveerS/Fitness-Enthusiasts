package com.example.fitnessenthusiasts.activities.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Navigation Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_screen);

        //Testing Session
        TextView textView = findViewById(R.id.testTV);

        SPManager spManager = new SPManager(this);
        HashMap<String, String> userDetails = spManager.getUserDetails();

        String fullName = userDetails.get(SPManager.S_FULLNAME);
        String email = userDetails.get(SPManager.S_EMAIL);
        String gender = userDetails.get(SPManager.S_GENDER);

        textView.setText(fullName + "\n" + email + "\n" + gender);

        //Hooks
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);
        navIcon = findViewById(R.id.nav_icon);

        navigationBar();


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
                return true;
            case R.id.step_counter:
                Intent intent = new Intent(getApplicationContext(), testMainActivity.class);
                startActivity(intent);
                return true;
        }

        return true;
    }



}