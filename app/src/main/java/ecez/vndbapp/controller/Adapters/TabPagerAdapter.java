package ecez.vndbapp.controller.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.LinkedList;
import java.util.List;

import ecez.vndbapp.controller.NovelListFragment;
import ecez.vndbapp.model.Constants;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private List<NovelListFragment> mActiveFragments = new LinkedList<>();

    public TabPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return newFragmentWithQuery(Constants.SORT_RATING);
            case 1:
                return newFragmentWithQuery(Constants.SORT_POPULARITY);
            case 2:
                return newFragmentWithQuery(Constants.SORT_RELEASED);
            default:
                return null;
        }
    }

    private final NovelListFragment newFragmentWithQuery (int sortParam) {
        NovelListFragment tab = new NovelListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("SORTPARAM",sortParam);
        tab.setArguments(bundle);
        mActiveFragments.add(tab);
        return tab;
    }

    public List<NovelListFragment> getFragmentList () {
        return mActiveFragments;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}