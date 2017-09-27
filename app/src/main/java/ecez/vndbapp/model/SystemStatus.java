package ecez.vndbapp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ecez.vndbapp.controller.CustomObserver;
import ecez.vndbapp.controller.ObserverSubject;

/**
 * Created by johnteng on 2017-09-19.
 */

public class SystemStatus implements ObserverSubject{
    private static SystemStatus ourInstance = new SystemStatus();
    public boolean connectedToServer;
    public boolean loggedIn;
    public HashMap<Integer,Trait> traitsMap = null;
    public HashMap<Integer,Tag> tagsMap = null;
    public boolean blockNSFW;
    public int spoilerLevel;
    public int displayMethod;
    private List<CustomObserver> observers = new ArrayList<>();

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
                SystemStatus.getInstance().blockNSFW = sharedPreferences.getBoolean("nsfw_toggle", false);
                notifyObservers();
                Log.d("nsfw_toggle", Boolean.toString(SystemStatus.getInstance().blockNSFW));
                break;
            case "display_method":
                SystemStatus.getInstance().displayMethod = Integer.valueOf(sharedPreferences.getString("display_method", "0"));
                notifyObservers();
                Log.d("display_method", Integer.toString(SystemStatus.getInstance().displayMethod));
                break;
            case "spoiler_level":
                SystemStatus.getInstance().spoilerLevel = Integer.valueOf(sharedPreferences.getString("spoiler_level", "0"));
                Log.d("spoiler_level", Integer.toString(SystemStatus.getInstance().spoilerLevel));
                break;
            default:
                SystemStatus.getInstance().blockNSFW = sharedPreferences.getBoolean("nsfw_toggle", false);
                SystemStatus.getInstance().spoilerLevel = Integer.valueOf(sharedPreferences.getString("spoiler_level", "0"));
                SystemStatus.getInstance().displayMethod = Integer.valueOf(sharedPreferences.getString("display_method", "0"));
                Log.d("nsfw_toggle", Boolean.toString(SystemStatus.getInstance().blockNSFW));
                Log.d("spoiler_level", Integer.toString(SystemStatus.getInstance().spoilerLevel));
                Log.d("display_method", Integer.toString(SystemStatus.getInstance().displayMethod));
                break;
        }



    }

    @Override
    public void registerObserver(CustomObserver customObserver) {
        observers.add(customObserver);
    }

    @Override
    public void removeObserver(CustomObserver customObserver) {
        observers.remove(customObserver);
    }

    @Override
    public void notifyObservers() {
        for (CustomObserver o : observers) {
            o.onDataChanged();
        }
    }



}
