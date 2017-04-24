package ecez.vndbapp.controller.Callbacks;

import ecez.vndbapp.model.Error;

/**
 * Created by johnteng on 2017-04-13.
 */

public interface AsyncTaskCallback {
    void onFailure(Error error, String errorMessage);
    void onSuccessUI();
}
