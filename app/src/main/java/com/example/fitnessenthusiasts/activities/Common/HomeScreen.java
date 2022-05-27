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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.Activities.PlanMainActivity;
import com.example.fitnessenthusiasts.activities.Common.Messages.MessagesMainActivity;
import com.example.fitnessenthusiasts.activities.Common.Notification.Notifications;
import com.example.fitnessenthusiasts.activities.Common.Search.SearchPeople;
import com.example.fitnessenthusiasts.activities.Common.Workout.WorkoutDetails;
import com.example.fitnessenthusiasts.activities.Databases.SPManager;
import com.example.fitnessenthusiasts.activities.Databases.Session;
import com.example.fitnessenthusiasts.activities.LogReg.StartUpScreen;
import com.example.fitnessenthusiasts.activities.testMainActivity;
import com.example.fitnessenthusiasts.databinding.ActivityHomeScreenBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Navigation Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navIcon;
    Session session;
    View header;
    FirebaseDatabase database;
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

        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));
        //Testing Session
        session = new Session(this);

//        SPManager spManager = new SPManager(this);
//        HashMap<String, String> userDetails = spManager.getUserDetails();
//
//        String fullName = userDetails.get(SPManager.S_FULLNAME);
//        String email = userDetails.get(SPManager.S_EMAIL);
//        String gender = userDetails.get(SPManager.S_GENDER);
//        String image = userDetails.get(SPManager.S_IMAGE);




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
                case R.id.activity:
                    replaceFragment(new ActivityFragment());
                    break;
                case R.id.workouts:
                    replaceFragment(new WorkoutsFragment());
                    break;
            }

            return true;
        });

        //Menu
        header = navigationView.getHeaderView(0);
        menuName = header.findViewById(R.id.menu_name);
        menuName.setText(session.getName());

        ImageView menuImage = header.findViewById(R.id.avatar_img);
        Picasso.get()
                .load(session.getPhoto())
                .placeholder(R.drawable.placeholder_avatar)
                .into(menuImage);
        ///SOLVVEEEE

        generateToken();
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
            case R.id.nav_find:
                startActivity(new Intent(getApplicationContext(), SearchPeople.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            case R.id.test:
                startActivity(new Intent(getApplicationContext(), testMainActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), StartUpScreen.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
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


    public void notifications(View view) {
        startActivity(new Intent(getApplicationContext(), Notifications.class));
    }

    public void messages(View view) {
        startActivity(new Intent(getApplicationContext(), MessagesMainActivity.class));
    }

    private void generateToken(){
        FirebaseMessaging.getInstance()
                .getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        HashMap<String ,Object> map = new HashMap<>();
                        map.put("token",s);
                        database.getReference().child("Users")
                                .child(FirebaseAuth.getInstance().getUid())
                                .updateChildren(map);

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String currentId = FirebaseAuth.getInstance().getUid();
        database.getReference().child("UserStatus").child(currentId).setValue("Online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        String currentId = FirebaseAuth.getInstance().getUid();
        database.getReference().child("UserStatus").child(currentId).setValue("Offline");
    }



}