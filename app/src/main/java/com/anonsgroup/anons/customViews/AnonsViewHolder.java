package com.anonsgroup.anons.customViews;


import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.anonsgroup.anons.R;

public class AnonsViewHolder extends RecyclerView.ViewHolder {



    public ImageView profilFotograf,begeniFotograf,aProfilFoto;
    public TextView kisi,metin,konum,tarih,aTarih,aMetin,aKonum,aKisi;
    public ConstraintLayout acilanLayout,geciciAcil,geciciİlk;
    public ConstraintLayout ilkLayout;
    public EditText cevapEditText;
    //boolean kontrol=false;
    public AnonsViewHolder(@NonNull final View itemView) {
        super(itemView);
        profilFotograf=itemView.findViewById(R.id.anaEkranKisiImageView);
        kisi=itemView.findViewById(R.id.anaekranKisiAdiTextView);
        metin=itemView.findViewById(R.id.anaekranKisiAnonsTextView);
        konum=itemView.findViewById(R.id.anaekranKnumTextView);
        tarih=itemView.findViewById(R.id.anaekranTarihTextView);
        begeniFotograf=itemView.findViewById(R.id.anaekranYeniBildiriImageView);

        aProfilFoto=itemView.findViewById(R.id.aProfilFoto);
        aKisi=itemView.findViewById(R.id.aKisi);
        aKonum=itemView.findViewById(R.id.aKonum);
        aMetin=itemView.findViewById(R.id.aMetin);
        aTarih=itemView.findViewById(R.id.aTarih);
        //TODO: Burası Scroolling olarak tekrar yapılcak
        cevapEditText=itemView.findViewById(R.id.cevapEditText);
        cevapEditText.setScroller(new Scroller(itemView.getContext()));
        cevapEditText.setMaxLines(3);
        cevapEditText.setVerticalScrollBarEnabled(true);
        cevapEditText.setMovementMethod(new ScrollingMovementMethod());

        acilanLayout=itemView.findViewById(R.id.acilanLayout);
        ilkLayout=itemView.findViewById(R.id.linear);
        ilkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Veritabanında görüldü alanı değiştirilecek.
                if(begeniFotograf.getDrawable() != null)
                    begeniFotograf.setImageDrawable(null);
                acilanLayout.setVisibility(View.VISIBLE);
                ilkLayout.setVisibility(View.GONE);
                if(geciciAcil!=acilanLayout&&geciciAcil!=null&&geciciAcil.getVisibility() == View.VISIBLE){
                    geciciAcil.setVisibility(View.GONE);
                    geciciİlk.setVisibility(View.VISIBLE);
                }
                geciciAcil = acilanLayout;
                geciciİlk = ilkLayout;
            }
        });
        acilanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ilkLayout.getVisibility()==View.GONE) {
                    ilkLayout.setVisibility(View.VISIBLE);
                    acilanLayout.setVisibility(View.GONE);
                }
            }
        });




    }
}
