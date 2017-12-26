package com.b.aman.atable.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by aman on 21/12/17.
 */

public class CacheHelper {


    public static void setCurrentLanguage(Context application, String language) {

        SharedPreferences sharedPreferences = application.getSharedPreferences("ApplicationData", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", language);
        editor.commit();
    }

    public static String getCurrentLanguage (Context application){
        SharedPreferences sharedPreferences = application.getSharedPreferences("ApplicationData", 0);
        String language = sharedPreferences.getString("language", "english");
        return language;
    }

}
