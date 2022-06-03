package com.example.fitnessenthusiasts.activities.Databases;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";

    public Session(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(UserHelperClass user) {
        //save session of user whenever user is logged in
        String FULLNAME = user.getFullName();
        String PROFILEPICTURE = user.getProfilePhoto();


        editor.putString("FULLNAME",FULLNAME);
        editor.putString("PROFILEPICTURE",PROFILEPICTURE);
        editor.apply();
    }

    public String getName() {
        return sharedPreferences.getString("FULLNAME","");
    }

    public String getPhoto() {
        return sharedPreferences.getString("PROFILEPICTURE","");
    }

}
