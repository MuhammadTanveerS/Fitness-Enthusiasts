package com.example.fitnessenthusiasts.activities.HelperClasses;

public class WorkoutsModel {
    String name, diff, bg,points;

    public WorkoutsModel(){}

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
