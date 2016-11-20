package ecez.vndbapp.controller;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import ecez.vndbapp.model.character;
import ecez.vndbapp.model.detailsData;
import ecez.vndbapp.model.listItem;
import ecez.vndbapp.model.novelScreenShot;
import ecez.vndbapp.model.serverRequest;
import ecez.vndbapp.view.vndatabaseapp;

/**
 * Created by Teng on 11/20/2016.
 */
public class populateCharacters extends Thread{

        private String jsonString;
        private ArrayList<character> characters;
        private novelScreenShot[] screens;
        private int vnID;

        public populateCharacters(int vnID) {
            this.vnID = vnID;
        }
        @Override
        public void run () {
            if (vndatabaseapp.loggedIn == true)
                jsonString = serverRequest.writeToServer("get", "character", "basic,meas,details,traits,vns", "(vn = "+Integer.toString(vnID)+")",null);
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

            Log.d("json2",f);
            character[] l = gson.fromJson(jsonResponse.toString(),character[].class);
            this.characters = new ArrayList<character>(Arrays.asList(l));
        }
        public ArrayList<character> getCharacters() {
            return this.characters;
        }


    }

