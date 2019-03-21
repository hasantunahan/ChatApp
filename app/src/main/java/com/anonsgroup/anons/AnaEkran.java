package com.anonsgroup.anons;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

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
        FirebaseMessaging.getInstance().subscribeToTopic("anons");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Objects.requireNonNull(currentUser).reload().addOnFailureListener(e -> startLoginEkran());

       /* FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("taggg", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = token;
                        Log.d("TOKEN::::::::", msg);
                        Toast.makeText(AnaEkran.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });*/

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
