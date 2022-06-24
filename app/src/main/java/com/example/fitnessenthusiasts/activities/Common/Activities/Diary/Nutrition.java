package com.example.fitnessenthusiasts.activities.Common.Activities.Diary;

public class Nutrition {
    private String nix_brand_id,food_name,nf_calories,serving_unit;

    public Nutrition(String nix_brand_id, String food_name, String nf_calories, String serving_unit) {
        this.nix_brand_id = nix_brand_id;
        this.food_name = food_name;
        this.nf_calories = nf_calories;
        this.serving_unit = serving_unit;
    }

    public Nutrition() {
    }

    public String getNix_brand_id() {
        return nix_brand_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public String getNf_calories() {
        return nf_calories;
    }

    public String getServing_unit() {
        return serving_unit;
    }
}
