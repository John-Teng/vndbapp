package ecez.vndbapp.controller;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import ecez.vndbapp.model.listItem;
import ecez.vndbapp.model.serverRequest;
import ecez.vndbapp.view.vndatabaseapp;

/**
 * Created by Teng on 10/22/2016.
 */
public class populateListItems extends Thread {
    private ArrayList<listItem> list;
    private String jsonString;
    private int page;
    private final int resultPerPage = 25;

    public populateListItems (ArrayList<listItem> list, int page) {
        this.list = list;
        this.page = page;
    }
    @Override
    public void run () {
        if (vndatabaseapp.loggedIn == true)
            jsonString = serverRequest.writeToServer("get", "vn", "basic,stats,details", "(released > \"1945\")", "{\"page\":"+Integer.toString(page)+",\"results\":"+resultPerPage+",\"sort\":\"rating\",\"reverse\":true}");
        else
            Log.d("Connection failure", "Cannot connect to server");
        Log.d("JSON Response",jsonString);

        Gson gson = new Gson();
        String dataString = jsonString.substring(8,jsonString.length()); //Removes the prepending "result" keyword in the json response
        Log.d("Modified Response 1",dataString);
        int numberOfResponses = 0;
        JSONArray jsonResponse = null;
        try {
            JSONObject returnObject = new JSONObject(dataString);
            numberOfResponses = returnObject.getInt("num");
            jsonResponse = returnObject.getJSONArray("items");
            Log.d("items json",jsonResponse.toString());
            Log.d("number of itmes",Integer.toString(numberOfResponses));
        } catch (JSONException e) {e.printStackTrace();}
        Log.d("json",jsonResponse.toString());
        listItem[] l = gson.fromJson(jsonResponse.toString(),listItem[].class);
        this.list = new ArrayList<listItem>(Arrays.asList(l));
        int y = (resultPerPage*page-(resultPerPage-1));
        for (listItem x:this.list) {
            x.setRank(y);
            y ++;
        }
    }
    public ArrayList<listItem> getList () {
        return this.list;
    }
}
