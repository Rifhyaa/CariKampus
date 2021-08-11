package id.carikampus.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    static final String KEY_ID_USER = "id_user";
    static final String KEY_TEMP_ID_KAMPUS = "id_kampus";
    static final String KEY_NAME_USER = "name_user";
    static final String KEY_USERNAME_USER = "username_user";
    static final String KEY_PASSWORD_USER = "password_user";
    static final String KEY_LOGGED_IN_STATUS = "status_logged_in";
    static final String KEY_INTRODUCTION_STATUS = "status_introduction";

    private static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setIdUser(Context context, int id) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(KEY_ID_USER, id);
        editor.apply();
    }

    public static void setIdKampus(Context context, int id) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(KEY_TEMP_ID_KAMPUS, id);
        editor.apply();
    }

    public static void setNameUser(Context context, String name) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_NAME_USER, name);
        editor.apply();
    }

    public static void setUsernameUser(Context context, String username) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USERNAME_USER, username);
        editor.apply();
    }

    public static void setPasswordUser(Context context, String password) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_PASSWORD_USER, password);
        editor.apply();
    }

    public static void setLoggedInStatus(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(KEY_LOGGED_IN_STATUS, status);
        editor.apply();
    }

    public static void setIntroductionStatus(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(KEY_INTRODUCTION_STATUS, status);
        editor.apply();
    }

    public static void clearPreferences(Context context) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.clear().commit();
    }

    public static int getIdUser(Context context){
        return getSharedPreference(context).getInt(KEY_ID_USER,0);
    }

    public static int getIdKampus(Context context){
        return getSharedPreference(context).getInt(KEY_TEMP_ID_KAMPUS,0);
    }

    public static String getNameUser(Context context){
        return getSharedPreference(context).getString(KEY_NAME_USER,"null");
    }

    public static String getUsernameUser(Context context){
        return getSharedPreference(context).getString(KEY_USERNAME_USER,null);
    }

    public static String getPasswordUser(Context context){
        return getSharedPreference(context).getString(KEY_PASSWORD_USER,null);
    }

    public static boolean getLoggedInStatus(Context context){
        return getSharedPreference(context).getBoolean(KEY_LOGGED_IN_STATUS,false);
    }

    public static boolean getIntroductionStatus(Context context){
        return getSharedPreference(context).getBoolean(KEY_INTRODUCTION_STATUS,false);
    }
}
