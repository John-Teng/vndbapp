package ecez.vndbapp.controller.Callbacks;

import ecez.vndbapp.model.DetailsData;

/**
 * Created by johnteng on 2017-04-24.
 */

public interface NovelDetailsDataCallback extends AsyncTaskCallback {
void onSuccessUI(DetailsData detailsData, String genres);
}

