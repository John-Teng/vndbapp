package ecez.vndbapp.model;
/** Custom your own ViewPager to extends support ViewPager. java source: */
/** Created by azi on 2013-6-21.  */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class FixedViewPager extends android.support.v4.view.ViewPager {

    public FixedViewPager(Context context) {
        super(context);
    }

    public FixedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
