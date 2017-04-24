package ecez.vndbapp.controller.Callbacks;

import java.util.List;

import ecez.vndbapp.model.ListItem;

/**
 * Created by johnteng on 2017-04-23.
 */

public interface ListCallback extends AsyncTaskCallback{
    void returnList(List<ListItem> list);
}
