package com.example.fitnessenthusiasts.activities.Common;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.SPManager;
import com.example.fitnessenthusiasts.activities.Databases.UserHelperClass;
import com.example.fitnessenthusiasts.activities.LogReg.Login;
import com.example.fitnessenthusiasts.activities.LogReg.StartUpScreen;
import com.example.fitnessenthusiasts.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Settings extends AppCompatActivity {

    ActivitySettingsBinding binding;
    ActivityResultLauncher<String> launcher;
    FirebaseDatabase database;
    FirebaseStorage storage;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));
        loadData();

        /*
        Shared Preferences
         */
        SPManager spManager = new SPManager(this);
        HashMap<String, String> userDetails = spManager.getUserDetails();
        String username = userDetails.get(SPManager.S_USERNAME);
        /*
        +
        ADD
        WORK
        LATER
        +
         */
        updateData();

        /*
        Progress Bar
         */
        binding.progressBar2.setVisibility(View.GONE);

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i  = new Intent(getApplicationContext(), StartUpScreen.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(i);
            }
        });

    }

    private void updateData(){
        SPManager spManager = new SPManager(Settings.this);
        HashMap<String, String> userDetails = spManager.getUserDetails();
        String image = userDetails.get(SPManager.S_IMAGE);

        Picasso.get()
                .load(image)
                .into(binding.settingCircleImageView);
    }

    public void changeProfPic(View view){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,22);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        binding.progressBar2.setVisibility(View.VISIBLE);
        if(data.getData()!=null){
            Uri uri = data.getData();


            final StorageReference reference = storage.getReference("profile_photo")
                    .child(FirebaseAuth.getInstance().getUid());
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    binding.progressBar2.setVisibility(View.GONE);
                    binding.settingCircleImageView.setImageURI(uri);
                    Toast.makeText(Settings.this, "PHOTO CHANGED", Toast.LENGTH_SHORT).show();
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(auth.getUid()).child("profilePhoto").setValue(uri.toString());
                        }
                    });
                }
            });
        }
    }

    public void close(View view) {
        finish();
    }

    private void loadData(){
        database.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                    binding.nameSettings.setText(user.getFullName());
                    Picasso.get()
                            .load(user.getProfilePhoto())
                            .placeholder(R.drawable.placeholder_avatar)
                            .into(binding.settingCircleImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}