package com.anonsgroup.anons;


import android.annotation.SuppressLint;
import com.anonsgroup.anons.customViews.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anonsgroup.anons.database.KullaniciIslemler;
import com.anonsgroup.anons.database.VeriTabaniDb;
import com.anonsgroup.anons.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class LoginEkran extends AppCompatActivity {
    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    TextView uyariTextView;
    Button signUp;
    Button login;
    String kullaniciAdi;
    String sifre;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private FirebaseUser fUser;
    private TextInputLayout userNameWrapper;
    private TextInputLayout passwordWrapper;
    private VeriTabaniDb tabaniDb;
    StorageReference storageRef;
    private AtomicBoolean verilerCekildi;
    private AtomicBoolean profilPhotoCekildi;
    private AtomicBoolean backgroundPhotoCekildi;
    private User user;

    //TODO: kullanıcı çıkış yaptığında local veritabanı tamamen temizlenecek.
    //TODO: Tekrar giriş yapan kullanıcı için local veritabanına bilgiler firebaseden çekilecek. Çekilen anons sayısı kontrollü olacak.

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ekran);
        login = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUpButton);
        userNameWrapper = findViewById(R.id.text_input_layout_username);
        passwordWrapper = findViewById(R.id.text_input_layout_password);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        uyariTextView = findViewById(R.id.uyariTextView);
        verilerCekildi = new AtomicBoolean();
        profilPhotoCekildi = new AtomicBoolean();
        backgroundPhotoCekildi = new AtomicBoolean();
        verilerCekildi.set(false);
        profilPhotoCekildi.set(false);
        backgroundPhotoCekildi.set(false);

        usernameEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                userNameWrapper.setError(null);
                return false;
            }
        });
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                passwordWrapper.setError(null);
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        fUser = mAuth.getCurrentUser();
        if (fUser != null)
            fUser.reload();


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!internetBaglantiKontrol()) {
                    Toast.makeText(LoginEkran.this, getResources().getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(getApplicationContext(), KayitOlEkran.class));
            }
        });

    }


    private boolean alanlarKontrol(String kullaniciAdi, String sifre) {
        if (kullaniciAdi.equals("") && sifre.equals("")) {
            userNameWrapper.setError(getResources().getString(R.string.username_empty));
            passwordWrapper.setError(getResources().getString(R.string.password_empty));
            return false;
        } else if (kullaniciAdi.equals("")) {
            userNameWrapper.setError(getResources().getString(R.string.username_empty));
            return false;
        } else if (sifre.equals("")) {
            passwordWrapper.setError(getResources().getString(R.string.password_empty));
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        fUser = mAuth.getCurrentUser();
        if (fUser != null) {
            fUser.reload();
            if (fUser.isEmailVerified()) {
                Log.d("doğrulama", fUser.isEmailVerified() + "");
                Intent intent = new Intent(getApplicationContext(), AnaEkran.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onStop() {
        if (progressDialog != null) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
        super.onStop();

    }

    private boolean internetBaglantiKontrol() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are checking whether connect to a network
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }

    public void loginEkranClicks(View view) {

        switch (view.getId()) {
            case R.id.loginButton:
                //şimdilik email ile giriyoruz.
                kullaniciAdi = usernameEditText.getText().toString().trim();
                sifre = passwordEditText.getText().toString().trim();
                if (!alanlarKontrol(kullaniciAdi, sifre)) {
                    return;
                }
                progressDialog = new ProgressDialog();
                progressDialog.setStyle(R.style.CustomAlertDialogStyle,R.style.CustomDialogTheme);
                progressDialog.setMessage(getResources().getString(R.string.please_wait));
                progressDialog.setCancelable(false);
                progressDialog.show(getSupportFragmentManager(),"LoginEkran");

                if (!kullaniciAdi.contains("@")) {
                    if (!internetBaglantiKontrol()) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginEkran.this, getResources().getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Query query = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("username").equalTo(kullaniciAdi);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d("girdi ", "asdasdasdasdasdasdasdasd");
                            if (dataSnapshot.exists()) {
                                Log.d("email: ", "childerns::::" + " - " + dataSnapshot.getChildren().toString());
                                // dataSnapshot is the "issue" node with all children with id 0
                                for(DataSnapshot datas : dataSnapshot.getChildren()) {
                                    Log.d("email: ", kullaniciAdi + " - " + dataSnapshot.child("email"));
                                    kullaniciAdi = datas.child("email").getValue().toString();
                                    Log.d("email: ", datas.getValue().toString());
                                }
                            }
                            girisYap();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else
                    girisYap();


                break;

            case R.id.loginEkranLayout:
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
                break;
        }

    }

    private void girisYap() {

        if (!internetBaglantiKontrol()) {
            progressDialog.dismiss();
            Toast.makeText(LoginEkran.this, getResources().getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(kullaniciAdi, sifre).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                fUser = mAuth.getCurrentUser();
                if (fUser != null) {
                    fUser.reload();
                    if (!fUser.isEmailVerified()) {
                        Log.d("doğrulama", fUser.isEmailVerified() + "");
                        Intent intent = new Intent(getApplicationContext(), EmailDogrulamaEkran.class);
                        startActivity(intent);
                        return;
                    }
                }
                tabaniDb = VeriTabaniDb.getInstance(getApplicationContext());
                tabaniDb.open();
                user = new KullaniciIslemler(tabaniDb.dbAl()).kullaniciAlMail(kullaniciAdi);
                if(user == null){
                    user = new User();
                    Query query = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("email").equalTo(kullaniciAdi);
                    query.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Log.d("gelen:" , postSnapshot.getValue().toString());
                                user = postSnapshot.getValue(User.class);
                                Log.d("gelenler:",user.getEmail() + " " + user.getName());
                                verilerCekildi.set(true);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    } );
                    fUser.reload();
                    storageRef = FirebaseStorage.getInstance().getReference();
                    // Create a storage reference from our app
                    StorageReference profilRef = storageRef.child("UsersPhotos/"+fUser.getUid()+"/profil.jpeg");
                    System.out.println(fUser.getUid());
                    final long maxBytes= 1024*1024*5;
                    profilRef.getBytes(maxBytes).addOnCompleteListener(gorev ->
                    {user.setProfilPhoto(gorev.getResult()); profilPhotoCekildi.set(true);});
                    StorageReference backgRef = storageRef.child("UsersPhotos/"+fUser.getUid()+"/backGround.jpeg");
                    backgRef.getBytes(maxBytes).addOnCompleteListener(gorev1 -> {user.setProfilBackground(gorev1.getResult()); backgroundPhotoCekildi.set(true);});
                    islemleriBitir();

                }
                else {
                    tabaniDb.close();
                    Log.d("displayname:", fUser.getDisplayName());
                    Intent intent = new Intent(getApplicationContext(), AnaEkran.class);
                    finish();
                    startActivity(intent);
                }

            } else {
                progressDialog.dismiss();
                uyariTextView.setText(getResources().getString(R.string.invalid_login));
                uyariTextView.setVisibility(View.VISIBLE);
                Log.d("girisHata", task.getException().getMessage());
            }
        });


    }

    private void islemleriBitir() {

        Thread anaGorev = new Thread(() -> {

            while (!profilPhotoCekildi.get()|| !backgroundPhotoCekildi.get() || !verilerCekildi.get()) {
                System.out.println("deneme");
                if(Thread.interrupted())
                    return;
            }
            Log.d("en sonki user: ",user.getEmail() + " " + user.getSurname());
            Log.d("Buraya bakkkk", profilPhotoCekildi.get() + "" + backgroundPhotoCekildi.get() + " " + verilerCekildi.get());
            new KullaniciIslemler(tabaniDb.dbAl()).yeniKullaniciKaydet(user);
            tabaniDb.close();
            Intent intent = new Intent(getApplicationContext(), AnaEkran.class);
            finish();
            startActivity(intent);
        });
        anaGorev.start();
        final AtomicReference<String> hataMesaji = new AtomicReference<>();
        hataMesaji.set("");
        new Thread(() -> {

            try {
                Thread.sleep(10000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!profilPhotoCekildi.get()) {
                hataMesaji.set(hataMesaji.get()+getResources().getString(R.string.profilPhoto_download_error) + "\n");
            }
            if (!backgroundPhotoCekildi.get()) {
                hataMesaji.set(hataMesaji.get() + getResources().getString(R.string.backgroundPhoto_download_error) + "\n");
            }
            if (!verilerCekildi.get()) {
                hataMesaji.set(hataMesaji.get() + getResources().getString(R.string.user_info_error) + "\n");
            }
            Log.d("Bakalimmmmmmmmm:","eeee Gelmiş");
            anaGorev.interrupt();
            hata(hataMesaji.get());
        }).start();

    }
    private void hata(String mesaj){
        this.runOnUiThread(() -> {
            Toast.makeText(getApplicationContext(), mesaj+getResources().getString(R.string.connection_timeout), Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        });
    }
}
