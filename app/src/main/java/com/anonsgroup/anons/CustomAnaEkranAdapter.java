package com.anonsgroup.anons;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.anonsgroup.anons.models.Anons;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;



public class CustomAnaEkranAdapter extends RecyclerView.Adapter<CustomAnaEkranAdapter.myviewHolder> {

    public CustomAnaEkranAdapter(Context context, List<Anons> mdata) {
        this.context = context;
        this.mdata = mdata;
    }

    private ConstraintLayout geciciAcil;
    private ConstraintLayout geciciİlk;

    Context context;
    List<Anons> mdata;

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.anaekran_anons_list,viewGroup,false);
        return new myviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder myviewHolder, int i) {
        //TODO: firebaseden çekilecek kardeşşşşşşş.
        Glide.with(context).load(mdata.get(i).getProfilUrl()).into(myviewHolder.profilFotograf);
        myviewHolder.profilFotograf.setImageResource(R.drawable.kullaniciprofildefault);
        myviewHolder.kisi.setText(mdata.get(i).getUsername());
        myviewHolder.metin.setText(mdata.get(i).getText());
        myviewHolder.konum.setText(mdata.get(i).getLocation());
        myviewHolder.tarih.setText(""+mdata.get(i).getDate());

        Glide.with(context).load(mdata.get(i).getProfilUrl()).into(myviewHolder.aProfilFoto);
        myviewHolder.aKisi.setText(mdata.get(i).getUsername());
        myviewHolder.aMetin.setText(mdata.get(i).getText());
        myviewHolder.aKonum.setText(mdata.get(i).getLocation());
        myviewHolder.aTarih.setText(""+mdata.get(i).getDate());

        if(!mdata.get(i).isSeen())
            myviewHolder.begeniFotograf.setImageResource(R.drawable.ic_bildiri);

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }





    public class myviewHolder extends RecyclerView.ViewHolder{
        LinearLayout l;
        ConstraintLayout c;

        ImageView profilFotograf,begeniFotograf,aProfilFoto;
        TextView kisi,metin,konum,tarih,aTarih,aMetin,aKonum,aKisi;
        ConstraintLayout acilanLayout;
        ConstraintLayout ilkLayout;
        EditText cevapEditText;
        //boolean kontrol=false;
        public myviewHolder(@NonNull final View itemView) {
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
            cevapEditText.setScroller(new Scroller(context));
            cevapEditText.setMaxLines(3);
            cevapEditText.setVerticalScrollBarEnabled(true);
            cevapEditText.setMovementMethod(new ScrollingMovementMethod());

            acilanLayout=itemView.findViewById(R.id.acilanLayout);
            ilkLayout=itemView.findViewById(R.id.linear);
            ilkLayout.setOnClickListener(v -> {
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
            });
            acilanLayout.setOnClickListener(v -> {
                if(ilkLayout.getVisibility()==View.GONE) {
                    ilkLayout.setVisibility(View.VISIBLE);
                    acilanLayout.setVisibility(View.GONE);
                }
            });




        }
    }
}
