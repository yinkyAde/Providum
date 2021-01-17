package com.israel.providum.sessionmanager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "DOCUMENT";
    public static final String DOCUMENT = "text";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }


    //SAVE USER DETAILS
    public void createSession(String text) {
        editor.putString(DOCUMENT, text);
        editor.apply();

    }


    //GET USER DETAILS
    public HashMap<String, String> getUserDocument() {
        HashMap<String, String> user = new HashMap<>();
        user.put(DOCUMENT, sharedPreferences.getString(DOCUMENT, null));
        return user;
    }


}

