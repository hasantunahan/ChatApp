package com.anonsgoup.anons;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AnaEkran extends AppCompatActivity implements ProfilEkran.OnFragmentInteractionListener,AnaMenuEkran.OnFragmentInteractionListener,ChatEkran.OnFragmentInteractionListener {
    private static int PROFIL=0;
    private static int ANAMENU=1;
    private static int CHAT=2;
    TextView arkadasSayisiTextView;
    TextView bildirimSayisiTextView;
    TextView begeniSayisiTextView;
    TextView profilAdSayodYasTextView;
    TextView hakkimdaTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran);

        hakkimdaTextView = findViewById(R.id.hakkimdaTextView);
        profilAdSayodYasTextView = findViewById(R.id.profilAdSayodYasTextView);
        bildirimSayisiTextView = findViewById(R.id.bildirimSayisiTextView);
        begeniSayisiTextView = findViewById(R.id.begeniSayisiTextView);
        arkadasSayisiTextView = findViewById(R.id.arkadasSayisiTextView);
        final ViewPager viewPager =(ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager(),3);
                viewPager.setAdapter(adapter);viewPager.setCurrentItem(1);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
