package ecez.vndbapp.controller.Callbacks;

import java.util.List;

/**
 * Created by johnteng on 2017-04-23.
 */

public interface ListCallback extends AsyncTaskCallback{
    void returnList(Object[] array);
    void onSuccessUI();
}
