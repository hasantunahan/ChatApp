package com.anonsgoup.anons;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomProfilAdapter extends RecyclerView.Adapter<CustomProfilAdapter.ViewHolder> {
    ArrayList<MobileOs> mobileOsArrayList=new ArrayList<>();

    public CustomProfilAdapter(ArrayList<MobileOs> mobileOsArrayList, Context context) {
        this.mobileOsArrayList = mobileOsArrayList;
        this.context = context;
    }

    LayoutInflater layoutInflater;
    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater=LayoutInflater.from(context);
        View v= layoutInflater.inflate(R.layout.profil_anons_list,viewGroup,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textName.setText(mobileOsArrayList.get(i).getProfilName());
        viewHolder.textYazi.setText(mobileOsArrayList.get(i).getProfilYazi());
        viewHolder.textKonum.setText(mobileOsArrayList.get(i).getProfilKonum());
        viewHolder.textTarih.setText(mobileOsArrayList.get(i).getProfilTarih());
        viewHolder.textSayi.setText(mobileOsArrayList.get(i).getLikeSayisi());
        viewHolder.imgProfil.setImageResource(mobileOsArrayList.get(i).getProfilImage());
        viewHolder.imgLike.setImageResource(mobileOsArrayList.get(i).getLikeimagee());
        viewHolder.linearLayout.setTag(viewHolder);

    }

    @Override
    public int getItemCount() {
        return mobileOsArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
         TextView textName,textYazi,textKonum,textTarih,textSayi;
         ImageView imgProfil,imgLike;
         LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName=itemView.findViewById(R.id.profilKisiAdiTextView);
            textYazi=itemView.findViewById(R.id.profilKisiAnonsTextView);
            textKonum=itemView.findViewById(R.id.profilKnumTextView);
            textTarih=itemView.findViewById(R.id.profilTarihTextView);
            textSayi=itemView.findViewById(R.id.profilLikeSayisiTextView);
            imgProfil=itemView.findViewById(R.id.profilKisiImagaView);
            imgLike=itemView.findViewById(R.id.profilLikeImageView);
            linearLayout=itemView.findViewById(R.id.linear);
        }
    }
}
