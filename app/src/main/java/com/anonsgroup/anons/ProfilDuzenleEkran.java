package com.anonsgroup.anons;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.anonsgroup.anons.database.KullaniciIslemler;
import com.anonsgroup.anons.database.VeriTabaniDb;
import com.anonsgroup.anons.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;


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
    private User user;
    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
    private boolean profilKontrol, backgroundKontrol;


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

        VeriTabaniDb db = VeriTabaniDb.getInstance(getApplicationContext());
        db.open();
        KullaniciIslemler kIslemler = new KullaniciIslemler(db.dbAl());
        user =kIslemler.kullaniciAl(fUser.getDisplayName());
        db.close();

        adiEditText.setText(user.getName());
        soyadiEditText.setText(user.getSurname());
        dogumTarihiEditText.setText(new Date(user.getDob()).toString());
        durumEditText.setText(user.getSummInfo());
        emailEditText.setText(user.getEmail());
        if(user.getProfilPhoto() != null)
            profilImage.setImageBitmap(BitmapFactory.decodeByteArray(user.getProfilPhoto(),0,user.getProfilPhoto().length));
        if(user.getProfilBackground() != null)
            backgroundImage.setImageBitmap(BitmapFactory.decodeByteArray(user.getProfilBackground(),0,user.getProfilBackground().length));


        //TODO: BU IMAGEVIEWLER BAŞLANGIÇ OLARAK DATABASEDEN ÇEKİLMELİ
        //TODO: Locale Kayıt yapılacak
        //TODO: İLK bu ekran açıldığında localden resimler çekilecek
        //TODO: Alttakilerin onsucces vs. doldurulacak.

    }


    public void profilDuzenleClicks(View view) {
        switch (view.getId()){
            case R.id.profiliDuzenleKaydetButton:
                storageReference = FirebaseStorage.getInstance().getReference("UsersPhotos").child(fUser.getUid());
                if(backgroundBitmap != null){
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    backgroundBitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                    backgroundByte = baos.toByteArray();
                    UploadTask uploadTask = storageReference.child("backGround.jpeg").putBytes(backgroundByte);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            backgroundKontrol = true;
                            System.out.println("deneememememee");

                        }
                    });

                }
                if(avatarBitmap != null){
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    avatarBitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                    avatarByte = baos.toByteArray();
                    UploadTask uploadTask = storageReference.child("profil.jpeg").putBytes(avatarByte);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            profilKontrol = true;
                            System.out.println("deneememememee");
                        }
                    });

                }
                //TODO: Burası Düzenlenecek tekrar
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        //Thread.sleep(10000);
                        while(!profilKontrol && !backgroundKontrol);
                        user.setUsername(fUser.getDisplayName());
                        if(backgroundKontrol)
                            user.setProfilBackground(backgroundByte);
                        if(profilKontrol)
                            user.setProfilPhoto(avatarByte);
                        user.setName(adiEditText.getText().toString());
                        user.setSummInfo(durumEditText.getText().toString());
                        VeriTabaniDb tabaniDb = VeriTabaniDb.getInstance(getApplicationContext());
                        tabaniDb.open();
                        new KullaniciIslemler(tabaniDb.dbAl()).kullaniciGuncelle(user);
                        tabaniDb.close();
                        finish();
                        return;

                    }
                };
                new Thread(runnable).start();

                break;
            case R.id.profiliDuzenleiptalButton:
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
                        backgroundDegis();
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
        }
    }

    private void backgroundDegis() {
        hangiResimDegisti = BACKGROUND_RESMI;
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(ProfilDuzenleEkran.this);
    }

    private void resimDegistir() {
        hangiResimDegisti = AVATAR_RESMI;
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
            }
        }
    }

}

