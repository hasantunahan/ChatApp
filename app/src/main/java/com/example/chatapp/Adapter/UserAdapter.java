package com.example.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatapp.MesajActivity;
import com.example.chatapp.Models.Chat;
import com.example.chatapp.Models.User;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.w3c.dom.Text;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<User> mUser;
    private boolean ischat;
    String sonmesajGlobal;

    public UserAdapter(Context context, List<User> mUser, boolean ischat) {
        this.context = context;
        this.mUser = mUser;
        this.ischat = ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.arkadas_listesi,viewGroup,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
         final User user=mUser.get(i);
        viewHolder.username.setText(user.getName());
        if(user.getImageURL().equals("default")){
            viewHolder.profil.setImageResource(R.drawable.wallpapere);
        }else{
            Glide.with(context).load(user.getImageURL()).into(viewHolder.profil);
        }

        if (ischat){
            sonMesaj(user.getId(),viewHolder.sonMesaj);
        }else{
            viewHolder.sonMesaj.setVisibility(View.GONE);
        }

        if(ischat){
            if(user.getStatus().equals("online")){
                viewHolder.imgici.setVisibility(View.VISIBLE);
                viewHolder.imgdisi.setVisibility(View.GONE);
            }else{
                viewHolder.imgici.setVisibility(View.GONE);
                viewHolder.imgdisi.setVisibility(View.VISIBLE);
            }
        }else{
            viewHolder.imgici.setVisibility(View.GONE);
            viewHolder.imgdisi.setVisibility(View.GONE);
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MesajActivity.class);
                intent.putExtra("userid",user.getId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView username;
        public ImageView profil;
        private ImageView imgdisi;
        private ImageView imgici;
        private TextView sonMesaj;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          username=itemView.findViewById(R.id.arkadasListTextView);
          profil=itemView.findViewById(R.id.arkadasProfilPhoto);
          imgdisi=itemView.findViewById(R.id.imgCevrimDisi);
          imgici=itemView.findViewById(R.id.imgCevrimici);
          sonMesaj=itemView.findViewById(R.id.sonmesajtextview);

        }
    }

    private void sonMesaj(final String userid, final TextView sonmesajim){
           sonmesajGlobal="default";
        final FirebaseUser fuser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Chats");
        if(fuser != null) {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Chat chat = snapshot.getValue(Chat.class);
                        if (chat.getReceiver().equals(fuser.getUid()) && chat.getSender().equals(userid) ||
                                chat.getReceiver().equals(userid) && chat.getSender().equals(fuser.getUid())) {
                            sonmesajGlobal = chat.getMesaj();
                        }
                    }
                    switch (sonmesajGlobal) {
                        case "default":
                            sonmesajim.setText("Mesaj bulunmamaktadir");
                            break;
                        default:
                            sonmesajim.setText(sonmesajGlobal);

                    }
                    sonmesajGlobal = "default";

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


}
