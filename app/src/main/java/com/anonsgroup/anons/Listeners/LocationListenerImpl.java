package com.anonsgroup.anons.Listeners;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.anonsgroup.anons.AnaEkran;

import static android.support.constraint.Constraints.TAG;

public class LocationListenerImpl implements LocationListener {
    public double sero,can;
    @Override
    public void onLocationChanged(Location location) {

       
        sero=location.getLatitude();
        can=location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
