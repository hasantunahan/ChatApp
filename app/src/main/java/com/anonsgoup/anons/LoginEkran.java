package com.anonsgoup.anons;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginEkran extends Activity {
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
        if(fUser!= null)
            fUser.reload();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //GİRİŞ YAPMA OLAYI KONTROLÜ
                //şimdilik email ile giriyoruz.
                kullaniciAdi = usernameEditText.getText().toString().trim();
                sifre = passwordEditText.getText().toString().trim();
                if(!alanlarKontrol(kullaniciAdi,sifre)){
                    return;
                }
                progressDialog = new ProgressDialog(LoginEkran.this);
                progressDialog.setTitle(getResources().getString(R.string.processing));
                progressDialog.setMessage(getResources().getString(R.string.please_wait));
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(kullaniciAdi,sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            fUser = mAuth.getCurrentUser();
                            if(fUser != null){
                                fUser.reload();
                                if(!fUser.isEmailVerified()) {
                                    Log.d("doğrulama",fUser.isEmailVerified()+"");
                                    Intent intent = new Intent(getApplicationContext(), EmailDogrulamaEkran.class);
                                    startActivity(intent);
                                    return;
                                }
                            }
                            Intent intent = new Intent(getApplicationContext(), AnaEkran.class);
                            startActivity(intent);
                            finish();
                        }
                        else {

                            progressDialog.hide();
                            if(!internetBaglantiKontrol()) {
                                Toast.makeText(LoginEkran.this, getResources().getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            uyariTextView.setText(getResources().getString(R.string.invalid_login));
                            uyariTextView.setVisibility(View.VISIBLE);
                            Log.d("girisHata",task.getException().getMessage());
                        }
                    }
                });

            }
        });


      signUp.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                if(!internetBaglantiKontrol()) {
                    Toast.makeText(LoginEkran.this, getResources().getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(getApplicationContext(), KayitOlEkran.class));
            }
        });

    }


    private boolean alanlarKontrol(String kullaniciAdi, String sifre) {
        if(kullaniciAdi.equals("") && sifre.equals("")){
            userNameWrapper.setError(getResources().getString(R.string.username_empty));
            passwordWrapper.setError(getResources().getString(R.string.password_empty));
            return false;
        }
        else if(kullaniciAdi.equals("")){
            userNameWrapper.setError(getResources().getString(R.string.username_empty));
            return false;
        }
        else if(sifre.equals("")){
            passwordWrapper.setError(getResources().getString(R.string.password_empty));
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        fUser = mAuth.getCurrentUser();
        if(fUser != null){
            fUser.reload();
            if(fUser.isEmailVerified()) {
                Log.d("doğrulama",fUser.isEmailVerified()+"");
                Intent intent = new Intent(getApplicationContext(), AnaEkran.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private boolean internetBaglantiKontrol(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are checking whether connect to a network
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }
    
}
