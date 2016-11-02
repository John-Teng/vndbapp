package ecez.vndbapp.controller;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ecez.vndbapp.model.detailsData;
import ecez.vndbapp.model.novelScreenShot;
import ecez.vndbapp.model.serverRequest;
import ecez.vndbapp.view.vndatabaseapp;

/**
 * Created by Teng on 10/27/2016.
 */
public class populateNovelDetails extends Thread{

        private String jsonString;
        private detailsData data;
        private novelScreenShot [] screens;
        private int id;

        public populateNovelDetails(int id) {
            this.id = id;
        }
        @Override
        public void run () {
            if (vndatabaseapp.loggedIn == true)
                jsonString = serverRequest.writeToServer("get", "vn", "basic,stats,details,screens", "(id = "+Integer.toString(id)+")",null);
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
            String s = jsonResponse.toString();
            String f = s.substring(1,s.length()-1);//Removes the square braces from the response

            JSONObject j = null;
            JSONArray jsonScreens = null;
            try {
                j = new JSONObject(f);
                jsonScreens = j.getJSONArray("screens");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            screens = gson.fromJson(jsonScreens.toString(),novelScreenShot[].class);
            Log.d("Screens",jsonScreens.toString());

            Log.d("json2",f);
            this.data = gson.fromJson(f,detailsData.class);

        }
        public detailsData getData () {
            return this.data;
        }

    public novelScreenShot[] getScreens () {
        return this.screens;
    }

}
