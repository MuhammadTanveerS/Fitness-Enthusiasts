package com.example.fitnessenthusiasts.activities.Common.Activities.Diary;

public class Nutrition2 {
    private String  food_name, serving_unit, nf_calories,serving_qty;
    private float serving_weight_grams, nf_total_fat, nf_total_carbohydrate,nf_protein;

    public Nutrition2(String food_name, String serving_unit, String nf_calories, String serving_qty, float serving_weight_grams, float nf_total_fat, float nf_total_carbohydrate, float nf_protein) {
        this.food_name = food_name;
        this.serving_unit = serving_unit;
        this.nf_calories = nf_calories;
        this.serving_qty = serving_qty;
        this.serving_weight_grams = serving_weight_grams;
        this.nf_total_fat = nf_total_fat;
        this.nf_total_carbohydrate = nf_total_carbohydrate;
        this.nf_protein = nf_protein;
    }

    public Nutrition2() {
    }

    public String getFood_name() {
        return food_name;
    }

    public String getServing_unit() {
        return serving_unit;
    }

    public String getNf_calories() {
        return nf_calories;
    }

    public String getServing_qty() {
        return serving_qty;
    }

    public float getServing_weight_grams() {
        return serving_weight_grams;
    }

    public float getNf_total_fat() {
        return nf_total_fat;
    }

    public float getNf_total_carbohydrate() {
        return nf_total_carbohydrate;
    }

    public float getNf_protein() {
        return nf_protein;
    }
}
