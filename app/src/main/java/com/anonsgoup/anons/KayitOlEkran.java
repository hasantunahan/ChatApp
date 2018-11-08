package com.anonsgoup.anons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class KayitOlEkran extends AppCompatActivity {

    Spinner genderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol_ekran);
        genderSpinner = findViewById(R.id.genderSpinner);

    }
}
