package ecez.vndbapp.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ecez.vndbapp.view.TopNovelsFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TopNovelsFragment tab1 = new TopNovelsFragment();
                return tab1;
            case 1:
                TopNovelsFragment tab2 = new TopNovelsFragment();
                return tab2;
            case 2:
                TopNovelsFragment tab3 = new TopNovelsFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}