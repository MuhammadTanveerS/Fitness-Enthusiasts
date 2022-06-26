package com.example.fitnessenthusiasts.activities.Common.Activities.Diary;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fitnessenthusiasts.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiarySearchFoods extends AppCompatActivity {

    Retrofit retrofit;
    JSONPlaceholder jsonPlaceholder;
    String results ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_search_foods);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://trackapi.nutritionix.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void searchFood(View view) {
        TextView tv = findViewById(R.id.textView15);
        EditText editText = findViewById(R.id.editTextTextPersonName);
        String searchValue = editText.getText().toString();
        results = "";

        jsonPlaceholder = retrofit.create(JSONPlaceholder.class);
        Call<JsonObject> call = jsonPlaceholder.getSearchNutritions(searchValue);

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
                        Log.e(TAG, n.getFood_name());
                        Log.e(TAG, n.getNf_calories()+" cal");
                        results += n.getFood_name()+" "+n.getNf_calories()+" cal\n";
                        tv.setText(results);
                        editText.setText("");
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