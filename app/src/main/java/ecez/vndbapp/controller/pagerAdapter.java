package ecez.vndbapp.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ecez.vndbapp.view.TopNovelsFragment;
import ecez.vndbapp.view.PopularNovelsFragment;
import ecez.vndbapp.view.NewNovelsFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

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
                PopularNovelsFragment tab2 = new PopularNovelsFragment();
                return tab2;
            case 2:
                NewNovelsFragment tab3 = new NewNovelsFragment();
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