package com.anonsgoup.anons;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AnaEkran extends AppCompatActivity implements ProfilEkran.OnFragmentInteractionListener,AnaMenuEkran.OnFragmentInteractionListener,ChatEkran.OnFragmentInteractionListener {
    private static int PROFIL=0;
    private static int ANAMENU=1;
    private static int CHAT=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran);

        final ViewPager viewPager =(ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager(),3);
                viewPager.setAdapter(adapter);viewPager.setCurrentItem(1);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
