package ecez.vndbapp.controller.Callbacks;

import java.util.List;

import ecez.vndbapp.model.Console;
import ecez.vndbapp.model.Country;
import ecez.vndbapp.model.DetailsData;
import ecez.vndbapp.model.NovelScreenShot;

/**
 * Created by johnteng on 2017-04-24.
 */

public interface NovelDetailsDataCallback extends AsyncTaskCallback {
    void onSuccessUI(List<Country>countryList, List<Console> consoleList, DetailsData detailsData,
                     List<NovelScreenShot> novelScreenShots, String genres);
}

