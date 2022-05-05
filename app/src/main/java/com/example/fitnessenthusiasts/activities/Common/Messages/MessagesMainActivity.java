package com.example.fitnessenthusiasts.activities.Common.Messages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.MessageUserAdapter;
import com.example.fitnessenthusiasts.databinding.ActivityMessagesMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MessagesMainActivity extends AppCompatActivity {

    ActivityMessagesMainBinding binding;
    FirebaseDatabase database;
    ArrayList<UserHelperClass> users;
    MessageUserAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessagesMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));
        users = new ArrayList<>();
        adapter = new MessageUserAdapter(this,users);
        binding.msgUserRV.setAdapter(adapter);

        fetchData();
    }

    private void fetchData() {
    }

    public void msgFinish(View view) {
        finish();
    }
}