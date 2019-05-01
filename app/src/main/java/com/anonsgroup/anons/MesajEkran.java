package com.anonsgroup.anons;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.anonsgroup.anons.models.FirebaseUserModel;
import com.anonsgroup.anons.models.MesajModel;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class MesajEkran extends AppCompatActivity {
    CircleImageView photo;
    TextView username;
    Intent intent;
    ImageView arkadas;
    FirebaseUser fuser;
    DatabaseReference reference;
    DatabaseReference reference1;
    MesajlasmaAdapter mesajAdapter;
    List<MesajModel> mchat;

    ImageView gonder;
    TextView metin;
    FirebaseAuth mAuth;
    String mCurrentid;
    String userid2;
    String currentUsername;
    String odaIDGlobal;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaj_ekran);
        gonder=findViewById(R.id.mesajEkrangonderButton);
        metin=findViewById(R.id.mesajekranMetin);
        mAuth=FirebaseAuth.getInstance();
        mCurrentid=mAuth.getCurrentUser().getUid();
        reference=FirebaseDatabase.getInstance().getReference();
        mchat=new ArrayList<>();
        recyclerView=findViewById(R.id.mesajEkraRecylerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        //Alttan başlatıyor
        //linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mesajAdapter = new MesajlasmaAdapter(MesajEkran.this, mchat);
        recyclerView.setAdapter(mesajAdapter);
        arkadas=findViewById(R.id.mesajEkranArkadasMi);
        arkadas.setOnClickListener(v -> arkadas.setImageResource(R.drawable.ic_checkfriends));
        photo=findViewById(R.id.mesajEkranPhoto);
        username=findViewById(R.id.mesajEkranIsimSoyisim);
        intent=getIntent();
        final String userid=intent.getStringExtra("userid");
       userid2=userid;

        fuser= FirebaseAuth.getInstance().getCurrentUser();
        //yazışılan kişnin bilgileri çekiliyor.
        if (fuser.getUid() !=null){
            FirebaseDatabase.getInstance().getReference("users").orderByChild("username").equalTo(userid)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            // username.setText(userid);
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                FirebaseUserModel user=snapshot.getValue(FirebaseUserModel.class);
                                username.setText(user.getUsername());
                                if (user.getProfilUrl().equals("default")) {
                                    photo.setImageResource(R.drawable.kullaniciprofildefault);
                                } else {
                                    Glide.with(MesajEkran.this).load(user.getProfilUrl()).into(photo);
                                }
                            }
                            readMesaj(currentUsername,userid2);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

        }

       currentUsername= FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        String odaid;
        if(currentUsername.compareTo(userid2)>0) {
            odaid=currentUsername+"-"+userid2;
        }
        else{
            odaid=userid2+"-"+currentUsername;
        }
        odaIDGlobal=odaid;
///Oda oluşturma
        FirebaseDatabase.getInstance().getReference().child("Rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.child(fuser.getDisplayName()).hasChild(userid2)){
                    int remainingMessage=20;
                    /*HashMap chatAddMap =new HashMap();
                    String profilUrl="default";
                    HashMap altmap=new HashMap();
                    altmap.put("profilUrl",profilUrl);
                    HashMap ustmap=new HashMap();
                    ustmap.put("profilUrl",profilUrl);
                    chatAddMap.put(currentUsername,ustmap);
                    chatAddMap.put(userid2,altmap);
                    chatAddMap.put("remainingMessage",remainingMessage);
                    //chatAddMap.put("username",username.getText().toString());

                    HashMap ChatUserMap=new HashMap();
                    ChatUserMap.put("Rooms/" +odaid, chatAddMap);
                    //ChatUserMap.put("Rooms/"+odaid+"/"+currentUsername,chatAddMap);
                    reference.updateChildren(ChatUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if(databaseError !=null){
                                Log.d("CHAT_LOG",databaseError.getMessage().toString());
                            }
                        }
                    });  */

                    reference.child("Rooms").child(fuser.getDisplayName()).child(userid2).child("profilUrl").setValue("default");
                    reference.child("Rooms").child(userid2).child(fuser.getDisplayName()).child("profilUrl").setValue("default");
                    reference.child("RemainingMessages").child(odaIDGlobal).child("remainingMessages").setValue(remainingMessage);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
                metin.setText("");
            }
        });





    }

    private void readMesaj(final String myid, final String userid){
       FirebaseDatabase.getInstance().getReference("messages").child(odaIDGlobal).orderByChild("time")
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();

                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MesajModel chat = snapshot.getValue(MesajModel.class);
                        if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) || chat.getReceiver().equals(userid) && chat.getSender().equals(myid)) {
                            mchat.add(chat);
                            mesajAdapter.notifyDataSetChanged();
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    private void sendMessage(){



        String msg=metin.getText().toString();
        if(!TextUtils.isEmpty(msg)){
            String current="messages/"+odaIDGlobal;
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("message",msg);
            hashMap.put("seend",false);
            hashMap.put("sender",currentUsername);
            hashMap.put("receiver",userid2);
            hashMap.put("time", ServerValue.TIMESTAMP);

            DatabaseReference reference2=FirebaseDatabase.getInstance().getReference();
            String mesajid=UUID.randomUUID().toString();

            HashMap map=new HashMap();
            map.put(current+"/"+mesajid,hashMap);
            reference2.updateChildren(map, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if(databaseError !=null){
                        Log.d("Chat_Log",databaseError.getMessage().toString());
                    }
                }
            });

        }




        /*String gelenUsername=intent.getStringExtra("userid");
       // String roomId= UUID.randomUUID().toString();
        String mesajid=UUID.randomUUID().toString();
        MesajModel mesajModel=new MesajModel(messages,sender,receiver);
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Messages").child(gelenUsername);
        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put(mesajid,mesajModel);
        hashMap.put("remainingMessages",20);
        reference.setValue(hashMap);
       hashMap.clear();
        reference=FirebaseDatabase.getInstance().getReference("Rooms").child(fuser.getUid()).child(gelenUsername);;
        hashMap.put("receiverUsername",username.getText().toString());
        hashMap.put("receiverPhoto","default");
        reference.setValue(hashMap);*/



    }


}
