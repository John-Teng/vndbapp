package ecez.vndbapp.controller.Utils;

import android.content.Context;
import android.support.annotation.Nullable;

/**
 * Created by johnteng on 2018-01-05.
 */

public class DisplayUtils {

    public static float DpToPx(float dp, @Nullable Context context) {
        if (context == null)
            return dp;
        float scale = context.getResources().getDisplayMetrics().density;
        return dp*scale + 0.5f;
    }

    public static float PxToDp(float px, @Nullable Context context) {
        if (context == null)
            return px;
        float scale = context.getResources().getDisplayMetrics().density;
        return (px-0.5f)/scale;
    }
}
