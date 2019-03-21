package com.anonsgroup.anons.customViews;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.anonsgroup.anons.R;

public class ProfilViewHolder extends RecyclerView.ViewHolder {

    private ImageView profilImage, likeImageView;
    private TextView likeSayisiTextView, anonsTextView, tarihTextView, konumTextView, adTextView;

    public ProfilViewHolder(@NonNull final View itemView) {
        super(itemView);
        /////////
        profilImage = itemView.findViewById(R.id.profilKisiImagaView);
        likeImageView = itemView.findViewById(R.id.profilLikeImageView);
        likeSayisiTextView = itemView.findViewById(R.id.profilLikeSayisiTextView);
        anonsTextView = itemView.findViewById(R.id.profilKisiAnonsTextView);
        tarihTextView = itemView.findViewById(R.id.profilTarihTextView);
        konumTextView = itemView.findViewById(R.id.profilKnumTextView);
        adTextView = itemView.findViewById(R.id.profilKisiAdiTextView);
        /////////

    }

    public ImageView getProfilImage() {
        return profilImage;
    }

    public void setProfilImage(ImageView profilImage) {
        this.profilImage = profilImage;
    }

    public ImageView getLikeImageView() {
        return likeImageView;
    }

    public void setLikeImageView(ImageView likeImageView) {
        this.likeImageView = likeImageView;
    }

    public TextView getLikeSayisiTextView() {
        return likeSayisiTextView;
    }

    public void setLikeSayisiTextView(TextView likeSayisiTextView) {
        this.likeSayisiTextView = likeSayisiTextView;
    }

    public TextView getAnonsTextView() {
        return anonsTextView;
    }

    public void setAnonsTextView(TextView anonsTextView) {
        this.anonsTextView = anonsTextView;
    }

    public TextView getTarihTextView() {
        return tarihTextView;
    }

    public void setTarihTextView(TextView tarihTextView) {
        this.tarihTextView = tarihTextView;
    }

    public TextView getKonumTextView() {
        return konumTextView;
    }

    public void setKonumTextView(TextView konumTextView) {
        this.konumTextView = konumTextView;
    }

    public TextView getAdTextView() {
        return adTextView;
    }

    public void setAdTextView(TextView adTextView) {
        this.adTextView = adTextView;
    }
}
