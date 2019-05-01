package com.anonsgroup.anons;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import com.anonsgroup.anons.Adapter.UserAdapter;
import com.anonsgroup.anons.models.ArkadaslarimModel;
import com.anonsgroup.anons.models.FirebaseUserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ArkadasListesiEkran extends AppCompatActivity {
        ImageView kapat;
    RecyclerView recyclerView;
    List<ArkadaslarimModel> nlist;
    private UserAdapter userAdapter;
    private String key;
    EditText kisiAra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arkadas_listesi_ekran);
        kapat=findViewById(R.id.arkadasListesiKapat);
        kapat.setOnClickListener(v -> finish());

        recyclerView=findViewById(R.id.arkadasListesiRecylerView);
        nlist=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(getApplicationContext(), nlist);
        recyclerView.setAdapter(userAdapter);
        readUser();

       kisiAra=findViewById(R.id.sohbetKisiaraEdittexxt);
   /*    kisiAra.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
             // kisiArama(s.toString());
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });*/



    }

    void kisiArama(String s){

        FirebaseUser fuser=FirebaseAuth.getInstance().getCurrentUser();
        Query query=FirebaseDatabase.getInstance().getReference("users").orderByChild("username")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nlist.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ArkadaslarimModel model=snapshot.getValue(ArkadaslarimModel.class);
                            if(!model.getUid().equals(fuser.getUid())){
                                nlist.add(model);
                            }
                }
                userAdapter=new UserAdapter(getApplicationContext(),nlist);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readUser() {
        FirebaseUser fuser= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference("ArkadasListesi").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            System.out.println(dataSnapshot.toString());
                            nlist.clear();
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                key= snapshot.getKey();
                                System.out.println(key);
                                FirebaseDatabase.getInstance().getReference("users").child(key).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        System.out.println(dataSnapshot.toString());
                                        FirebaseUserModel userModel = dataSnapshot.getValue(FirebaseUserModel.class);
                                        ArkadaslarimModel user = new ArkadaslarimModel(userModel.getUsername(), userModel.getProfilUrl(),key, userModel.getSummInfo());
                                        nlist.add(user);
                                        userAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





    }
}
