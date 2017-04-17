package ecez.vndbapp.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ecez.vndbapp.controller.Callbacks.ListCallback;
import ecez.vndbapp.model.ListItem;
import ecez.vndbapp.model.ServerRequest;

/**
 * Created by Teng on 10/22/2016.
 */
public class PopulateListItems extends AsyncTask {
    private List<ListItem> list;
    private String jsonString;
    private int page;
    private final int resultPerPage = 25;
    private String sortParam;
    public ListCallback callback;

    public PopulateListItems(int page, String sortParam) {
        this.list = new ArrayList<>();
        this.page = page;
        this.sortParam = sortParam;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        if (vndatabaseapp.loggedIn == true)
            jsonString = ServerRequest.getInstance().writeToServer("get", "vn", "basic,stats,details", "(released > \"1945\")", "{\"page\":"+Integer.toString(page)+",\"results\":"+resultPerPage+",\"sort\":\""+sortParam+"\",\"reverse\":true}");
        else {
            Log.d("Connection failure", "Cannot connect to server");
            return false;
        }
        Log.d("JSON Response",jsonString);

        Gson gson = new Gson();
        String dataString = jsonString.substring(8,jsonString.length()); //Removes the prepending "result" keyword in the json response
        int numberOfResponses;
        JSONArray jsonResponse;
        try {
            JSONObject returnObject = new JSONObject(dataString);
            numberOfResponses = returnObject.getInt("num");
            jsonResponse = returnObject.getJSONArray("items");
            Log.d("number of itmes",Integer.toString(numberOfResponses));
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        Log.d("json",jsonResponse.toString());
        ListItem[] l = gson.fromJson(jsonResponse.toString(),ListItem[].class);
        list = new ArrayList<>(Arrays.asList(l));
        int y = (resultPerPage*page-(resultPerPage-1));
        for (ListItem x:list) {
            x.setRank(y);
            y ++;
        }
        callback.onSuccess(list);

        return true;
    }

    @Override
    protected void onPostExecute(Object o) {
        if (o.equals(true))
            callback.onSuccessUI();
        else
            callback.onFailureUI();

    }

}
