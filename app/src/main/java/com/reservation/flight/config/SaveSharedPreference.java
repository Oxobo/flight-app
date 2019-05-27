package com.reservation.flight.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    public static final String LOGGED_IN_PREF = "logged_in_status";
    public static final String LOGGED_IN_USER = "logged_in_user";

    private SaveSharedPreference() {
        throw new IllegalStateException("Utility class");
    }

    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getUsername(Context context) {
        if (getLoggedStatus(context)) {
            return getPreferences(context).getString(LOGGED_IN_USER, "NothingFound");
        }
        return null;
    }

    public static void setLoggedIn(Context context, String username, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.putString(LOGGED_IN_USER, username);
        editor.apply();
    }

    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }
}