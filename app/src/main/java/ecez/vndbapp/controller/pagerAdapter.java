package ecez.vndbapp.controller;

import android.os.Bundle;
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
                return newFragmentWithQuery("rating");
            case 1:
                return newFragmentWithQuery("popularity");
            case 2:
                return newFragmentWithQuery("released");
            default:
                return null;
        }
    }

    public static final TopNovelsFragment newFragmentWithQuery (String sortParam) {
        TopNovelsFragment tab = new TopNovelsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("SORTPARAM",sortParam);
        tab.setArguments(bundle);
        return tab;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}