package com.example.fitnessenthusiasts.activities.Common;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Databases.SPManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    View view;
    CircleImageView avatar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        avatar = view.findViewById(R.id.circular);
        LoadImage();


        // Inflate the layout for this fragment
        return view;
    }

    private void LoadImage(){
        SPManager spManager = new SPManager(view.getContext());
        HashMap<String, String> userDetails = spManager.getUserDetails();
        String image = userDetails.get(SPManager.S_IMAGE);

        Picasso.get()
                .load(image)
                .into(avatar);
    }

}