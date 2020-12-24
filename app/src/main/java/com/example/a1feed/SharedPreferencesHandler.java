package com.example.a1feed;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHandler {

    public static void saveSharedPreferences(Context context, String country) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Keys.SHARED_PREFERENCES_PREFIX, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        try {
            editor.clear();
            editor.putString(Keys.SHARED_PREFERENCES_COUNTRY, country);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String loadSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Keys.SHARED_PREFERENCES_PREFIX, Context.MODE_PRIVATE);
        String country = sharedPreferences.getString(Keys.SHARED_PREFERENCES_COUNTRY, null);
        return country;
    }

    public static boolean exists(Context context) {
        return context.getSharedPreferences(Keys.SHARED_PREFERENCES_PREFIX, Context.MODE_PRIVATE).contains(Keys.SHARED_PREFERENCES_COUNTRY);
    }
}
