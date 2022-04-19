package com.example.fitnessenthusiasts.activities.HelperClasses.Models;

public class CategoriesGet {
    String catName;
    int catImage,catBgImage;

    public CategoriesGet(){

    }


    public CategoriesGet(String catName, int catImage, int catBgImage) {
        this.catName = catName;
        this.catImage = catImage;
        this.catBgImage = catBgImage;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getCatImage() {
        return catImage;
    }

    public void setCatImage(int catImage) {
        this.catImage = catImage;
    }

    public int getCatBgImage() {
        return catBgImage;
    }

    public void setCatBgImage(int catBgImage) {
        this.catBgImage = catBgImage;
    }
}
