package com.gaia.app.smartwarehouse.classes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by praveen_gadi on 6/29/2016.
 */
public class TabViewerAdapter extends FragmentStatePagerAdapter {


    private final List<Fragment> search_FragmentList = new ArrayList<>();
    private final List<String> search_FragmentTitleList = new ArrayList<>();


    public TabViewerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return search_FragmentList.get(position);
    }

    @Override
    public int getCount() {
        return search_FragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return search_FragmentTitleList.get(position);
    }

    public void addfragment(Fragment fragment, String title)
    {
        search_FragmentList.add(fragment);
        search_FragmentTitleList.add(title);
    }

}
