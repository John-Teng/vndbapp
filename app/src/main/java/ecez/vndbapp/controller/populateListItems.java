package ecez.vndbapp.controller;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import ecez.vndbapp.model.ListItem;
import ecez.vndbapp.model.ServerRequest;
import ecez.vndbapp.view.vndatabaseapp;

/**
 * Created by Teng on 10/22/2016.
 */
public class PopulateListItems extends Thread {/// MOVE THIS CLASS TO AN ASYNC TASK
    private ArrayList<ListItem> list;
    private String jsonString;
    private int page;
    private final int resultPerPage = 25;
    private String sortParam;

    public PopulateListItems(ArrayList<ListItem> list, int page, String sortParam) {
        this.list = list;
        this.page = page;
        this.sortParam = sortParam;
    }
    @Override
    public void run () {
        if (vndatabaseapp.loggedIn == true)
            jsonString = ServerRequest.getInstance().writeToServer("get", "vn", "basic,stats,details", "(released > \"1945\")", "{\"page\":"+Integer.toString(page)+",\"results\":"+resultPerPage+",\"sort\":\""+sortParam+"\",\"reverse\":true}");
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
        ListItem[] l = gson.fromJson(jsonResponse.toString(),ListItem[].class);
        this.list = new ArrayList<ListItem>(Arrays.asList(l));
        int y = (resultPerPage*page-(resultPerPage-1));
        for (ListItem x:this.list) {
            x.setRank(y);
            y ++;
        }
    }
    public ArrayList<ListItem> getList () {
        return this.list;
    }
}
