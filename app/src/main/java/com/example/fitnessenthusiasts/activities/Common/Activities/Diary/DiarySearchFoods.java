package com.example.fitnessenthusiasts.activities.Common.Activities.Diary;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.DiarySearchAdapter;
import com.example.fitnessenthusiasts.databinding.ActivityChatBinding;
import com.example.fitnessenthusiasts.databinding.ActivityDiarySearchFoodsBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiarySearchFoods extends AppCompatActivity {

    Retrofit retrofit;
    JSONPlaceholder jsonPlaceholder;
    String results ="";
    ActivityDiarySearchFoodsBinding binding;
    DiarySearchAdapter adapter;
    ArrayList<Nutrition> nutritionModels;
    Intent intent;
    String Linner ="", date ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityDiarySearchFoodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent = getIntent();
        Linner = intent.getStringExtra("Linner");
        date = intent.getStringExtra("date");

        retrofit = new Retrofit.Builder()
                .baseUrl("https://trackapi.nutritionix.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nutritionModels = new ArrayList<>();
        adapter = new DiarySearchAdapter(nutritionModels,this);
        binding.diarySearchRV.setAdapter(adapter);

        searchFood();
    }

//    public void searchFood(View view) {
//        TextView tv = findViewById(R.id.textView15);
//        EditText editText = findViewById(R.id.editTextTextPersonName);
//        String searchValue = editText.getText().toString();
//        results = "";
//
//        jsonPlaceholder = retrofit.create(JSONPlaceholder.class);
//        Call<JsonObject> call = jsonPlaceholder.getSearchNutritions(searchValue);
//
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    JsonObject res = response.body();
//                    String value = res.get("branded").toString();
////                    Log.e(TAG, value);
//                    Gson gson=new Gson();
//                    Nutrition nutrition[] = gson.fromJson(res.get("branded").toString(), Nutrition[].class);
//                    for(Nutrition n : nutrition){
//                        Log.e(TAG, n.getFood_name());
//                        Log.e(TAG, n.getNf_calories()+" cal");
//                        results += n.getFood_name()+" "+n.getNf_calories()+" cal\n";
//                        tv.setText(results);
//                        editText.setText("");
//                    }
//
//                } else {
//                    Log.e(TAG, "Error in getSearchNutritions:" + response.code() + " " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//
//            }
//        });
//
//
//
//    }

    public void searchFood() {

        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {

                    performSearch(binding.etSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }

    private void performSearch(String searchItem) {
        nutritionModels.clear();
        binding.diarySearchRV.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);

        jsonPlaceholder = retrofit.create(JSONPlaceholder.class);
        Call<JsonObject> call = jsonPlaceholder.getSearchNutritions(searchItem);

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
                        n.setLinner(Linner);
                        n.setDate(date);
                        nutritionModels.add(n);
                    }
                    adapter.notifyDataSetChanged();
                    binding.diarySearchRV.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);

                } else {
                    Log.e(TAG, "Error in getSearchNutritions:" + response.code() + " " + response.message());
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}