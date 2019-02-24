package com.anonsgoup.anons;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAnaEkranAdapter extends RecyclerView.Adapter<CustomAnaEkranAdapter.myviewHolder> {

    public CustomAnaEkranAdapter(Context context, List<AnaEkranitem> mdata) {
        this.context = context;
        this.mdata = mdata;
    }

    Context context;
    List<AnaEkranitem> mdata;

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.anaekran_anons_list,viewGroup,false);
        return new myviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder myviewHolder, int i) {
        myviewHolder.anaEkranProfil.setImageResource(mdata.get(i).getAnaprofilImage());
        myviewHolder.anaEkranKisi.setText(mdata.get(i).getAnaKisi());
        myviewHolder.anaEkranYazi.setText(mdata.get(i).getAnaYazi());
        myviewHolder.anaEkranKonum.setText(mdata.get(i).getAnaKonum());
        myviewHolder.anaEkranTarih.setText(mdata.get(i).getAnaTarih());
        myviewHolder.anaEkranYeniBildiri.setImageResource(mdata.get(i).getAnaYeniBildiriImage());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder{
        ImageView anaEkranProfil,anaEkranYeniBildiri;
        TextView anaEkranKisi,anaEkranYazi,anaEkranKonum,anaEkranTarih;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            anaEkranProfil=itemView.findViewById(R.id.anaEkranKisiImageView);
            anaEkranKisi=itemView.findViewById(R.id.anaekranKisiAdiTextView);
            anaEkranYazi=itemView.findViewById(R.id.anaekranKisiAnonsTextView);
            anaEkranKonum=itemView.findViewById(R.id.anaekranKnumTextView);
            anaEkranTarih=itemView.findViewById(R.id.anaekranTarihTextView);
            anaEkranYeniBildiri=itemView.findViewById(R.id.anaekranYeniBildiriImageView);


        }
    }
}
