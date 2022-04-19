package com.example.fitnessenthusiasts.activities.HelperClasses.Models;

public class WorkoutsModel {
    String name, diff, bg,points;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;

    public WorkoutsModel(){

    }

    public WorkoutsModel(String name, String diff, String bg, String points) {
        this.name = name;
        this.diff = diff;
        this.bg = bg;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public String getDiff() {
        return diff;
    }

    public String getBg() {
        return bg;
    }

    public String getPoints() {
        return points;
    }
}
