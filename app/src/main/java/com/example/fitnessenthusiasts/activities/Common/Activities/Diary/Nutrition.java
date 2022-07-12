package com.example.fitnessenthusiasts.activities.Common.Activities.Diary;

public class Nutrition {

    private String nix_item_id,food_name,nf_calories,serving_unit,Linner;

    public Nutrition(String nix_item_id, String food_name, String nf_calories, String serving_unit) {
        this.nix_item_id = nix_item_id;
        this.food_name = food_name;
        this.nf_calories = nf_calories;
        this.serving_unit = serving_unit;
    }

    public Nutrition() {
    }

    public String getNix_item_id() {
        return nix_item_id;
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

    public String getLinner() {
        return Linner;
    }

    public void setLinner(String linner) {
        Linner = linner;
    }
}
