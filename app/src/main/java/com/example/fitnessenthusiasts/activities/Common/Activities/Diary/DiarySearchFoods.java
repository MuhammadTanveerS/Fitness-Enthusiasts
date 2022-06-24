package com.example.fitnessenthusiasts.activities.Common.Activities.Diary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fitnessenthusiasts.R;

public class DiarySearchFoods extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_search_foods);
    }

    public void searchFood(View view) {
        TextView tv = findViewById(R.id.textView15);
        EditText editText = findViewById(R.id.editTextTextPersonName);
        String text = editText.getText().toString();

        tv.setText(text);
        editText.setText("");
    }
}