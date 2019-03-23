package com.anonsgroup.anons;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.anonsgroup.anons.customViews.ProgressDialog;
import com.anonsgroup.anons.database.KullaniciIslemler;
import com.anonsgroup.anons.database.VeriTabaniDb;
import com.anonsgroup.anons.models.FirebaseUserModel;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;


public class ProfilDuzenleEkran extends AppCompatActivity {
    private StorageReference storageReference;
    private static final int REQUEST_STORAGE_PERMISSION = 100;
    private ImageView profilImage, backgroundImage;
    private final int BACKGROUND_RESMI=2;
    private final int AVATAR_RESMI=1;
    private int hangiResimDegisti;
    private Bitmap avatarBitmap;
    private byte[] avatarByte;
    private byte[] backgroundByte;
    private Bitmap backgroundBitmap;
    private EditText adiEditText;
    private EditText dogumTarihiEditText;
    private EditText durumEditText;
    private EditText emailEditText;
    private EditText soyadiEditText;
    private long longDOB;
    private FirebaseUserModel user;
    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
    private boolean profilKontrol, backgroundKontrol;
    private boolean profilDegisti = false, backgroundDegisti = false;
    private ProgressDialog progressDialog;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiliduzenle_ekran);
        profilImage = findViewById(R.id.profilDuzenleAvatarCircleImage);
        backgroundImage = findViewById(R.id.profilDuzenleBackgroundImageView);
        adiEditText = findViewById(R.id.profilDuzenleAdiEditText);
        dogumTarihiEditText = findViewById(R.id.profilDuzenleDogumTarihiEditText);
        durumEditText  = findViewById(R.id.profilDuzenleDurumEditText);
        emailEditText = findViewById(R.id.profilDuzenleEmailEditText);
        soyadiEditText = findViewById(R.id.profilDuzenleSoyadEditText);
        dogumTarihiEditText.setFocusable(false);

        //Firebaseden Verilerin Çekildiği Kod::
        ref= FirebaseDatabase.getInstance().getReference("users").child(fUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(FirebaseUserModel.class);
                adiEditText.setText(user.getName());
                soyadiEditText.setText(user.getSurname());
                longDOB = user.getDob();
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date(longDOB));
                String a = cal.get(Calendar.DAY_OF_MONTH)+"-"+(cal.get(Calendar.MONTH)+1) +"-" +cal.get(Calendar.YEAR);
                dogumTarihiEditText.setText(a);
                durumEditText.setText(user.getSummInfo());
                emailEditText.setText(user.getEmail());
                if(user.getProfilUrl().equals("default"))
                    profilImage.setImageResource(R.drawable.kullaniciprofildefault);
                else
                    Glide.with(getApplicationContext()).load(user.getProfilUrl()).into(profilImage);
                if(user.getBackgroundUrl().equals("default"))
                    backgroundImage.setImageResource(R.drawable.defaultback);
                else
                    Glide.with(getApplicationContext().getApplicationContext()).load(user.getBackgroundUrl()).into(backgroundImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        //TODO: BU IMAGEVIEWLER BAŞLANGIÇ OLARAK DATABASEDEN ÇEKİLMELİ
        //TODO: Locale Kayıt yapılacak
        //TODO: İLK bu ekran açıldığında localden resimler çekilecek
        //TODO: Alttakilerin onsucces vs. doldurulacak.

    }


    public void profilDuzenleClicks(View view) {
        AtomicReference<String> profilUrl = new AtomicReference<>("default");
        AtomicReference<String> backgroundUrl = new AtomicReference<>("default");
        switch (view.getId()){
            case R.id.profiliDuzenleKaydetButton:
                progressDialog = new ProgressDialog();
                progressDialog.setMessage(getResources().getString(R.string.please_wait));
                progressDialog.setCancelable(false);
                progressDialog.setStyle(R.style.CustomAlertDialogStyle,R.style.CustomDialogTheme);
                progressDialog.show(getSupportFragmentManager(),"ProfilDuzenleEkran");
                storageReference = FirebaseStorage.getInstance().getReference("UsersPhotos").child(fUser.getUid());
                if(backgroundBitmap != null){
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    backgroundBitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                    backgroundByte = baos.toByteArray();
                    UploadTask uploadTask = storageReference.child("backGround.jpeg").putBytes(backgroundByte);
                    uploadTask.addOnFailureListener(exception -> {
                        // Handle unsuccessful uploads
                    }).addOnSuccessListener(taskSnapshot -> {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri ->{
                            backgroundUrl.set(uri.toString());
                            backgroundKontrol = true;
                        });
                        System.out.println("deneememememee");

                    });

                }
                if(avatarBitmap != null){
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    avatarBitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                    avatarByte = baos.toByteArray();
                    UploadTask uploadTask = storageReference.child("profil.jpeg").putBytes(avatarByte);
                    uploadTask.addOnFailureListener(exception -> {
                        // Handle unsuccessful uploads
                    }).addOnSuccessListener(taskSnapshot -> {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri ->{
                            profilUrl.set(uri.toString());
                            profilKontrol = true;
                        });
                        System.out.println("deneememememee");
                    });

                }
                //TODO: Burası Düzenlenecek tekrar
                Runnable runnable = () -> {
                    //Thread.sleep(10000);
                    if(profilDegisti && backgroundDegisti)
                        while(!profilKontrol && !backgroundKontrol);
                    else if(profilDegisti)
                        while(!profilKontrol);
                    else if (backgroundDegisti)
                        while (!backgroundKontrol);
                    user.setUsername(fUser.getDisplayName());
                    if(backgroundKontrol)
                        user.setBackgroundUrl(backgroundUrl.get());
                    if(profilKontrol)
                        user.setProfilUrl(profilUrl.get());
                    user.setName(adiEditText.getText().toString().trim());
                    user.setSummInfo(durumEditText.getText().toString().trim());
                    user.setDob(longDOB);
                    user.setSurname(soyadiEditText.getText().toString().trim());
                    FirebaseDatabase.getInstance().getReference("users")
                            .child(fUser.getUid())
                            .setValue(user).addOnCompleteListener( task -> {
                                if(task.isSuccessful()) {
                                    this.runOnUiThread(() -> progressDialog.dismiss());
                                    finish();
                                }
                                else{
                                    //TODO: buraya firebase e ekleme yapılamadıysa ne olacağı yazılacak.
                                }
                    });

                };
                new Thread(runnable).start();
                break;
            case R.id.profiliDuzenleiptalButton:
                finish();
                break;
            case R.id.profilDuzenleAvatarCircleImage:
                if(PackageManager.PERMISSION_GRANTED !=
                        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_STORAGE_PERMISSION);
                    } else {
                        //Yeah! I want both block to do the same thing, you can write your own logic, but this works for me.
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_STORAGE_PERMISSION);
                    }
                }
                else {
                    resimDegistir();

                }
                break;
            case R.id.profilDuzenleBackgroundImageView:
                if(PackageManager.PERMISSION_GRANTED !=
                        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_STORAGE_PERMISSION);
                    } else {
                        //Yeah! I want both block to do the same thing, you can write your own logic, but this works for me.
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_STORAGE_PERMISSION);
                    }
                }
                else {
                    backgroundDegis();

                }
                break;

            case R.id.profilDuzenleDogumTarihiEditText:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                onDateSetListener = (view1, year1, month1, dayOfMonth) -> {
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, year1);
                    cal.set(Calendar.MONTH, month1);
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
                    longDOB = timestamp.getTime();
                    month1 = month1 + 1;
                    dogumTarihiEditText.setText(dayOfMonth + "-" + month1 + "-" + year1);
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfilDuzenleEkran.this,
                        android.R.style.Theme_Holo_Light_Dialog, onDateSetListener, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate((new Date().getTime()) - Long.parseLong("31556926000") * 13);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                break;
        }
    }

    private void backgroundDegis() {
        hangiResimDegisti = BACKGROUND_RESMI;
        backgroundDegisti = true;
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(ProfilDuzenleEkran.this);
    }

    private void resimDegistir() {
        hangiResimDegisti = AVATAR_RESMI;
        profilDegisti = true;
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(ProfilDuzenleEkran.this);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK && data != null) {
                Uri resultUri = result.getUri();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                }    catch (IOException e) {
                    e.printStackTrace();
                }
                    if(hangiResimDegisti == AVATAR_RESMI) {
                        avatarBitmap = bitmap;
                        profilImage.setImageBitmap(bitmap);

                    }
                    else if( hangiResimDegisti == BACKGROUND_RESMI){
                        backgroundBitmap = bitmap;
                        backgroundImage.setImageBitmap(bitmap);

                    }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                error.printStackTrace();
            }
        }
    }

}

