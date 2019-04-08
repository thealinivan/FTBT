package com.example.ftbt;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter{

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return null;
    }

    @Override
    public Fragment getItem(int attraction_group) {
        switch(attraction_group){
            case 0:
                return new ListMainAttrFragment();
            case 1:
                return new ListMuseumFragment();
            case 2:
                return new ListGreenSpacesFragment();
        }
        return null;
    }


}
