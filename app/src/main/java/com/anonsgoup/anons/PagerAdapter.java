package com.anonsgoup.anons;

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
                ProfilEkran pf=new ProfilEkran();
                return pf;
            case 1:
                AnaMenuEkran am=new AnaMenuEkran();
                return am;
            case 2:
                ChatEkran ch=new ChatEkran();
                return ch;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}


