package com.example.fitnessenthusiasts.activities.Common.Activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.Common.Activities.Diary.DiarySearchFoods;
import com.example.fitnessenthusiasts.activities.Common.Activities.Diary.JSONPlaceholder;
import com.example.fitnessenthusiasts.activities.Common.Activities.Diary.Nutrition;
import com.example.fitnessenthusiasts.activities.Common.Activities.Diary.Post;
import com.example.fitnessenthusiasts.databinding.FragmentDiaryBinding;
import com.example.fitnessenthusiasts.databinding.FragmentPostBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DiaryFragment extends Fragment {

    View view;
    Retrofit retrofit;
    JSONPlaceholder jsonPlaceholder;
    FragmentDiaryBinding binding;

    public DiaryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDiaryBinding.inflate(inflater,container, false);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://trackapi.nutritionix.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        addFood();
        getFood2();

        return binding.getRoot();
    }

    private void addFood() {
        binding.addBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DiarySearchFoods.class);
                i.putExtra("Linner","Breakfast");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        binding.addLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DiarySearchFoods.class);
                i.putExtra("Linner","Lunch");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        binding.addSnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DiarySearchFoods.class);
                i.putExtra("Linner","Snacks");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        binding.addDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DiarySearchFoods.class);
                i.putExtra("Linner","Dinner");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
    }

    private void getFood() {
        jsonPlaceholder = retrofit.create(JSONPlaceholder.class);
        Call<List<Post>> call = jsonPlaceholder.getPost();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(view.getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Post> postList = response.body();
                for (Post p : postList){
                    Log.e("body", p.getBody());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(view.getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFood2() {
        jsonPlaceholder = retrofit.create(JSONPlaceholder.class);
        Call<JsonObject> call = jsonPlaceholder.getSearchNutritions("bhindi");

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject res = response.body();
                    String value = res.get("branded").toString();
//                    Log.e(TAG, value);
                    Gson gson=new Gson();
                    Nutrition nutrition[] = gson.fromJson(res.get("branded").toString(), Nutrition[].class);
                    for(Nutrition n : nutrition){
//                        Log.e(TAG, n.getFood_name());
//                        Log.e(TAG, n.getNf_calories()+" cal");
                    }

                } else {
                    Log.e(TAG, "Error in getSearchNutritions:" + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}