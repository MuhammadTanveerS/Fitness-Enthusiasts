package com.example.fitnessenthusiasts.activities.HelperClasses;

public class Badges {
    static String[] badges;

    static String[] pregnantWomen = {"https://firebasestorage.googleapis.com/v0/b/fitness-enthusiasts.appspot.com/o/badges%2FpregnantWomenCommunity%2FPregCom1.png?alt=media&token=65674ebb-719b-41dc-874f-06a7523d9560",
            "https://firebasestorage.googleapis.com/v0/b/fitness-enthusiasts.appspot.com/o/badges%2FpregnantWomenCommunity%2FPregCom2.png?alt=media&token=7ade7df5-2223-4d4f-b961-ed2f9383d5ce",
            "https://firebasestorage.googleapis.com/v0/b/fitness-enthusiasts.appspot.com/o/badges%2FpregnantWomenCommunity%2FPregCom3.png?alt=media&token=53053f61-9200-4cb9-aa62-ef9d1c50f697"};


    public static String[] com(String name){
        switch (name){

            case "Community 2":
                badges = pregnantWomen;
                break;

        }
        return  badges;
    }
}
