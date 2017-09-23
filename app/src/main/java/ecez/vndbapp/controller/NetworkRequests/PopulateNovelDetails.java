package ecez.vndbapp.controller.NetworkRequests;

import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import ecez.vndbapp.controller.Callbacks.NovelDetailsDataCallback;
import ecez.vndbapp.controller.vndatabaseapp;
import ecez.vndbapp.model.Constants;
import ecez.vndbapp.model.DetailsData;
import ecez.vndbapp.model.SystemStatus;
import ecez.vndbapp.model.Tag;

/**
 * Created by Teng on 10/27/2016.
 */
public class PopulateNovelDetails extends VNDBrequest {

    private String genres;
    private DetailsData data;
    private int id;
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
        JSONArray jsonResponse = getJSONfromRequest("get", "vn", "basic,stats,details,anime,screens,tags",
                "(id = "+Integer.toString(id)+")",null,callback);
        Log.d("json",jsonResponse.toString());
        if (jsonResponse == null)
            return false;

        String s = jsonResponse.toString();
        s = s.substring(1,s.length()-1);//Removes the square braces from the response

        Log.d("json2",s);
        data = gson.fromJson(s,DetailsData.class);

        findTags();

        return true;
    }

    private void findTags() {
        String [] [] tags = data.getTags();
        if (tags == null)
            return;

        Tag tag;

        for (int x = 0; x < tags.length; x++){ //iterate for every tag returned
            int tagID = Integer.parseInt(tags[x][0]);
            tag = SystemStatus.getInstance().tagsMap.get(tagID);
            if (tag == null)
                continue; //Sometimes, the tag ID that is looked up will return a null Tag
            findGenres(tag);
        }

        if (genreList == null) {
            genres = Constants.NO_GENRES;
            return;
        }

        StringBuilder s = new StringBuilder();

        for (String d : genreList){
            s.append(d);
            s.append("  |  ");
        }
        if (s.length() < 6) {
            s.append(Constants.NO_GENRES + "12345"); //Add 5 characters that will be cut off
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
            Tag nextTag = SystemStatus.getInstance().tagsMap.get(parents[0]);
            if (!performGenreCheck(nextTag.getName())) {
                findGenreFromParent(nextTag);
            }
        }
    }

    @Override
    public void onPostExecute (Object o) {
        if (o.equals(true)) {
            callback.onSuccessUI(data, genres);
        }
    }
}
