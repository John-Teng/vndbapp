package ecez.vndbapp.controller.NetworkRequests;

import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ecez.vndbapp.controller.Callbacks.ListCallback;
import ecez.vndbapp.model.Character;

/**
 * Created by Teng on 11/20/2016.
 */
public class PopulateCharacters extends VNDBrequest{

    private int vnID;
    public ListCallback callback;

    public PopulateCharacters(int vnID) {
        this.vnID = vnID;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        JSONArray jsonResponse = getJSONfromRequest("get", "character", "basic,meas,details,traits,vns", "(vn = "+Integer.toString(vnID)+")",null, callback);
        if (jsonResponse == null) {
            return false;
        }
        Log.d("json",jsonResponse.toString());
        String s = jsonResponse.toString();
        String f = s.substring(1,s.length()-1);//Removes the square braces from the response

        Log.d("json2",f);
        Character[] characters = gson.fromJson(jsonResponse.toString(),Character[].class);
        callback.returnList(characters);
        return true;
    }

    @Override
    public void onPostExecute (Object o) {
        if (o.equals(true)) {
            callback.onSuccessUI();
        }
    }



}

