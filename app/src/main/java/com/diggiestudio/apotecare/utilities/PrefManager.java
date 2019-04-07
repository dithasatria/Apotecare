package com.diggiestudio.apotecare.utilities;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by DITHA on 28/11/2017.
 */

public class PrefManager {

    //public final String KEY_ID_USER = "idUser";
    //public final String KEY_DISPLAY_NAME = "displayName";
    //public final String KEY_EMAIL_USER = "emailUser";
    public final String KEY_IS_LOGIN = "isLoggedIn";

    public PrefManager() {
    }

    public void saveString(Context context, String key, String value){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(key, value)
                .apply();
    }

    public String getString(Context context, String key){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(key, "");
    }


    public void deleteData(Context context, String key){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .clear()
                .apply();
    }

    public void saveBoolean(Context context, String key, boolean value){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(key, value)
                .apply();
    }

    public boolean getBoolean(Context context, String key){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(key, false);
    }


    public void deleteDataBoolean(Context context, String key){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .clear()
                .apply();
    }
}
