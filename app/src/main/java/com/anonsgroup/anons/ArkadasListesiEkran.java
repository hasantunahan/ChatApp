package com.anonsgroup.anons;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.anonsgroup.anons.Adapter.UserAdapter;
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
    List<FirebaseUserModel> nlist;
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
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nlist.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    FirebaseUserModel user=snapshot.getValue(FirebaseUserModel.class);
                    assert fuser != null;
                    assert user != null;
                    if(!user.getUsername().equals(fuser.getUid())){
                        nlist.add(user);
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
}
