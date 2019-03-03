package com.anonsgroup.anons;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;
    public PagerAdapter(FragmentManager fm,int NumnberOfTabs){
        super(fm);
        this.mNoOfTabs=NumnberOfTabs;
    }


    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new ProfilEkran();
            case 1:
                return new AnaMenuEkran();
            case 2:
                return new ChatEkran();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}


