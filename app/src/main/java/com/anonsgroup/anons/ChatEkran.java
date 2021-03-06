package com.anonsgroup.anons;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.anonsgroup.anons.Adapter.UserAdapter;
import com.anonsgroup.anons.Notification.Token;
import com.anonsgroup.anons.models.Anons;
import com.anonsgroup.anons.models.ArkadaslarimModel;
import com.anonsgroup.anons.models.FirebaseUserModel;
import com.anonsgroup.anons.models.MesajModel;
import com.anonsgroup.anons.models.SenderUsers;
import com.anonsgroup.anons.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatEkran extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FloatingActionButton mesajAt;

    private OnFragmentInteractionListener mListener;

    public ChatEkran() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatEkran.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatEkran newInstance(String param1, String param2) {
        ChatEkran fragment = new ChatEkran();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<User> mUsers;
    FirebaseUser fuser;
    DatabaseReference reference;
    private List<ArkadaslarimModel> userList;
    private List<ArkadaslarimModel> userList2;

    private EditText kisiAra;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chat_ekran, container, false);

        recyclerView=view.findViewById(R.id.chatRecylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fuser=FirebaseAuth.getInstance().getCurrentUser();
        //Sohbete eklemek için
        userList=new ArrayList<>();
        userList2=new ArrayList<>();
        mesajAt=view.findViewById(R.id.mesajAtButton);
        mesajAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(),ArkadasListesiEkran.class);
                startActivity(intent);
            }
        });

        adapter = new UserAdapter(getContext(),userList,userList2);
        recyclerView.setAdapter(adapter);


        FirebaseDatabase.getInstance().getReference("Rooms").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                userList2.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    /*userList.add(new ArkadaslarimModel(snapshot.getKey(),snapshot.getChildren().iterator().next().getValue().toString()));
                    System.out.println("HASOOOOOOOOOOOO"+snapshot.getValue().toString());*/
                    String key= snapshot.getChildren().iterator().next().getValue().toString();
                    System.out.println("AAAA"+key);
                    FirebaseDatabase.getInstance().getReference("users").child(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            System.out.println(dataSnapshot.toString());
                            FirebaseUserModel userModel = dataSnapshot.getValue(FirebaseUserModel.class);
                            ArkadaslarimModel user = new ArkadaslarimModel(userModel.getUsername(), userModel.getProfilUrl());
                            userList.add(user);
                            userList2.add(user);
                          //  Collections.reverse(userList);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
              // adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        kisiAra=view.findViewById(R.id.sohbetKisiaraEdittexxt);
        kisiAra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });

        updateToken(FirebaseInstanceId.getInstance().getToken());

        return view;
    }

    private void updateToken(String token){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1=new Token();
        reference.child(fuser.getUid()).setValue(token1);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
