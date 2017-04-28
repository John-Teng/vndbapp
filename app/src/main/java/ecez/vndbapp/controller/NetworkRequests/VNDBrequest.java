package ecez.vndbapp.controller.NetworkRequests;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ecez.vndbapp.controller.Callbacks.AsyncTaskCallback;
import ecez.vndbapp.controller.vndatabaseapp;
import ecez.vndbapp.model.Constants;
import ecez.vndbapp.model.Error;
import ecez.vndbapp.model.ServerRequest;

/**
 * Created by johnteng on 2017-04-27.
 */

public abstract class VNDBrequest extends AsyncTask{
    Gson gson = new Gson();


    protected JSONArray getJSONfromRequest (String queryType, String queryTarget,String flags, String filters, String options, AsyncTaskCallback callback) {
        String jsonString;

        if (vndatabaseapp.loggedIn == true)
            jsonString = ServerRequest.getInstance().writeToServer(queryType, queryTarget, flags, filters, options);
        else {
            callback.onFailure(null, Constants.serverNotLoggedInError);
            return null;
        }
        Log.d("JSON Response",jsonString);


        if (jsonString.substring(0,4).equals("error")) {
            Error error = gson.fromJson(jsonString.toString(), Error.class);
            callback.onFailure(error,Constants.serverErrorResponse);
            return null;
        }

        jsonString = jsonString.substring(8,jsonString.length()); //Removes the prepending "result" keyword in the json response

        int numberOfResponses;
        JSONArray jsonResponse;
        try {
            JSONObject returnObject = new JSONObject(jsonString);
            numberOfResponses = returnObject.getInt("num");
            jsonResponse = returnObject.getJSONArray("items");
            Log.d("items json",jsonResponse.toString());
            Log.d("number of itmes",Integer.toString(numberOfResponses));
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onFailure(null,Constants.jsonSerializationError);
            return null;
        }
        return jsonResponse;
    }

}
