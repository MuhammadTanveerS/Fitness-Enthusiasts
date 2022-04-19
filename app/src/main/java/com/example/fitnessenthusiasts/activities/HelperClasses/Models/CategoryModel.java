package com.example.fitnessenthusiasts.activities.HelperClasses.Models;

import android.widget.RelativeLayout;

public class CategoryModel {

    String catName;
    int catImage, catBgImage;

    public CategoryModel(String catName, int catImage, int catBgImage) {
        this.catName = catName;
        this.catImage = catImage;
        this.catBgImage = catBgImage;
    }


    public String getCatName() {
        return catName;
    }

    public int getCatImage() {
        return catImage;
    }


    public int getCatBgImage() {
        return catBgImage;
    }
}
