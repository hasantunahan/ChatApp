package com.anonsgroup.anons;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.anonsgroup.anons.models.MesajModel;
import com.anonsgroup.anons.models.Messages;
import com.anonsgroup.anons.models.SenderUsers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MesajlasmaAdapter extends RecyclerView.Adapter<MesajlasmaAdapter.ViewHolders> {
        Context context;
        FirebaseUser fuser=FirebaseAuth.getInstance().getCurrentUser();


    public MesajlasmaAdapter(Context context, List<MesajModel> mdata) {
        this.context = context;
        this.mChat = mdata;
    }

    List<MesajModel> mChat;

        final int MSG_TYPE_LEFT=0;
        final int MSG_TYPE_RIGHT=1;
        final int MSG_TYPE_BOTTOM=2;


@NonNull
@Override
public MesajlasmaAdapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    if(i ==MSG_TYPE_RIGHT){
        View view= LayoutInflater.from(context).inflate(R.layout.mesaj_item_sag_baloncuk,viewGroup,false);
        return new MesajlasmaAdapter.ViewHolders(view);
    }else if (i == MSG_TYPE_LEFT){
        View view= LayoutInflater.from(context).inflate(R.layout.mesaj_item_sol_baloncuk,viewGroup,false);
        return new MesajlasmaAdapter.ViewHolders(view);
    }
    else {
        View view= LayoutInflater.from(context).inflate(R.layout.mesaj_item_bottom_baloncuk,viewGroup,false);
        return new MesajlasmaAdapter.ViewHolders(view);
    }
        }

@Override
public void onBindViewHolder(@NonNull MesajlasmaAdapter.ViewHolders viewHolders, int i) {
         MesajModel c=mChat.get(i);
         viewHolders.mesajText.setText(c.getMessage().replaceAll("(15294789585564643482588464648948387638431537815628496284274531863862485647)",""));
        }

@Override
public int getItemCount() {
        return mChat.size();
        }

public static class ViewHolders extends RecyclerView.ViewHolder{
      public static TextView mesajText;


    public ViewHolders(@NonNull View itemView) {
        super(itemView);
        mesajText=itemView.findViewById(R.id.mesajItemSagTextView);
    }


}

    @Override
    public int getItemViewType(int position) {
        if(mChat.get(position).getMessage().contains("15294789585564643482588464648948387638431537815628496284274531863862485647")  ){
        return  MSG_TYPE_BOTTOM;
        }
        if(mChat.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())){
            return MSG_TYPE_RIGHT;
        }else
        {
            return MSG_TYPE_LEFT;
        }
    }
}

