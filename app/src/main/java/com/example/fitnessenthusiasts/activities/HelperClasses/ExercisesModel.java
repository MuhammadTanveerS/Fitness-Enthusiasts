package com.example.fitnessenthusiasts.activities.HelperClasses;

public class ExercisesModel {
    String name,time;

    public ExercisesModel(){}

    public ExercisesModel(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
