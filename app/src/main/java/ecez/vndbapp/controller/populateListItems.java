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
        //temporary fucntion to populate an arraylist, in the future, this will be handled in the API parser class
//        list.add(new listItem("#1 - Muv-Luv Alternative", "9.21 (excellent)","Very Long (> 50 hours)", R.mipmap.ic_launcher));
//        list.add(new listItem("#2 - Steins;Gate", "9.02 (excellent)","Long (30 - 50 hours)",  R.mipmap.ic_launcher));
//        list.add(new listItem("#3 - Baldr Sky Dive2 'Recordare'", "8.83 (very good)","Very Long (> 50 hours)", R.mipmap.ic_launcher));
//        list.add(new listItem("#4 - White Album 2 ~Closing Chapter~", "8.77 (very good)","Very Long (> 50 hours)",  R.mipmap.ic_launcher));
//        list.add(new listItem("#5 - Umineko no Naku Koro ni", "8.76 (very good)","Very Long (> 50 hours)",  R.mipmap.ic_launcher));
        Gson gson = new Gson();
        String dataString = jsonString.substring(8,jsonString.length()); //Removes the prepending "result" keyword in the json response
        Log.d("Modified Response 1",dataString);
//        Log.d("Modified Response 1",dataString.substring(0,3000));
//        Log.d("Modified Response 2",dataString.substring(3000,6000));
//        Log.d("Modified Response 3",dataString.substring(6000,9000));
//        Log.d("Modified Response 3",dataString.substring(9000,12000));
//        Log.d("Modified Response 4",dataString.substring(12000,13351));
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
