package ecez.vndbapp.controller.Callbacks;

/**
 * Created by johnteng on 2017-05-07.
 */

public interface DefaultCallback <T> extends  AsyncTaskCallback {
    void onSuccess(T item);
}
