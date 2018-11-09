package com.anonsgoup.anons;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class KayitOlEkran extends AppCompatActivity {

    private Spinner genderSpinner;
    private TextView dobEditText;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol_ekran);

        genderSpinner = findViewById(R.id.genderSpinner);
        dobEditText = findViewById(R.id.dobEditText);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.sex, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);


    }

    public void kayitOlClicks(View view) {
        switch (view.getId()){
            case R.id.dobEditText:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(KayitOlEkran.this,
                        android.R.style.Theme_Holo_Light_Dialog,onDateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

                onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        dobEditText.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                };
        }
    }
}
