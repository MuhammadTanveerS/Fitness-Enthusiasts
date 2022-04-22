package com.example.fitnessenthusiasts.activities.Common;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.Session;
import com.example.fitnessenthusiasts.activities.HelperClasses.Models.PostModel;
import com.example.fitnessenthusiasts.databinding.FragmentHomeBinding;
import com.example.fitnessenthusiasts.databinding.FragmentPostBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Date;


public class PostFragment extends Fragment {

    FragmentPostBinding binding;
    Uri uri;
    Session session;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog dialog;

    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(getContext());
        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));
        storage = FirebaseStorage.getInstance();
        dialog = new ProgressDialog(getContext(),R.style.MyAlertDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(inflater,container, false);

        loadDetails();

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Posting");
        dialog.setMessage("Please Wait ...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        binding.postStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String status = binding.postStatus.getText().toString();
                if(!status.isEmpty()){
                    //binding.postBtn.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.neon_btn_radius));
                    binding.postBtn.setBackgroundColor(getContext().getResources().getColor(R.color.neon));
                    binding.postBtn.setTextColor(getContext().getResources().getColor(R.color.white));
                    binding.postBtn.setEnabled(true);
                }else{
                    binding.postBtn.setBackgroundColor(getContext().getResources().getColor(R.color.sub_grey));
                    binding.postBtn.setTextColor(getContext().getResources().getColor(R.color.sub_greyDark));
                    binding.postBtn.setEnabled(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.postImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,40);

            }
        });

        binding.postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();
                final StorageReference reference = storage.getReference().child("posts")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child(new Date().getTime()+"");
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                PostModel post = new PostModel();
                                post.setPostImage(uri.toString());
                                post.setPostedBy(FirebaseAuth.getInstance().getUid());
                                post.setPostStatus(binding.postStatus.getText().toString());
                                post.setPostedAt(new Date().getTime());

                                database.getReference().child("Posts")
                                        .push()
                                        .setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialog.dismiss();
                                        Toast.makeText(getContext(), "Posted Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }
                });
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData()!=null){
            uri = data.getData();
            binding.postImage.setImageURI(uri);
            binding.postImage.setVisibility(View.VISIBLE);

            binding.postBtn.setBackgroundColor(getContext().getResources().getColor(R.color.neon));
            binding.postBtn.setTextColor(getContext().getResources().getColor(R.color.white));
            binding.postBtn.setEnabled(true);
        }
    }

    private void loadDetails(){
        Picasso.get()
                .load(session.getPhoto())
                .placeholder(R.drawable.placeholder_avatar)
                .into(binding.postPic);
        binding.postName.setText(session.getName());
    }
}