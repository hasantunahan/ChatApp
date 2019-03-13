package com.anonsgroup.anons.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.anonsgroup.anons.models.User;

public class KullaniciIslemler {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase db;
    private static KullaniciIslemler kullaniciIslemleri;

    private final String kullaniciTablo="kullanici";
    private KullaniciIslemler(Context context){
        this.sqLiteOpenHelper = new VeriTabaniBaglanti(context);
    }

    public static KullaniciIslemler getInstance(Context context){
        if(kullaniciIslemleri == null){
            kullaniciIslemleri = new KullaniciIslemler(context);
        }
        return kullaniciIslemleri;
    }
    public void open() {
        this.db = sqLiteOpenHelper.getWritableDatabase();
    }
    public void close(){
        this.db.close();
    }

    public void yeniKullaniciKaydet(User user){
        Cursor c;
            try {
                c=db.rawQuery("select username from kullanici where username="+user.getUsername(),null);
                if(c.moveToFirst()){
                    db.execSQL("delete from kullanici where username="+user.getUsername());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        ContentValues values=new ContentValues();
        values.put("username",user.getUsername());
        values.put("email",user.getEmail());
        values.put("name",user.getName());
        values.put("surname",user.getSurname());
        values.put("dob",user.getDob());
        values.put("gender",user.getGender());
        values.put("createdDate",user.getCreatedDate());
        values.put("summInfo",user.getSummInfo());
        values.put("lastDateOfLogIn",user.getLastDateOfLogIn());
        values.put("countOfAnonsDaily",user.getCountOfAnonsDaily());
        values.put("profilPhoto",user.getProfilPhoto());
        values.put("profilBackground",user.getProfilBackground());
        db.insert("kullanici",null,values);
    }

    public void kullaniciGuncelle(User user){
        ContentValues values=new ContentValues();
        values.put("name",user.getName());
        values.put("surname",user.getSurname());
        values.put("summInfo",user.getSummInfo());
        values.put("email",user.getEmail());
        values.put("profilPhoto",user.getProfilPhoto());
        values.put("profilBackground",user.getProfilBackground());
        db.update("kullanici",values,"username",new String[]{user.getUsername()});
    }


}
