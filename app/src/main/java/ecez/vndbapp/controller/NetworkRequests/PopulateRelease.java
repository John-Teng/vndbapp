package ecez.vndbapp.controller.NetworkRequests;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;

import ecez.vndbapp.controller.Callbacks.DefaultCallback;
import ecez.vndbapp.model.Release;


/**
 * Created by johnteng on 2017-05-07.
 */

public class PopulateRelease extends VNDBrequest {
    private Release release;
    private DefaultCallback callback;
    private int id;
    private String releaseDate;

    public PopulateRelease (int id, String releaseDate, DefaultCallback callback) {
        this.id = id;
        this.releaseDate = releaseDate;
        this.callback = callback;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        JSONArray jsonResponse = getJSONfromRequest("get", "release", "basic,producers",
                "(vn = "+Integer.toString(id)+ " and released = \"" +releaseDate+"\")", null,callback);
        Log.d("json",jsonResponse.toString());
        if (jsonResponse == null)
            return false;

        String s = jsonResponse.toString();

        Gson gson = new Gson();
        Release [] r = gson.fromJson(s, Release[].class);
        release = r[0];

        Log.d("json2",s);
        return true;
    }

    @Override
    public void onPostExecute (Object o) {
        if (o.equals(true)) {
            callback.onSuccess(release);
        }
    }

}


