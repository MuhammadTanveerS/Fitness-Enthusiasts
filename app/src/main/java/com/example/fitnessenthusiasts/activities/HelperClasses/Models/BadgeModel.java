package com.example.fitnessenthusiasts.activities.HelperClasses.Models;

public class BadgeModel {
    private  String name,details,image,type;

    public BadgeModel() {
    }

    public BadgeModel(String name, String details, String image, String type) {
        this.name = name;
        this.details = details;
        this.image = image;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getImage() {
        return image;
    }
}
