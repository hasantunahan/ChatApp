package com.anonsgroup.anons;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    private List<Anons> nlist;
    CustomAnaEkranAdapter customAnaEkranAdapter;
    private OnFragmentInteractionListener mListener;
    private FirebaseRecyclerOptions<Anonss> recyclerOptions;
    private FirebaseRecyclerAdapter<Anonss, AnonsViewHolder> fAdapter;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private double lat,longi;
    private FirebaseUser fUser;

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
        nlist = new ArrayList<>();
        RecyclerView recyclerView=view.findViewById(R.id.anaEkranAnonsListLayout);
        fUser = FirebaseAuth.getInstance().getCurrentUser();


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        customAnaEkranAdapter=new CustomAnaEkranAdapter(view.getContext(),nlist);
        recyclerView.setAdapter(customAnaEkranAdapter);


        //Anonsların Çekildiği Kod:
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AnonsGidenKullanicilar").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    System.out.println(dataSnapshot1.toString());
                    Anons anons = dataSnapshot1.getValue(Anons.class);
                    nlist.add(anons);
                }
                customAnaEkranAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //TODO: Sorulacak ? olması gereken ""  new Dialog(context :  this) """
        epicdialog=new Dialog(view.getContext(),R.style.Kendiismim);
        yeniAnons= view.findViewById(R.id.yeniAnonsButton);

        yeniAnons.setOnClickListener(v -> showDialog());

        FirebaseDatabase.getInstance().getReference("ArkadasListesi").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                            System.out.println(snapshot.getKey());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 99);
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        Toast.makeText(getActivity().getApplicationContext(), location.getLatitude() + "-" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                    }
                });

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(createLocationRequest());

        SettingsClient client = LocationServices.getSettingsClient(getContext());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(getActivity(), locationSettingsResponse -> {
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
        });

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                    HashMap<String,Object> map = new HashMap<>();
                    longi = location.getLongitude();
                    lat = location.getLatitude();
                    map.put("longitude",location.getLongitude());
                    map.put("latitude",location.getLatitude());
                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
                    Toast.makeText(getActivity(), location.getLatitude() + "-" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                }

            }
        };

        startLocationUpdates();


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

        gonderButton=epicdialog.findViewById(R.id.mesajEkrangonderButton);
        gonderButton.setOnClickListener(v -> {
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
            String anonsId = UUID.randomUUID().toString();
            String senderId = fUser.getUid();
            String username = fUser.getDisplayName();
            String photoUrl;
            if(fUser.getPhotoUrl() != null)
                photoUrl = fUser.getPhotoUrl().toString();
            else
                photoUrl = "default";
            String city = getAddress(lat,longi,getContext());
            Anonss anons = new Anonss(senderId,city,new Date().getTime(),0,editText.getText().toString(),lat,longi,current,username,photoUrl);
            epicdialog.dismiss();
            databaseRef.child("Anonslar").child(senderId).child(anonsId).setValue(anons).addOnSuccessListener(command -> {

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
    protected LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(60000);
        locationRequest.setFastestInterval(45000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.requestLocationUpdates(createLocationRequest(),
                locationCallback,
                null /* Looper */);
    }
    public String getAddress(double latitude, double longitude,Context context) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getSubLocality()).append("-").append(address.getSubAdminArea());
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        return result.toString();


    }
}
