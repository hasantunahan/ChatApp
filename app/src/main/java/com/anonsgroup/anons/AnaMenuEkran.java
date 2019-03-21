package com.anonsgroup.anons;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anonsgroup.anons.customViews.AnonsViewHolder;
import com.anonsgroup.anons.models.Anons;
import com.anonsgroup.anons.models.Anonss;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnaMenuEkran.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnaMenuEkran#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnaMenuEkran extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Dialog epicdialog;
    private FloatingActionButton yeniAnons;
    private SeekBar mesafeSeekBar;
    private TextView mesafeTextView;
    private int min=5,max=999,current=10;
    private ImageView gonderButton;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public AnaMenuEkran() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnaMenuEkran.
     */
    // TODO: Rename and change types and number of parameters
    public static AnaMenuEkran newInstance(String param1, String param2) {
        AnaMenuEkran fragment = new AnaMenuEkran();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ana_menu_ekran, container, false);
        // Inflate the layout for this fragment

       //TODO: Sorulacak ? olması gereken ""  new Dialog(context :  this) """
        epicdialog=new Dialog(view.getContext(),R.style.Kendiismim);
        yeniAnons= view.findViewById(R.id.yeniAnonsButton);

        yeniAnons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


        return view;
    }
    public void showDialog(){
        epicdialog.setContentView(R.layout.yeni_anons);
        epicdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicdialog.show();

        mesafeTextView=epicdialog.findViewById(R.id.seekBarSayisi);
        mesafeSeekBar=epicdialog.findViewById(R.id.mesafeSeekBar);
        mesafeSeekBar.setMax(max-min);
        mesafeSeekBar.setProgress(current-min);
        mesafeTextView.setText(""+current+"metre");
        EditText editText = epicdialog.findViewById(R.id.yeniAnonsEditText);

        gonderButton=epicdialog.findViewById(R.id.gonderButton);
        gonderButton.setOnClickListener(v -> {
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
            String anonsId = UUID.randomUUID().toString();

            String senderId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Anonss anons = new Anonss(senderId,"yok",new Date().getTime(),0,editText.getText().toString());
            epicdialog.dismiss();
            databaseRef.child("Anons").child(senderId).child(anonsId).setValue(anons).addOnSuccessListener(command -> {
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> Toast.makeText(getContext(), "Anonsunuz iletilmiştir.", Toast.LENGTH_SHORT).show());
            });
        });

        mesafeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                current=progress+min;
                mesafeTextView.setText(""+current+"metre");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


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
