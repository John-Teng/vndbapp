package ecez.vndbapp.controller.NetworkRequests;

import android.util.Log;
import org.json.JSONArray;

import ecez.vndbapp.controller.Callbacks.ListCallback;
import ecez.vndbapp.model.Constants;
import ecez.vndbapp.model.NovelData;

/**
 * Created by Teng on 10/22/2016.
 */
public class PopulateListItems extends VNDBrequest {
    private int page;
    private final int RESULTS_PER_PAGE = 24;
    private int sortParam;
    public ListCallback callback;

    public PopulateListItems(int page, int sortParam) {
        this.page = page;
        this.sortParam = sortParam;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        JSONArray jsonResponse = getJSONfromRequest("get", "vn", "basic,stats,details", "(released > \"1945\")", "{\"page\":"+
                                        Integer.toString(page)+
                                        ",\"results\":"+
                                        RESULTS_PER_PAGE +
                                        ",\"sort\":\""+
                                        Constants.getSortParam(sortParam)+
                                        "\"," +
                                        "\"reverse\":true}",callback);

        if (jsonResponse == null)
            return false;

        Log.d("json",jsonResponse.toString());
        NovelData[] array = gson.fromJson(jsonResponse.toString(),NovelData[].class);
        int y = (RESULTS_PER_PAGE *page-(RESULTS_PER_PAGE -1));
        for (int x = 0; x< array.length; x++) {
            array[x].setRank(y);
            y ++;
        }
        callback.returnList(array);

        return true;
    }

    @Override
    protected void onPostExecute(Object o) {
        if (o.equals(true))
            callback.onSuccessUI();

    }

}
