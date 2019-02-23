package com.anonsgoup.anons;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AnaEkran extends AppCompatActivity implements ProfilEkran.OnFragmentInteractionListener,AnaMenuEkran.OnFragmentInteractionListener,ChatEkran.OnFragmentInteractionListener {
    TextView arkadasSayisiTextView;
    TextView bildirimSayisiTextView;
    TextView begeniSayisiTextView;
    TextView profilAdSayodYasTextView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();


        profilAdSayodYasTextView = findViewById(R.id.profilAdSayodYasTextView);
        bildirimSayisiTextView = findViewById(R.id.bildirimSayisiTextView);
        begeniSayisiTextView = findViewById(R.id.begeniSayisiTextView);
        arkadasSayisiTextView = findViewById(R.id.arkadasSayisiTextView);

        final ViewPager viewPager =(ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager(),3);
                viewPager.setAdapter(adapter);viewPager.setCurrentItem(1);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            startLoginEkran();
        }
    }

    private void startLoginEkran(){
        Intent intent = new Intent(getApplicationContext(),LoginEkran.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
