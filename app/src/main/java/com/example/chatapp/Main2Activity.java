package com.example.chatapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatapp.Fragments.ChatFragment;
import com.example.chatapp.Fragments.ProfilFragment;
import com.example.chatapp.Fragments.UserFragment;
import com.example.chatapp.Models.Chat;
import com.example.chatapp.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Main2Activity extends AppCompatActivity {
    TextView username;
    ImageView profilimg,kapat;
    FirebaseUser fuser;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        username=findViewById(R.id.main2usernameEdittext);
        profilimg=findViewById(R.id.profilImage);
        kapat=findViewById(R.id.anakapatButton);
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              User user=dataSnapshot.getValue(User.class);
              username.setText(user.getName());
              if(user.getImageURL().equals("default")){
                 profilimg.setImageResource(R.mipmap.ic_launcher);
              }else{
                  Glide.with(getApplicationContext()).load(user.getImageURL()).into(profilimg);
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        kapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Main2Activity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));*/
                FirebaseAuth.getInstance().signOut();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                   finish();
                }
            }
        });

        final TabLayout tabLayout=findViewById(R.id.anaEkranTabLayout);
        final ViewPager viewPager=findViewById(R.id.anaViewPager);


        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
               int unRead=0;
               for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                   Chat chat =snapshot.getValue(Chat.class);
                   if(chat.getReceiver().equals(fuser.getUid())&& !chat.isIsseen() ){
                       unRead++;
                   }
               }
               if( unRead ==0){
                   viewPagerAdapter.addFragment(new ChatFragment(),"Chats");
               }else{
                   viewPagerAdapter.addFragment(new ChatFragment(),"("+unRead+") Chats");

               }

                viewPagerAdapter.addFragment(new UserFragment(),"Kisiler");
                viewPagerAdapter.addFragment(new ProfilFragment(),"Profil");

                viewPager.setAdapter(viewPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    class ViewPagerAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm){
            super(fm);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();
        }

        @Override
        public Fragment getItem(int i) {
           return fragments.get(i);
        }

        @Override
        public int getCount() {
           return fragments.size();
        }

        public void addFragment(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
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
       // }
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
}
