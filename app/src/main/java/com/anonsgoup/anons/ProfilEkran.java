package com.anonsgoup.anons;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfilEkran.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfilEkran#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilEkran extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button geciciButton;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private TextView anonsGorGizle;
    private NestedScrollView anonslarimScrollView;

    private OnFragmentInteractionListener mListener;
    ArrayList<MobileOs> mobileOs=new ArrayList<>();
    RecyclerView recyclerView;
    Context context;

    public ProfilEkran() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilEkran.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilEkran newInstance(String param1, String param2) {
        ProfilEkran fragment = new ProfilEkran();
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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.activity_profil, container, false);
        mAuth = FirebaseAuth.getInstance();
        context = view.getContext();

        anonsGorGizle = view.findViewById(R.id.anonsGorGizleTextView);
        anonslarimScrollView = view.findViewById(R.id.anonslarimNestedScrollView);

        anonsGorGizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(anonslarimScrollView.getVisibility() == View.GONE) {
                    anonslarimScrollView.setVisibility(View.VISIBLE);
                    anonslarimScrollView.scrollTo(0,0);
                    anonsGorGizle.setText(getResources().getString(R.string.hide_anons));
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_sortupsvg);
                    anonsGorGizle.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);
                    return;
                }
                anonslarimScrollView.setVisibility(View.GONE);
                anonsGorGizle.setText(getResources().getString(R.string.show_anons));
                Drawable drawable = getResources().getDrawable(R.drawable.ic_sortsvg);
                anonsGorGizle.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);
            }
        });


        recyclerView=view.findViewById(R.id.itemlerLayout);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        mobileOs.add(new MobileOs(R.mipmap.seropng,"Askin","Gazi Mahallesi","Şarj Aleti Olan var mı ?","25.12.2018",R.mipmap.hsn,"12k"));
        mobileOs.add(new MobileOs(R.mipmap.hsn,"Askin","Gazi Mahallesi,Emniyet Mahallesi","Şarj Aleti Olan var mı ?","28.12.2018",R.mipmap.hsn,"129"));
        mobileOs.add(new MobileOs(R.mipmap.trk,"Askin","Gazi Mahallesi,Emniyet Mahallesi","Şarj Aleti Olan var mı ?","28.12.2018",R.mipmap.hsn,"129"));
        mobileOs.add(new MobileOs(R.mipmap.seropng,"Askin","Gazi Mahallesi","Şarj Aleti Olan var mı ?","25.12.2018",R.mipmap.hsn,"12k"));
        mobileOs.add(new MobileOs(R.mipmap.hsn,"Askin","Gazi Mahallesi,Emniyet Mahallesi","Şarj Aleti Olan var mı ?","28.12.2018",R.mipmap.hsn,"129"));
        mobileOs.add(new MobileOs(R.mipmap.trk,"Askin","Gazi Mahallesi,Emniyet Mahallesi","Şarj Aleti Olan var mı ?","28.12.2018",R.mipmap.hsn,"129"));
        mobileOs.add(new MobileOs(R.mipmap.seropng,"Askin","Gazi Mahallesi","Şarj Aleti Olan var mı ?","25.12.2018",R.mipmap.hsn,"12k"));
        mobileOs.add(new MobileOs(R.mipmap.hsn,"Askin","Gazi Mahallesi,Emniyet Mahallesi","Şarj Aleti Olan var mı ?","28.12.2018",R.mipmap.hsn,"129"));
        mobileOs.add(new MobileOs(R.mipmap.trk,"Askin","Gazi Mahallesi,Emniyet Mahallesi","Şarj Aleti Olan var mı ?","28.12.2018",R.mipmap.hsn,"129"));
        mobileOs.add(new MobileOs(R.mipmap.seropng,"Askin","Gazi Mahallesi","Şarj Aleti Olan var mı ?","25.12.2018",R.mipmap.hsn,"12k"));
        mobileOs.add(new MobileOs(R.mipmap.hsn,"Askin","Gazi Mahallesi,Emniyet Mahallesi","Şarj Aleti Olan var mı ?","28.12.2018",R.mipmap.hsn,"129"));
        mobileOs.add(new MobileOs(R.mipmap.trk,"Askin","Gazi Mahallesi,Emniyet Mahallesi","Şarj Aleti Olan var mı ?","28.12.2018",R.mipmap.hsn,"129"));
        CustomProfilAdapter customProfilAdapter=new CustomProfilAdapter(mobileOs,context);
        recyclerView.setAdapter(customProfilAdapter);


        geciciButton = view.findViewById(R.id.geciciButton);
        geciciButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();
                Intent intent = new Intent(view.getContext(), LoginEkran.class);
                startActivity(intent);

            }
        });
        return view;
    }


    // TODO: Rename method, update argument and hook metho into UI event
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
