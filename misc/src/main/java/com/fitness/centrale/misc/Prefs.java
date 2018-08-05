package com.fitness.centrale.misc;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by remy on 16/03/17.
 */

public class Prefs {


    public static SharedPreferences prefs;
    private static String username;

    public static void initPreferencesManager(Context ctx){
        if (prefs ==null)
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
    }


    public static String getToken(){
        return prefs.getString(Constants.TOKEN, "none");
    }

    public static void setToken(String token){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.TOKEN, token);
        editor.apply();
    }

    public static void removeToken(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(Constants.TOKEN);
        editor.apply();

    }

    public static void setUsername(String username) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.LOGIN, username);
        editor.apply();
    }
}

