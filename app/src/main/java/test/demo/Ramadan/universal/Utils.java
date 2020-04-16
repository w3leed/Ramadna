package test.demo.Ramadan.universal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.ArrayList;

/**
 * Created by Shahzad Ahmad on 30-Jun-15.
 */
public class Utils {
    public static ArrayList<Constant> list = new ArrayList<Constant>();

    public static String Banner="ca-app-pub-8912715782698177/5711853048";
    public static String Interstitial="ca-app-pub-8912715782698177/";

    public  static ArrayList<Constants> CategoryName=new ArrayList<Constants>();

    public static String getPreferences(String key, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userName = sharedPreferences.getString(key, "");
        return userName;
    }

    public static boolean savePreferences(String key, String value,
                                          Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        return true;
    }

    public static int getPreferencesInt(String key, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int userName = sharedPreferences.getInt(key, 0);
        return userName;
    }

    public static boolean savePreferencesInt(String key, int value,
                                             Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }
}
