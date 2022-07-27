package com.example.fitnessenthusiasts.activities.Common.Activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.fitnessenthusiasts.activities.Common.Activities.Diary.Nutrition2;
import com.example.fitnessenthusiasts.activities.Common.Activities.Diary.Post;
import com.example.fitnessenthusiasts.activities.HelperClasses.Adapters.DiaryAdapter;
import com.example.fitnessenthusiasts.databinding.FragmentDiaryBinding;
import com.example.fitnessenthusiasts.databinding.FragmentPostBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    String selectedDate;
    FirebaseDatabase database;
    DiaryAdapter breakfastAdapter;
    ArrayList<Nutrition2> breakfastList, lunchList, snacksList, dinnerList;

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

        database = FirebaseDatabase.getInstance(getString(R.string.db_instance));
        breakfastList = new ArrayList<>();
        breakfastAdapter = new DiaryAdapter(breakfastList, getContext());
        binding.breakfastRV.setAdapter(breakfastAdapter);

        setDate();
        addFood();
        getDate();
        getFood();

        return binding.getRoot();
    }

    private void setDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        selectedDate = df.format(c);

        binding.currentDate.setText(selectedDate);
    }

    private void addFood() {
        binding.addBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DiarySearchFoods.class);
                i.putExtra("Linner","Breakfast");
                i.putExtra("date",selectedDate);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        binding.addLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DiarySearchFoods.class);
                i.putExtra("Linner","Lunch");
                i.putExtra("date",selectedDate);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        binding.addSnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DiarySearchFoods.class);
                i.putExtra("Linner","Snacks");
                i.putExtra("date",selectedDate);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        binding.addDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DiarySearchFoods.class);
                i.putExtra("Linner","Dinner");
                i.putExtra("date",selectedDate);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
    }

    private void getFood() {
       database.getReference().child("Diary").child(FirebaseAuth.getInstance().getUid())
               .child(selectedDate).child("Breakfast").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.exists()){
                   for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                       if(dataSnapshot.exists()){
                           Nutrition2 nutrition2 = dataSnapshot.getValue(Nutrition2.class);
                           breakfastList.add(nutrition2);
                       }
                   }
               }
               breakfastAdapter.notifyDataSetChanged();

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

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

    private  void getDate(){

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT DATE");
        MaterialDatePicker materialDatePicker = builder.build();


        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                selectedDate = materialDatePicker.getHeaderText();
                binding.currentDate.setText(selectedDate);
            }
        });
    }
}