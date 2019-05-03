package com.anonsgroup.anons;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.anonsgroup.anons.models.Anons;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class CustomAnaEkranAdapter extends RecyclerView.Adapter<CustomAnaEkranAdapter.myviewHolder> {

    public CustomAnaEkranAdapter(Context context, List<Anons> mdata) {
        this.context = context;
        this.mdata = mdata;
    }

    private ConstraintLayout geciciAcil;
    private ConstraintLayout geciciİlk;

    Context context;
    List<Anons> mdata;
    String currentUsername;
    FirebaseUser fuser=FirebaseAuth.getInstance().getCurrentUser();
    String odaIDGlobal;
    DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
    String userid2;
    String text;

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.anaekran_anons_list, viewGroup, false);
        return new myviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder myviewHolder, int i) {
        //TODO: firebaseden çekilecek kardeşşşşşşş.
        final String[] url = new String[1];
        FirebaseDatabase.getInstance().getReference("users").child(mdata.get(i).getUserId()).child("profilUrl").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                url[0] = dataSnapshot.getValue().toString();
                if (url[0].equals("default"))
                    myviewHolder.profilFotograf.setImageResource(R.drawable.kullaniciprofildefault);
                else
                    Glide.with(context).load(url[0]).into(myviewHolder.profilFotograf);
                if (url[0].equals("default"))
                    myviewHolder.aProfilFoto.setImageResource(R.drawable.kullaniciprofildefault);
                else
                    Glide.with(context).load(url[0]).into(myviewHolder.aProfilFoto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myviewHolder.kisi.setText(mdata.get(i).getUsername());
        myviewHolder.metin.setText(mdata.get(i).getText());
        myviewHolder.konum.setText(mdata.get(i).getLocation());
        myviewHolder.tarih.setText("" + mdata.get(i).getDate());


        myviewHolder.aKisi.setText(mdata.get(i).getUsername());
        myviewHolder.aMetin.setText(mdata.get(i).getText());
        myviewHolder.aKonum.setText(mdata.get(i).getLocation());
        myviewHolder.aTarih.setText("" + mdata.get(i).getDate());

        if (!mdata.get(i).isSeen())
            myviewHolder.begeniFotograf.setImageResource(R.drawable.ic_bildiri);

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


    public class myviewHolder extends RecyclerView.ViewHolder {
        LinearLayout l;
        ConstraintLayout c;

        ImageView profilFotograf, begeniFotograf, aProfilFoto, cevaplaButton;
        TextView kisi, metin, konum, tarih, aTarih, aMetin, aKonum, aKisi;
        ConstraintLayout acilanLayout;
        ConstraintLayout ilkLayout;
        EditText cevapEditText;

        //boolean kontrol=false;
        public myviewHolder(@NonNull final View itemView) {
            super(itemView);
            profilFotograf = itemView.findViewById(R.id.anaEkranKisiImageView);
            kisi = itemView.findViewById(R.id.anaekranKisiAdiTextView);
            metin = itemView.findViewById(R.id.anaekranKisiAnonsTextView);
            konum = itemView.findViewById(R.id.anaekranKnumTextView);
            tarih = itemView.findViewById(R.id.anaekranTarihTextView);
            begeniFotograf = itemView.findViewById(R.id.anaekranYeniBildiriImageView);
            cevaplaButton = itemView.findViewById(R.id.cevaplaButton);


            aProfilFoto = itemView.findViewById(R.id.aProfilFoto);
            aKisi = itemView.findViewById(R.id.aKisi);
            aKonum = itemView.findViewById(R.id.aKonum);
            aMetin = itemView.findViewById(R.id.aMetin);
            aTarih = itemView.findViewById(R.id.aTarih);
            //TODO: Burası Scroolling olarak tekrar yapılcak
            cevapEditText = itemView.findViewById(R.id.cevapEditText);
            cevapEditText.setScroller(new Scroller(context));
            cevapEditText.setMaxLines(3);
            cevapEditText.setVerticalScrollBarEnabled(true);
            cevapEditText.setMovementMethod(new ScrollingMovementMethod());

            acilanLayout = itemView.findViewById(R.id.acilanLayout);
            ilkLayout = itemView.findViewById(R.id.linear);
            ilkLayout.setOnClickListener(v -> {
                //TODO: Veritabanında görüldü alanı değiştirilecek.
                if (begeniFotograf.getDrawable() != null)
                    begeniFotograf.setImageDrawable(null);
                acilanLayout.setVisibility(View.VISIBLE);
                ilkLayout.setVisibility(View.GONE);
                if (geciciAcil != acilanLayout && geciciAcil != null && geciciAcil.getVisibility() == View.VISIBLE) {
                    geciciAcil.setVisibility(View.GONE);
                    geciciİlk.setVisibility(View.VISIBLE);
                }
                geciciAcil = acilanLayout;
                geciciİlk = ilkLayout;
            });
            acilanLayout.setOnClickListener(v -> {
                if (ilkLayout.getVisibility() == View.GONE) {
                    ilkLayout.setVisibility(View.VISIBLE);
                    acilanLayout.setVisibility(View.GONE);
                }
            });




            cevaplaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userid2 = mdata.get(getAdapterPosition()).getUsername();
                    text = cevapEditText.getText().toString();

                    currentUsername = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    String odaid;
                    if (currentUsername.compareTo(userid2) > 0) {
                        odaid = currentUsername + "-" + userid2;
                    } else {
                        odaid = userid2 + "-" + currentUsername;
                    }
                    odaIDGlobal = odaid;

                    Toast.makeText(context, "Tamamadır", Toast.LENGTH_SHORT).show();

                    sendMessage("15294789585564643482588464648948387638431537815628496284274531863862485647"+metin.getText());

                    sendMessage(text);
                    metin.setText("");
                    cevapEditText.setText("");
                    FirebaseDatabase.getInstance().getReference("messages").child(odaIDGlobal).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            System.out.println("BAŞIIIIIII");
                            System.out.println(userid2+"---"+mdata.get(getAdapterPosition()).getUserId());
                            if (dataSnapshot.exists()) {

                                FirebaseDatabase.getInstance().getReference().child("Rooms").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        System.out.println("2.basşıııı");

                                        if (!dataSnapshot.child(fuser.getDisplayName()).hasChild(userid2)) {
                                            int remainingMessage = 20;
                                            reference.child("Rooms").child(fuser.getDisplayName()).child(userid2).child("uid").setValue(mdata.get(getAdapterPosition()).getUserId());
                                            reference.child("Rooms").child(userid2).child(fuser.getDisplayName()).child("uid").setValue(fuser.getUid());
                                            reference.child("RemainingMessages").child(odaIDGlobal).child("remainingMessages").setValue(remainingMessage);
                                        }
                                        System.out.println("2.sonuuuuu");
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            System.out.println("SONUUUUU");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            });


        }
    }

    void sendMessage(String msg) {
       /* String msg = text;*/
        if (!TextUtils.isEmpty(msg)) {
            String current = "messages/" + odaIDGlobal;
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("message", msg);
            hashMap.put("send", false);
            hashMap.put("sender", currentUsername);
            hashMap.put("receiver", userid2);
            hashMap.put("time", ServerValue.TIMESTAMP);

            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
            String mesajid = UUID.randomUUID().toString();

            HashMap map = new HashMap();
            map.put(current + "/" + mesajid, hashMap);
            System.out.println("UPADETEEET YAPIYO");
            reference2.updateChildren(map, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.d("Chat_Log", databaseError.getMessage().toString());
                    }
                }
            });

        }


    }
}