package com.anonsgroup.anons;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anonsgroup.anons.models.Anons;

import java.util.ArrayList;

public class CustomProfilAdapter extends RecyclerView.Adapter<CustomProfilAdapter.ViewHolder> {
    ArrayList<Anons> anonsArrayList;

    public CustomProfilAdapter(ArrayList<Anons> anonsArrayList, Context context) {
        this.anonsArrayList = anonsArrayList;
        this.context = context;
    }

    LayoutInflater layoutInflater;
    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater=LayoutInflater.from(context);
        View v= layoutInflater.inflate(R.layout.profil_anons_list,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textName.setText(anonsArrayList.get(i).getKisi());
        viewHolder.textYazi.setText(anonsArrayList.get(i).getMetin());
        viewHolder.textKonum.setText(anonsArrayList.get(i).getKonum());
        viewHolder.textTarih.setText(anonsArrayList.get(i).getTarih());
        //TODO Beğeni Sayısı bir şekilde buraya çekilecek ayarlama yapılacak.
        viewHolder.textSayi.setText("---");
        viewHolder.imgProfil.setImageResource(anonsArrayList.get(i).getProfilFotograf());
        viewHolder.imgLike.setImageResource(R.drawable.ic_like);
        viewHolder.constraintLayout.setTag(viewHolder);

    }

    @Override
    public int getItemCount() {
        return anonsArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
         TextView textName,textYazi,textKonum,textTarih,textSayi;
         ImageView imgProfil,imgLike;
         ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName=itemView.findViewById(R.id.profilKisiAdiTextView);
            textYazi=itemView.findViewById(R.id.profilKisiAnonsTextView);
            textKonum=itemView.findViewById(R.id.profilKnumTextView);
            textTarih=itemView.findViewById(R.id.profilTarihTextView);
            textSayi=itemView.findViewById(R.id.profilLikeSayisiTextView);
            imgProfil=itemView.findViewById(R.id.profilKisiImagaView);
            imgLike=itemView.findViewById(R.id.profilLikeImageView);
            constraintLayout=itemView.findViewById(R.id.linear);
        }
    }
}
