package com.anonsgroup.anons;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AnaEkran extends AppCompatActivity implements ProfilEkran.OnFragmentInteractionListener,AnaMenuEkran.OnFragmentInteractionListener,ChatEkran.OnFragmentInteractionListener {
    TextView arkadasSayisiTextView;
    TextView bildirimSayisiTextView;
    TextView begeniSayisiTextView;
    TextView profilAdSayodYasTextView;
    private FirebaseAuth mAuth;


    public void profilEkranTiklama(View view){
        Toast.makeText(getApplicationContext(), "Hasan", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        profilAdSayodYasTextView = findViewById(R.id.profilAdSayodTextView);
        bildirimSayisiTextView = findViewById(R.id.bildirimSayisiTextView);
        begeniSayisiTextView = findViewById(R.id.begeniSayisiTextView);
        arkadasSayisiTextView = findViewById(R.id.arkadasSayisiTextView);

        final ViewPager viewPager =(ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager(),3);
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {
                        if (ProfilEkran.mdrawerLayout!= null && ProfilEkran.mdrawerLayout.isDrawerOpen(GravityCompat.START))
                            ProfilEkran.mdrawerLayout.closeDrawer(GravityCompat.START);
                    }
                });
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
    @Override
    public void onBackPressed() {
    if (ProfilEkran.mdrawerLayout!= null && ProfilEkran.mdrawerLayout.isDrawerOpen(GravityCompat.START))
        ProfilEkran.mdrawerLayout.closeDrawer(GravityCompat.START);
        else
        super.onBackPressed();
    }
}
