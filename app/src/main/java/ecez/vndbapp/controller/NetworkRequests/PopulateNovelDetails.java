package ecez.vndbapp.controller.NetworkRequests;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ecez.vndbapp.controller.Callbacks.NovelDetailsDataCallback;
import ecez.vndbapp.controller.vndatabaseapp;
import ecez.vndbapp.model.Console;
import ecez.vndbapp.model.Constants;
import ecez.vndbapp.model.Country;
import ecez.vndbapp.model.DetailsData;
import ecez.vndbapp.model.Error;
import ecez.vndbapp.model.NovelScreenShot;
import ecez.vndbapp.model.ServerRequest;
import ecez.vndbapp.model.Tag;

/**
 * Created by Teng on 10/27/2016.
 */
public class PopulateNovelDetails extends AsyncTask {

    private String jsonString, genres;
    private DetailsData data;
    private List<NovelScreenShot> screens;
    private int id;
    private List<Country> countries;
    private List<Console> consoles;
    private List<String> genreList = new ArrayList<>();
    private List<String> possibleGenres = new ArrayList<String>(){{
        add("Drama");
        add("Fantasy");
        add("Horror");
        add("Action");
        add("Comedy");
        add("Romance");
        add("Mystery");
        add("Thriller");
    }};

    public NovelDetailsDataCallback callback;

    public PopulateNovelDetails(int id) {
        this.id = id;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        if (vndatabaseapp.loggedIn == true)
            jsonString = ServerRequest.getInstance().writeToServer("get", "vn", "basic,stats,details,screens,tags", "(id = "+Integer.toString(id)+")",null);
        else {
            callback.onFailure(null, Constants.serverNotLoggedInError);
            return false;
        }
        Log.d("JSON Response",jsonString);

        Gson gson = new Gson();

        if (jsonString.substring(0,4).equals("error")) {
            Error error = gson.fromJson(jsonString.toString(), Error.class);
            callback.onFailure(error,Constants.serverErrorResponse);
            return false;
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
            return false;
        }
        Log.d("json",jsonResponse.toString());
        String s = jsonResponse.toString();
        s = s.substring(1,s.length()-1);//Removes the square braces from the response

        JSONObject j;
        JSONArray jsonScreens;
        try {
            j = new JSONObject(s);
            jsonScreens = j.getJSONArray("screens");
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onFailure(null,Constants.jsonSerializationError);
            return false;
        }
        NovelScreenShot [] pics = gson.fromJson(jsonScreens.toString(),NovelScreenShot[].class);
        Log.d("Screens",jsonScreens.toString());
        screens = new ArrayList<>(Arrays.asList(pics));

        Log.d("json2",s);
        data = gson.fromJson(s,DetailsData.class);

        createCountryList();

        createConsoleList();

        findTags();

        return true;

    }
    private void createCountryList () {
        countries = new ArrayList<>();
        String [] l = data.getLanguages();
        for (int x = 0; x<l.length; x++) {
            countries.add(new Country(l[x]));
        }
    }

    private void createConsoleList () {
        consoles = new ArrayList<>();
        String [] a = data.getPlatforms();
        for (int x = 0; x<a.length; x++) {
            consoles.add(new Console(a[x]));
        }
    }

    private void findTags() {
        String [] [] tags = data.getTags();
        if (tags == null)
            return;

        Tag tag;

        for (int x = 0; x < tags.length; x++){ //iterate for every tag returned
            int tagID = Integer.parseInt(tags[x][0]);
            tag = vndatabaseapp.tagsMap.get(tagID);
            if (tag == null)
                continue; //Sometimes, the tag ID that is looked up will return a null Tag
            findGenres(tag);
        }

        if (genreList == null) {
            genres = Constants.noGenres;
            return;
        }

        StringBuilder s = new StringBuilder();

        for (String d : genreList){
            s.append(d);
            s.append("  |  ");
        }
        if (s.length() < 6) {
            s.append(Constants.noGenres + "12345"); //Add 5 characters that will be cut off
        }
        genres = s.toString().substring(0, s.toString().length() - 5);
    }

    private void findGenres (Tag tag) {
        if (!performGenreCheck(tag.getName())) {
            findGenreFromParent(tag);
        }
    }

    private boolean performGenreCheck(String tagName) {

        for (String genre : possibleGenres) {
            while (genreList.size() < 4 && tagName.equals(genre)) {
                genreList.add(tagName);
                possibleGenres.remove(genre);
                return true;
            }
        }
        return false;
    }

    private void findGenreFromParent (Tag t) {
        Integer [] parents = t.getParents();
        if (parents.length > 0) { //make another recursive call
            Tag nextTag = vndatabaseapp.tagsMap.get(parents[0]);
            if (!performGenreCheck(nextTag.getName())) {
                findGenreFromParent(nextTag);
            }
        }
    }

    @Override
    public void onPostExecute (Object o) {
        if (o.equals(true)) {
            callback.onSuccessUI(countries, consoles, data, screens, genres);
        }
    }
}
