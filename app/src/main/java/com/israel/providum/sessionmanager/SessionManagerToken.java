package com.israel.providum.sessionmanager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.israel.providum.Dashboard;
import com.israel.providum.auth.LoginScreen;

import java.util.HashMap;

public class SessionManagerToken {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private  static  final String PREF_NAME = "LOGIN";
    private  static  final String LOGIN = "IS LOGIN";
    public  static  final  String NAME ="NAME";
    public  static  final  String USER_ID ="USER_ID";
    public  static  final  String TOKEN ="TOKEN";

    public SessionManagerToken(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }


    //SAVE USER DETAILS
    public void createSession(String token) {
        editor.putBoolean(LOGIN, true);
        editor.putString(TOKEN, token);
        editor.apply();

    }

    //CHECK IF USER IS ALREADY LOGGED IN
    public boolean isLoggin(){
        return  sharedPreferences.getBoolean(LOGIN,false);
    }

    //GET USER DETAILS
    public HashMap<String, String> getUserDocument() {
        HashMap<String, String> user = new HashMap<>();
        user.put(TOKEN, sharedPreferences.getString(TOKEN, null));
        return user;
    }

    //LOGOUT
    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginScreen.class);
        context.startActivity(i);
        ((Dashboard)context).finish();
    }

}


