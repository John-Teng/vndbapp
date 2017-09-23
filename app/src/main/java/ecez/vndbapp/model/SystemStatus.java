package ecez.vndbapp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by johnteng on 2017-09-19.
 */

public class SystemStatus {
    private static SystemStatus ourInstance = new SystemStatus();
    public boolean connectedToServer;
    public boolean loggedIn;
    public HashMap<Integer,Trait> traitsMap = null;
    public HashMap<Integer,Tag> tagsMap = null;
    public boolean nsfwAllowed = false;
    public int spoilerLevel;
    public int displayMethod;


    public static SystemStatus getInstance() {
        return ourInstance;
    }

    public synchronized  void loadPreferences (Context context) {
        loadPreferences(context,"");
    }

    public synchronized void loadPreferences(Context context, String key) {
        if (key == null)
            return;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        switch (key) {
            case "nsfw_toggle":
                SystemStatus.getInstance().nsfwAllowed = sharedPreferences.getBoolean("nsfw_toggle", false);
                Log.d("nsfw_toggle", Boolean.toString(SystemStatus.getInstance().nsfwAllowed));
                break;
            case "display_method":
                SystemStatus.getInstance().displayMethod = Integer.valueOf(sharedPreferences.getString("display_method", "0"));
                Log.d("display_method", Integer.toString(SystemStatus.getInstance().displayMethod));
                break;
            case "spoiler_level":
                SystemStatus.getInstance().spoilerLevel = Integer.valueOf(sharedPreferences.getString("spoiler_level", "0"));
                Log.d("spoiler_level", Integer.toString(SystemStatus.getInstance().spoilerLevel));
                break;
            default:
                SystemStatus.getInstance().nsfwAllowed = sharedPreferences.getBoolean("nsfw_toggle", false);
                SystemStatus.getInstance().spoilerLevel = Integer.valueOf(sharedPreferences.getString("spoiler_level", "0"));
                SystemStatus.getInstance().displayMethod = Integer.valueOf(sharedPreferences.getString("display_method", "0"));
                Log.d("nsfw_toggle", Boolean.toString(SystemStatus.getInstance().nsfwAllowed));
                Log.d("spoiler_level", Integer.toString(SystemStatus.getInstance().spoilerLevel));
                Log.d("display_method", Integer.toString(SystemStatus.getInstance().displayMethod));
                break;
        }



    }


}
