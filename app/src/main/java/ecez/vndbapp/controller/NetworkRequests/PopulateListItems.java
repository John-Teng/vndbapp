package ecez.vndbapp.controller.NetworkRequests;

import android.util.Log;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ecez.vndbapp.controller.Callbacks.ListCallback;
import ecez.vndbapp.model.NovelData;

/**
 * Created by Teng on 10/22/2016.
 */
public class PopulateListItems extends VNDBrequest {
    private List<NovelData> list;
    private int page;
    private final int resultPerPage = 25;
    private String sortParam;
    public ListCallback callback;

    public PopulateListItems(int page, String sortParam) {
        this.list = new ArrayList<>();
        this.page = page;
        this.sortParam = sortParam;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        JSONArray jsonResponse = getJSONfromRequest("get", "vn", "basic,stats,details", "(released > \"1945\")",
                "{\"page\":"+Integer.toString(page)+",\"results\":"+resultPerPage+",\"sort\":\""+ sortParam+"\","
                        + "\"reverse\":true}",callback);

        if (jsonResponse == null)
            return false;

        Log.d("json",jsonResponse.toString());
        NovelData[] l = gson.fromJson(jsonResponse.toString(),NovelData[].class);
        list = new ArrayList<>(Arrays.asList(l));
        int y = (resultPerPage*page-(resultPerPage-1));
        for (NovelData x:list) {
            x.setRank(y);
            y ++;
        }
        callback.returnList(list);

        return true;
    }

    @Override
    protected void onPostExecute(Object o) {
        if (o.equals(true))
            callback.onSuccessUI();

    }

}
