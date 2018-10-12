package habit.tracker.habittracker;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class MySharedPreference {
    public final static  String MY_PREFS = "mypre";

    public static void saveUser(Context context, String userId, String username){
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();
        editor.putString("userId", userId);
        editor.putString("username", username);
        editor.apply();
    }

    public static String getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        String restoredText = prefs.getString("username", null);
        if (restoredText != null) {
            String userId = prefs.getString("userId", null);
            return userId;
        }
        return null;
    }
}
