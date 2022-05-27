package com.example.fitnessenthusiasts.activities.HelperClasses.Models;

public class CommunityInfoModel {
    private String trainer,type,ageLimit,description1,description2,image;
    private double price;

    public CommunityInfoModel() {
    }

    public CommunityInfoModel(String trainer, String type, String ageLimit, String description1,String description2, String image, double price) {
        this.trainer = trainer;
        this.type = type;
        this.ageLimit = ageLimit;
        this.description1 = description1;
        this.description2 = description2;
        this.image = image;
        this.price = price;
    }



    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(String ageLimit) {
        this.ageLimit = ageLimit;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }
}
