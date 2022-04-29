package com.example.fitnessenthusiasts.activities.HelperClasses;

import com.example.fitnessenthusiasts.R;

public class Exercises {

    static int exercise;

    static int jumping_jacks = R.raw.jumpingjacks;
    static int jumping_squats = R.raw.jumping_squats;
    static int cobra_stretches = R.raw.cobrastretch;
    static int punches = R.raw.punches;


    public static int ex(String name){

        switch (name){

            case "Jumping Jacks":
                exercise= jumping_jacks;
                break;
            case "Jumping Squats":
                exercise = jumping_squats;
                break;
            case "Cobra Stretches":
                exercise = cobra_stretches;
                break;
            case "Punches":
                exercise = punches;
                break;
        }

        return exercise;
    }

}
