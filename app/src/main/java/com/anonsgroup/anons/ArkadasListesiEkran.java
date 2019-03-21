package com.anonsgroup.anons;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.anonsgroup.anons.Adapter.UserAdapter;
import com.anonsgroup.anons.models.ArkadaslarimModel;
import com.anonsgroup.anons.models.FirebaseUserModel;
import com.anonsgroup.anons.models.SenderUsers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ArkadasListesiEkran extends AppCompatActivity {
        ImageView kapat;
    RecyclerView recyclerView;
    List<ArkadaslarimModel> nlist;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arkadas_listesi_ekran);
        kapat=findViewById(R.id.arkadasListesiKapat);
        kapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView=findViewById(R.id.arkadasListesiRecylerView);
        nlist=new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        readUser();



    }

    private void readUser() {
        FirebaseUser fuser= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference("ArkadasListesi").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        System.out.println(dataSnapshot.toString());

                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String key= snapshot.getKey();
                            System.out.println(key);
                            FirebaseDatabase.getInstance().getReference("users").child(key).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    nlist.clear();
                                    System.out.println(dataSnapshot.toString());
                                    FirebaseUserModel userModel = dataSnapshot.getValue(FirebaseUserModel.class);
                                    ArkadaslarimModel user = new ArkadaslarimModel(userModel.getUsername(), userModel.getProfilUrl(), userModel.getSummInfo());
                                    nlist.add(user);
                                    userAdapter = new UserAdapter(getApplicationContext(), nlist);
                                    recyclerView.setAdapter(userAdapter);
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
