package ecez.vndbapp.controller.Callbacks;

import java.util.List;

import ecez.vndbapp.model.NovelData;

/**
 * Created by johnteng on 2017-04-23.
 */

public interface ListCallback extends AsyncTaskCallback{
    void returnList(List<NovelData> list);
    void onSuccessUI();
}
