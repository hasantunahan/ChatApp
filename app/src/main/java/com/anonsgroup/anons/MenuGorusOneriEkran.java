package com.anonsgroup.anons;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MenuGorusOneriEkran extends AppCompatActivity {
    EditText gorusOneriEditText;
    Spinner gorusOneriSpinner;
    private String gorusOneri;
    private String mailIcerigi;
    Button mailGonderButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_gorus_oneri_ekran);
        gorusOneriEditText = findViewById(R.id.gorusOneriEditText);
        gorusOneriSpinner = findViewById(R.id.gorusOneriSpinner);
        mailGonderButton=findViewById(R.id.mailGonderButton);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Görüş ve Öneriler");
        actionBar.setDisplayHomeAsUpEnabled(true);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.opinion_suggestions, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        gorusOneriSpinner.setAdapter(adapter);

        mailGonderButton.setOnClickListener(v -> {
            gorusOneri = gorusOneriSpinner.getSelectedItem().toString();
            mailIcerigi = gorusOneriEditText.getText().toString();
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,gorusOneri);//Email konusu
            emailIntent.putExtra(Intent.EXTRA_TEXT,mailIcerigi);//Email içeriği
            startActivity(Intent.createChooser(emailIntent, "E-mail Göndermek için Seçiniz:")); //birden fazla email uygulaması varsa seçmek için
            String aEmailList[] = { "anons.aths@gmail.com" };  //Mail gönderielecek kişi.Birden fazla ise virgülle ayırarak yazılır
            emailIntent.putExtra(Intent.EXTRA_EMAIL, aEmailList);
            startActivity(emailIntent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home==item.getItemId()){  //actionbar geri tusu için
            finish();}
        return super.onOptionsItemSelected(item);
    }

}
