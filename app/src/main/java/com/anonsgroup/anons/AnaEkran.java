package com.anonsgroup.anons;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anonsgroup.anons.Listeners.LocationListenerImpl;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class AnaEkran extends AppCompatActivity implements ProfilEkran.OnFragmentInteractionListener, AnaMenuEkran.OnFragmentInteractionListener, ChatEkran.OnFragmentInteractionListener {
    TextView arkadasSayisiTextView;
    TextView bildirimSayisiTextView;
    TextView begeniSayisiTextView;
    TextView profilAdSayodYasTextView;
    private FirebaseAuth mAuth;
    protected LocationManager locationManager;
    LocationListenerImpl locationListenerImpl;


    public void profilEkranTiklama(View view) {
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

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), 3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (ProfilEkran.mdrawerLayout != null && ProfilEkran.mdrawerLayout.isDrawerOpen(GravityCompat.START))
                    ProfilEkran.mdrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        // FirebaseMessaging.getInstance().subscribeToTopic("anons");
        System.out.println("serogeldiiiiiiiiiiii");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            System.out.println("tırogeldiiiiiiiiiiiiiiiiiiiiiiiiiiii");

            return;
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListenerImpl=new LocationListenerImpl();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerImpl);

        Toast.makeText(this, ""+locationListenerImpl.can, Toast.LENGTH_SHORT).show();

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
