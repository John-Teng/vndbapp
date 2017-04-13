package ecez.vndbapp.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import ecez.vndbapp.model.ListItem;
import ecez.vndbapp.model.ServerRequest;
import ecez.vndbapp.view.vndatabaseapp;

import static ecez.vndbapp.R.id.view;

/**
 * Created by Teng on 10/22/2016.
 */
public class PopulateListItems extends AsyncTask {/// MOVE THIS CLASS TO AN ASYNC TASK
    private ArrayList<ListItem> list;
    private String jsonString;
    private int page;
    private final int resultPerPage = 25;
    private String sortParam;
    private ProgressBar pb;
    private RecyclerAdapter adapter;
    private SwipeRefreshLayout mSwipeContainer;

    public PopulateListItems(ArrayList<ListItem> list, int page, String sortParam, ProgressBar pb, RecyclerAdapter adapter, SwipeRefreshLayout mSwipeContainer) {
        this.list = list;
        this.page = page;
        this.sortParam = sortParam;
        this.pb = pb;
        this.adapter = adapter;
        this.mSwipeContainer = mSwipeContainer;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
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
        Log.d("Async Task","About to finish getting data");

        return true;
    }

    @Override
    protected void onPostExecute(Object o) {
        pb.setVisibility(View.GONE);
        adapter.setData(this.list);
        adapter.notifyDataSetChanged();
        mSwipeContainer.setRefreshing(false);
    }


}
