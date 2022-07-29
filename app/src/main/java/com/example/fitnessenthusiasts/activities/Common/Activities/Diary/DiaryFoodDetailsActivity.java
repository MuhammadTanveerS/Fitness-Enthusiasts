package com.example.fitnessenthusiasts.activities.Common.Activities.Diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.fitnessenthusiasts.R;
import com.example.fitnessenthusiasts.databinding.ActivityDiaryFoodDetailsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

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
    private PieChart chart;
    private int i1 = 15;
    private int i2 = 25;
    private int i3 = 35;
    float fat,carbs,protein;
    String Linner, date;
    Nutrition2[] nutrition;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityDiaryFoodDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.detailsLL.setVisibility(View.GONE);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://trackapi.nutritionix.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));

        intent = getIntent();
        String foodId = intent.getStringExtra("foodId");
        Linner =intent.getStringExtra("Linner");
        date =intent.getStringExtra("date");

        Log.e(Linner, date);

        fetchFoodData(foodId);
        addFood();
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
                    nutrition = gson.fromJson(res.get("foods").toString(), Nutrition2[].class);
                    Log.e("here", nutrition[0].getServing_unit());
//
                    binding.fdFoodName.setText(nutrition[0].getFood_name());
                    binding.fdCalories.setText(nutrition[0].getNf_calories());
                    binding.fdServingNum.setText(nutrition[0].getServing_qty());
                    binding.fdServingUnit.setText(nutrition[0].getServing_unit());
                    binding.fdServingSize.setText(nutrition[0].getServing_weight_grams()+" grams");
                    binding.fdDate.setText(date);
                    binding.fdMeal.setText(Linner);

                    carbs = nutrition[0].getNf_total_carbohydrate();
                    fat = nutrition[0].getNf_total_fat();
                    protein = nutrition[0].getNf_protein();
                    binding.fdCarbs.setText(carbs+" g");
                    binding.fdFat.setText(fat+" g");
                    binding.fdProtein.setText(protein+" g");

                    binding.progressBar.setVisibility(View.GONE);
                    binding.detailsLL.setVisibility(View.VISIBLE);
                    addToPieChart();

                } else {
                    Log.e("TAG", "Error in getFoodDetails:" + response.code() + " " + response.message());
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void addToPieChart() {

        float total = carbs+fat+protein;
        float carbP = (carbs/total)*100;
        float fatP = (fat/total)*100;
        float proteinP = (protein/total)*100;
        binding.fdCarbsPercent.setText(Math.round(carbP)+"%");
        binding.fdFatPercent.setText(Math.round(fatP)+"%");
        binding.fdProteinPercent.setText(Math.round(proteinP)+"%");

        // add to pie chart

        binding.pieChart.addPieSlice(new PieModel("Carbs", carbs, Color.parseColor("#08D9D6")));
        binding.pieChart.addPieSlice(new PieModel("Fat", fat, Color.parseColor("#FF7396")));
        binding.pieChart.addPieSlice(new PieModel("Protein", protein, Color.parseColor("#E8AA42")));

        binding.pieChart.startAnimation();
    }

    private void addFood() {
        binding.addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getReference().child("Diary").child(FirebaseAuth.getInstance().getUid())
                        .child(date).child(Linner).push().setValue(nutrition[0]).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(DiaryFoodDetailsActivity.this, "Food Added", Toast.LENGTH_SHORT).show();
                        binding.addFood.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    public void closeActivity(View view) {
        finish();
    }
}