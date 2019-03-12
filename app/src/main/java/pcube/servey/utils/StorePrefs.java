package pcube.servey.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class StorePrefs {

    public static final String PREFS_USER_JSON = "user_json";
    public static final String PREFS_USER_TYPE ="type" ;
    public static final String PREFS_USER_TOKEN ="token" ;
    public static final String PREFS_USER_AVATAR_IMAGE = "avatar";
    public static final String PREFS_USER_COVER_IMAGE = "cover";
    public static final String PREFS_USER_ID ="id" ;
    public static final String PREFS_USER_NAME ="name" ;
    public static final String PREFS_USER_NAME_name ="ffff" ;
    public static final String PREFS_INVOICE_DATA ="invoicedata" ;
    public static final String AMA ="ama" ;
    public static final String answerd ="answerd" ;
    public static final String ignore ="ignored" ;
    public static final String PREFS_ENABLESOUND = "sound";

    public static void setDefaults(String key, String value, Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }




    public static String getDefaults(String key, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }




    public static void clearDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.clear();
        editor.commit();
        Log.d("CLEARED PREFERENCES : ", "KEY :=====> " + key);
    }

    public static void clearDefaultsSpecificData(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key).apply();

    }

    public static void clearAllDefaults(Context context)
    {


        clearDefaults(PREFS_USER_JSON, context);
        clearDefaults(PREFS_USER_TYPE, context);
        clearDefaults(PREFS_USER_TOKEN, context);
        clearDefaults(PREFS_USER_AVATAR_IMAGE, context);
        clearDefaults(PREFS_USER_COVER_IMAGE, context);
        clearDefaults(PREFS_USER_ID, context);
        clearDefaults(PREFS_USER_NAME, context);
        clearDefaults(PREFS_USER_NAME_name, context);
        clearDefaults( PREFS_INVOICE_DATA, context);
        clearDefaults( AMA, context);
        clearDefaults( ignore, context);
        clearDefaults( answerd, context);

        Log.e("CLEARED All PREFERENCES", " =====> ALL PREFS CLEARED");
    }



}
