package com.example.chatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.chatapp.Adapter.MesajAdapter;
import com.example.chatapp.Fragments.APIService;
import com.example.chatapp.Models.Chat;
import com.example.chatapp.Models.User;
import com.example.chatapp.Notification.Client;
import com.example.chatapp.Notification.Data;
import com.example.chatapp.Notification.MyResponse;
import com.example.chatapp.Notification.Sender;
import com.example.chatapp.Notification.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MesajActivity extends AppCompatActivity {

     ImageView profil;
     TextView username;

     FirebaseUser fuser;
     DatabaseReference reference;

     EditText mesajGir;
     ImageView gonderButton;

     MesajAdapter mesajAdapter;
     List<Chat> mchat;

     RecyclerView recyclerView;

     Intent intent;

     ValueEventListener seenListener;
      String userid2;
      APIService apiService;
      boolean notify=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaj);

        Toolbar toolbar=findViewById(R.id.toolbar);


        profil=findViewById(R.id.profilImage);
        username=findViewById(R.id.main2usernameEdittext);
        mesajGir=findViewById(R.id.mesajGirEditText);
        gonderButton=findViewById(R.id.mesajGonderButton);


        apiService= Client.getClient("https://fcm.googleapis.com/").create(APIService.class);


        recyclerView=findViewById(R.id.mesajRecylerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        intent=getIntent();
        //TODO: Burdaki hataya bakılacak
        final String userid=intent.getStringExtra("userid");
        userid2=userid;
        fuser= FirebaseAuth.getInstance().getCurrentUser();

        gonderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify=true;
                String msg=mesajGir.getText().toString();
                if(!msg.equals("")){
                    sendMessage(fuser.getUid(),userid2,msg);
                }else{
                    Toast.makeText(MesajActivity.this, "Gönderemediniz", Toast.LENGTH_SHORT).show();
                }
                mesajGir.setText("");
            }
        });



        reference= FirebaseDatabase.getInstance().getReference("Users").child(userid2);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                username.setText(user.getName());
                if(user.getImageURL().equals("default")){
                    profil.setImageResource(R.drawable.ic_send_black_24dp);
                }else{
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(profil);
                }

                readMesaj(fuser.getUid(),userid2,user.getImageURL());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seenMessage(userid);

    }

    private void seenMessage(final String userId){
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot :  dataSnapshot.getChildren()){
                    Chat chat= snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(fuser.getUid() ) && chat.getSender().equals(userId)){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage(String sender, final String receiver, String mesaj){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("mesaj",mesaj);
        hashMap.put("isseen",false);

        reference.child("Chats").push().setValue(hashMap);

        final DatabaseReference chatref= FirebaseDatabase.getInstance().getReference("ChatList")
                                                .child(fuser.getUid())
                                                .child(userid2);

        chatref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    chatref.child("id").setValue(userid2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DatabaseReference chatref1=FirebaseDatabase.getInstance().getReference("ChatList")
                .child(receiver).child(fuser.getUid());
        chatref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    chatref1.child("id").setValue(fuser.getUid());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

         final String msg=mesaj;
         reference=FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
         reference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              User user=dataSnapshot.getValue(User.class);
              if(notify){
              sendNotification(receiver,user.getName(),msg);}
              notify=false;
             }
             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
        System.out.println("receiver" +receiver);
    }


       private void sendNotification(String receiver, final String username, final String message){
        DatabaseReference tokens=FirebaseDatabase.getInstance().getReference("Tokens");
        Query query=tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token=snapshot.getValue(Token.class);
                    Data data=new Data(fuser.getUid(),R.mipmap.ic_launcher,username+":"+message,"Yeni Mesaj",userid2);
                    System.out.println("karsi"+userid2);
                    Sender sender=new Sender(data,token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if(response.code() ==200){
                                        if(response.body().success!=1){
                                            Toast.makeText(MesajActivity.this, "Bildirim Hatasi", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMesaj(final String myid, final String userid, final String imageUrl){
        mchat=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat=snapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(myid) && chat.getSender().equals(userid) || chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        mchat.add(chat);
                    }
                    mesajAdapter=new MesajAdapter(MesajActivity.this,mchat,imageUrl);
                    recyclerView.setAdapter(mesajAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void CurrentUser(String userid){
        SharedPreferences.Editor editor=getSharedPreferences("PREFS",MODE_PRIVATE).edit();
        editor.putString("currentUser",userid);
        editor.apply();
    }

    private void status(String status){
        reference=FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("status",status);
        reference.updateChildren(hashMap);

    }

    @Override
    protected void onResume() {
        super.onResume();
      //  if(fuser != null) {
            status("online");
     //   }
        CurrentUser(userid2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(seenListener);
        status("offline");
        CurrentUser("none");
    }

}
