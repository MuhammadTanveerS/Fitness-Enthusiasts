package com.example.fitnessenthusiasts.activities.Common;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.SPManager;
import com.example.fitnessenthusiasts.activities.LogReg.Login;
import com.example.fitnessenthusiasts.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_settings);
        setContentView(binding.getRoot());

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



        /*
        Database
         */
        database = FirebaseDatabase.getInstance("https://fitness-enthusiasts-default-rtdb.firebaseio.com");
        storage = FirebaseStorage.getInstance();

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                binding.progressBar2.setVisibility(View.VISIBLE);

                final StorageReference reference = storage.getReference()
                        .child("image");
                reference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("Users").child(username).child("Data").child("image")
                                        .setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getApplicationContext(), "Image Updated", Toast.LENGTH_SHORT).show();
                                        binding.settingCircleImageView.setImageURI(result);
                                        binding.progressBar2.setVisibility(View.GONE);

//                                        SPManager spManager = new SPManager(Settings.this);
//                                        spManager.updateImage(result.toString());
                                    }
                                });
                            }
                        });

                    }
                });
            }
        });

        binding.changeProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");
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
}