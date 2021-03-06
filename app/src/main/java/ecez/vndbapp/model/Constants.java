package ecez.vndbapp.model;

import android.util.Log;

import ecez.vndbapp.R;

/**
 * Created by johnteng on 2017-04-23.
 */

public class Constants {

    public static final String SERVER_ERROR_RESPONSE = "Error response from the server";
    public static final String SERVER_NOT_LOGGED_IN_ERROR = "Not logged into server";
    public static final String JSON_SERIALIZATION_ERROR = "Problem occurred with json serialization";
    public static final String NO_GENRES = "No Genre Information Available";
    public static final String INTENT_NAME = "NOVEL_NAME";
    public static final String INTENT_ID = "NOVEL_ID";
    public static final String INTENT_CHARACTERS = "NOVEL_CHARACTERS";
    public static final int NO_SPOILERS = 0;
    public static final int MINOR_SPOILERS = 1;
    public static final int ALL_SPOILERS = 2;
    public static final int LIST_DISPLAY = 0;
    public static final int GRID_DISPLAY = 1;
    public static final int SORT_RATING = 0;
    public static final int SORT_POPULARITY = 1;
    public static final int SORT_RELEASED = 2;
    public static final String NSFW_IMAGE = "android.resource://ecez.vndbapp/"+ R.drawable.nsfw_image;

    public static String getSortParam (int sortParam) {
        switch (sortParam) {
            case (Constants.SORT_RATING):
                return "rating";
            case (Constants.SORT_POPULARITY):
                return "popularity";
            case (Constants.SORT_RELEASED):
                return "released";
            default:
                return "rating";
        }

    }
}
