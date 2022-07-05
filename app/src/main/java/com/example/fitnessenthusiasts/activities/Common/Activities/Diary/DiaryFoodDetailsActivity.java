package com.example.fitnessenthusiasts.activities.Common.Activities.Diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.databinding.ActivityDiaryFoodDetailsBinding;
import com.example.fitnessenthusiasts.databinding.ActivityDiarySearchFoodsBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiaryFoodDetailsActivity extends AppCompatActivity {

    ActivityDiaryFoodDetailsBinding binding;
    Intent intent;
    Retrofit retrofit;
    JSONPlaceholder jsonPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityDiaryFoodDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        retrofit = new Retrofit.Builder()
                .baseUrl("https://trackapi.nutritionix.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        intent = getIntent();
        String foodId = intent.getStringExtra("foodId");
        binding.textView15.setText(foodId);

        fetchFoodData(foodId);
    }

        public void fetchFoodData(String id) {

        jsonPlaceholder = retrofit.create(JSONPlaceholder.class);
        Call<JsonObject> call = jsonPlaceholder.getFoodDetails(id);
        Log.e("Before Enque",id+"   "+call);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("Inside Enque",response+"");
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject res = response.body();
                    String value = res.get("foods").toString();
                    Log.e("Response", value);
                    Gson gson=new Gson();
//                    Nutrition nutrition[] = gson.fromJson(res.get("branded").toString(), Nutrition[].class);
//                    for(Nutrition n : nutrition){
//                        Log.e(TAG, n.getFood_name());
//                        Log.e(TAG, n.getNf_calories()+" cal");
//                        results += n.getFood_name()+" "+n.getNf_calories()+" cal\n";
//                        tv.setText(results);
//                        editText.setText("");
//                    }

                } else {
                    Log.e("TAG", "Error in getFoodDetails:" + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });



    }
}