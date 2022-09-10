package com.example.fitnessenthusiasts.activities.HelperClasses;

import com.example.fitnessenthusiasts.R;

public class Exercises {

    static int exercise;

    static int jumping_jacks = R.raw.jumpingjacks;
    static int jumping_squats = R.raw.jumping_squats;
    static int cobra_stretches = R.raw.cobrastretch;
    static int punches = R.raw.punches;
    static int staggered_push = R.raw.staggered_push;
    static int frogpress = R.raw.frogpress;
    static int pushup = R.raw.pushup;
    static int seatedabscircle = R.raw.seatedabscircle;
    static int squatreach = R.raw.squatreach;


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
            case "Staggered PushUp":
                exercise = staggered_push;
                break;
            case "Frog Press":
                exercise = frogpress;
                break;
            case "PushUp":
                exercise = pushup;
                break;
            case "Seated Abs Circle":
                exercise = seatedabscircle;
                break;
            case "Squat Reach":
                exercise = squatreach;
                break;
        }

        return exercise;
    }

}
