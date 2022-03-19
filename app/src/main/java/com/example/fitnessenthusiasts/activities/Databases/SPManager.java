package com.example.fitnessenthusiasts.activities.Databases;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SPManager {

    SharedPreferences userSP;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String S_FULLNAME = "fullName";
    public static final String S_USERNAME = "username";
    public static final String S_EMAIL = "email";
    public static final String S_PHONENUMBER = "phoneNumber";
    public static final String S_PASSWORD = "password";
    public static final String S_DOB = "date";
    public static final String S_GENDER = "gender";

    public SPManager(Context _context) {
        context = _context;
        userSP = context.getSharedPreferences("loginSession", Context.MODE_PRIVATE);
        editor = userSP.edit();
    }

    public void createLoginSession(String fullName, String username, String email, String phoneNumber, String password, String age, String gender) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(S_FULLNAME, fullName);
        editor.putString(S_USERNAME, username);
        editor.putString(S_EMAIL, email);
        editor.putString(S_PHONENUMBER, phoneNumber);
        editor.putString(S_PASSWORD, password);
        editor.putString(S_DOB, age);
        editor.putString(S_GENDER, gender);

        editor.commit();

    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(S_FULLNAME, userSP.getString(S_FULLNAME, null));
        userData.put(S_USERNAME, userSP.getString(S_USERNAME, null));
        userData.put(S_EMAIL, userSP.getString(S_EMAIL, null));
        userData.put(S_PHONENUMBER, userSP.getString(S_PHONENUMBER, null));
        userData.put(S_PASSWORD, userSP.getString(S_PASSWORD, null));
        userData.put(S_DOB, userSP.getString(S_DOB, null));
        userData.put(S_GENDER, userSP.getString(S_GENDER, null));

        return userData;

    }

    public boolean checkLogin() {
        if (userSP.getBoolean(IS_LOGIN, true)) {
            return true;
        } else {
            return false;
        }
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

}
