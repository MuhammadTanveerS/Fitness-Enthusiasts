package com.example.fitnessenthusiasts.activities.Common;

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

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.databinding.FragmentHomeBinding;
import com.example.fitnessenthusiasts.databinding.FragmentPostBinding;


public class PostFragment extends Fragment {

    FragmentPostBinding binding;
    Uri uri;

    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(inflater,container, false);

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
}