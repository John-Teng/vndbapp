package ecez.vndbapp.controller.Callbacks;

import java.util.List;

import ecez.vndbapp.model.NovelData;

/**
 * Created by johnteng on 2017-04-23.
 */

public interface ListCallback <T> extends AsyncTaskCallback{
    void returnList(List<T> list);
    void onSuccessUI();
}
