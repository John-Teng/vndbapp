package ecez.vndbapp.controller.Callbacks;

import java.util.List;

import ecez.vndbapp.model.ListItem;

/**
 * Created by johnteng on 2017-04-13.
 */

public interface ListCallback {
    void onSuccess(List<ListItem> list);
    void onSuccessUI();
    void onFailureUI();
}
