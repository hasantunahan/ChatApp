package com.anonsgroup.anons;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anonsgroup.anons.customViews.AnonsViewHolder;
import com.anonsgroup.anons.customViews.ProfilViewHolder;
import com.anonsgroup.anons.database.KullaniciIslemler;
import com.anonsgroup.anons.database.VeriTabaniDb;
import com.anonsgroup.anons.models.Anons;
import com.anonsgroup.anons.models.Anonss;
import com.anonsgroup.anons.models.FirebaseUserModel;
import com.anonsgroup.anons.models.User;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfilEkran.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfilEkran#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilEkran extends Fragment implements NavigationView.OnNavigationItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private TextView anonsGorGizle;
    private NestedScrollView anonslarimScrollView;
    private ImageView settingImageView;
    private NavigationView navigationView;
    static public DrawerLayout mdrawerLayout;
    private TextView adSoyadTextView;
    private TextView profildurumTextView;
    private ImageView profilPhotoImageView;
    private ImageView profilBackgroundImageView;
    private OnFragmentInteractionListener mListener;
    private ArrayList<Anons> anons=new ArrayList<>();
    private RecyclerView recyclerView;
    private Context context;
   // public static FirebaseUserModel userModel ;
    private FirebaseRecyclerOptions<Anonss> recyclerOptions;
    private FirebaseRecyclerAdapter<Anonss, ProfilViewHolder> fAdapter;
    ImageView navigationProfilView;
    TextView navigationUsername;
    private TextView bildirimSayiTextView;
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
        settingImageView = view.findViewById(R.id.settingsImageButton);
        adSoyadTextView = view.findViewById(R.id.profilAdSayodTextView);
        bildirimSayiTextView = view.findViewById(R.id.bildirimSayisiTextView);
        profildurumTextView = view.findViewById(R.id.profildurumTextView);
        profilPhotoImageView  = view.findViewById(R.id.profilAvatarCircleImage);
        profilBackgroundImageView = view.findViewById(R.id.profilBackgroundImageView);

        final DrawerLayout drawerLayout = view.findViewById(R.id.drawerLayout);
        settingImageView.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.START));


        anonsSayacYazdir();

            //TODO: resimler databaseden çekilip imageview a konacak ve diğer bilgiler.
        //TODO: Şerefin yaptığı açılan pencere buna entegre edilecek.

        anonsGorGizle = view.findViewById(R.id.anonsGorGizleTextView);
        anonslarimScrollView = view.findViewById(R.id.anonslarimNestedScrollView);

        //reklam alanının kapanıp anonların gözüktüğü yer.
        anonsGorGizle.setOnClickListener(v -> {
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
        });

        navigationView= view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mdrawerLayout = view.findViewById(R.id.drawerLayout);
        View headerView = navigationView.getHeaderView(0);
        navigationUsername = headerView.findViewById(R.id.nav_header_kullanici_adi);
        navigationProfilView = headerView.findViewById(R.id.nav_header_profil_foto);

        ActionBarDrawerToggle drawerToggle=
                new ActionBarDrawerToggle(this.getActivity(),mdrawerLayout,R.string.drawer_open,R.string.drawer_close);
        mdrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        //kendi anonslarının çekildiği kod
        RecyclerView recyclerView=view.findViewById(R.id.profilEkranAnonsListRecycler);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Anonslar").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).orderByChild("date").getRef();
        recyclerOptions = new FirebaseRecyclerOptions.Builder<Anonss>()
                .setQuery(ref,Anonss.class).build();
        fAdapter = new FirebaseRecyclerAdapter<Anonss, ProfilViewHolder>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ProfilViewHolder holder, int position, @NonNull Anonss model) {
                //TODO: BURASI localden Çekilecek
                holder.getProfilImage().setImageResource(R.drawable.kullaniciprofildefault);
                holder.getAdTextView().setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                holder.getAnonsTextView().setText(model.getText());
                holder.getKonumTextView().setText(model.getLocation());
                holder.getTarihTextView().setText(model.getDate()+"");
            }

            @NonNull
            @Override
            public ProfilViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater=LayoutInflater.from(getActivity().getApplicationContext());
                View v=inflater.inflate(R.layout.profil_anons_list,viewGroup,false);
                return new ProfilViewHolder(v);
            }
        };
        //CustomAnaEkranAdapter customAnaEkranAdapter=new CustomAnaEkranAdapter(view.getContext(),nlist);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        fAdapter.startListening();
        recyclerView.setAdapter(fAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        //TODO: Navigation drawer gelince bu burdan kalkıcak.



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
    public void onResume() {
        //Profil Bilgilerinin Çekildği kod:
        FirebaseDatabase.getInstance().getReference("users").orderByChild("email").equalTo(mAuth.getCurrentUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        //userModel = snapshot.getValue(FirebaseUserModel.class);
                        profildurumTextView.setText(snapshot.child("summInfo").getValue().toString());
                        String adSoyad = snapshot.child("name").getValue().toString() + " " + snapshot.child("surname").getValue().toString();
                        String username= snapshot.child("username").getValue().toString();
                        adSoyadTextView.setText(adSoyad);
                        navigationUsername.setText(username);
                        String background = snapshot.child("backgroundUrl").getValue().toString();
                        String profil = snapshot.child("profilUrl").getValue().toString();
                        if(background.equals("default"))
                            profilBackgroundImageView.setImageResource(R.drawable.defaultback);
                        else
                            Glide.with(getActivity().getApplicationContext()).load(background).into(profilBackgroundImageView);

                        if(profil.equals("default")) {
                            profilPhotoImageView.setImageResource(R.drawable.kullaniciprofildefault);
                            navigationProfilView.setImageResource(R.drawable.kullaniciprofildefault);
                        }
                        else {
                            Glide.with(getActivity().getApplicationContext()).load(profil).into(profilPhotoImageView);
                            Glide.with(getActivity().getApplicationContext()).load(profil).into(navigationProfilView);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onResume();
        if(fAdapter!=null)
            fAdapter.startListening();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        navigationViewKapat();

        switch (menuItem.getItemId()){
            case R.id.menu_item_profil_duzenle:
                Intent intent=new Intent(getContext(), ProfilDuzenleEkran.class);
                startActivity(intent);
                break;
            case R.id.menu_item_begendigin_anonslar:
                Toast.makeText(getContext(),"beğendigin anonslar",Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_item_engellenenler:
                Toast.makeText(getContext(),"Engellenenler",Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_item_uygulamayi_paylas:
                Intent shareIntent =new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String sharedBody="body";
                String shareSub="sub";
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,sharedBody);
                context.startActivity(Intent.createChooser(shareIntent,"Paylaş"));
                Toast.makeText(getContext(), "3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item_lisanslar:
                Toast.makeText(getContext(),"Lisanslar",Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_item_gizlilik_kosullari:
                Toast.makeText(getContext(),"Gizlilik Koşulları",Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_item_gorus_oneri:
                Intent intentGorusOneri = new Intent(getContext(), MenuGorusOneriEkran.class);
                startActivity(intentGorusOneri);
                Toast.makeText(getContext(),"Görüş Ve öneri",Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_item_yardim:
                Toast.makeText(getContext(),"yardım",Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_item_ayarlar:
                Intent intentDrawableAc = new Intent(getContext(), MenuAyarlarEkran.class);
                startActivity(intentDrawableAc);
                break;
            case R.id.menu_item_cikis_yap:
                FirebaseAuth.getInstance().signOut();
                Intent intentCikisyap = new Intent(getContext(), LoginEkran.class);
                startActivity(intentCikisyap);
                Objects.requireNonNull(getActivity()).finish();
                break;
        }


        return true;
    }

    private void anonsSayacYazdir() {
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("countOfAllAnons")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String sayac = dataSnapshot.getValue().toString();
                        bildirimSayiTextView.setText(sayac);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


   static public void navigationViewKapat() {
            mdrawerLayout.closeDrawer(GravityCompat.START);
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
    @Override
    public void onStart() {
        super.onStart();
        if(fAdapter!=null)
            fAdapter.startListening();
    }



    @Override
    public void onStop() {
        super.onStop();
        if(fAdapter!=null)
            fAdapter.stopListening();
    }

}
