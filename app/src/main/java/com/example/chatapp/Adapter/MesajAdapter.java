package com.example.chatapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatapp.Models.Chat;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MesajAdapter extends RecyclerView.Adapter<MesajAdapter.ViewHolder> {
  public  static final int MSG_TYPE_LEFT=0;
    public  static final int MSG_TYPE_RIGHT=1;
    private Context context;
    private List<Chat> mChat;
    private String imageURL;
    FirebaseUser fuser;

    public MesajAdapter(Context context, List<Chat> mchat,String imageURL) {
        this.context = context;
        this.mChat = mchat;
        this.imageURL=imageURL;
    }

    @NonNull
    @Override
    public MesajAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i ==MSG_TYPE_RIGHT){
        View view= LayoutInflater.from(context).inflate(R.layout.mesaj_item_sag_baloncuk,viewGroup,false);
        return new MesajAdapter.ViewHolder(view);
        }else{
            View view= LayoutInflater.from(context).inflate(R.layout.mesaj_item_sol_baloncuk,viewGroup,false);
            return new MesajAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MesajAdapter.ViewHolder viewHolder, int i) {
     Chat chat=mChat.get(i);
     viewHolder.show_mesaj.setText(chat.getMesaj());

     if(imageURL.equals("default")){
         viewHolder.profil.setImageResource(R.drawable.common_google_signin_btn_icon_light_focused);
     }else{
         Glide.with(context).load(imageURL).into(viewHolder.profil);
     }

    if(i == mChat.size()-1){
        if(chat.isIsseen()){
            viewHolder.seen.setText("Görüldü");
        }else {
            viewHolder.seen.setText(("Görülmedi"));
        }
    }else {
        viewHolder.seen.setVisibility(View.GONE);
    }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView show_mesaj;
        public ImageView profil;

        public TextView seen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_mesaj=itemView.findViewById(R.id.showMesaj);
            profil=itemView.findViewById(R.id.bProfil);
            seen = itemView.findViewById(R.id.seenTextView);

        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }else
        {
            return MSG_TYPE_LEFT;
        }
    }

}
