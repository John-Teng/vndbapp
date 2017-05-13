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
    private Release [] releases;
    private DefaultCallback callback;
    private int id;
    private String releaseDate, type;

    public PopulateRelease (int id, String type, String releaseDate, DefaultCallback callback) {
        this.id = id;
        this.releaseDate = releaseDate;
        this.callback = callback;
        this.type = type;
    }

    public PopulateRelease (int id, String type, DefaultCallback callback) {
        this.id = id;
        this.type = type;
        this.callback = callback;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        StringBuilder filters = new StringBuilder("(vn = "+Integer.toString(id));
        if (releaseDate != null )
            filters.append(" and released = \"" +releaseDate+"\")");
        else
            filters.append("dank?");

        JSONArray jsonResponse = getJSONfromRequest("get", "release", type, filters.toString(), null,callback);
        Log.d("json",jsonResponse.toString());
        if (jsonResponse == null)
            return false;

        String s = jsonResponse.toString();

        Gson gson = new Gson();
        releases = gson.fromJson(s, Release[].class);

        Log.d("json2",s);
        return true;
    }

    @Override
    public void onPostExecute (Object o) {
        if (o.equals(true)) {
            callback.onSuccess(releases);
        }
    }

}


