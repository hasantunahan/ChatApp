package com.anonsgroup.anons.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.anonsgroup.anons.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class KullaniciIslemler {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase db;
    private static KullaniciIslemler kullaniciIslemleri;

    private final String kullaniciTablo="user";
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
                c=db.rawQuery("select username from user where username="+user.getUsername(),null);
                if(c.moveToFirst()){
                    db.execSQL("delete from user where username="+user.getUsername());
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
        db.update("user",values,"username",new String[]{user.getUsername()});
    }
    public User kullaniciAl (String kullaniciAdi){
        User user = new User();
        String sql = "SELECT * FROM user where username="+kullaniciAdi;
        Cursor cursor;
        cursor = db.rawQuery(sql, null);

        if (cursor.moveToNext()){
           user.setEmail(cursor.getString(cursor.getColumnIndex("email" )));
           user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
           user.setName(cursor.getString(cursor.getColumnIndex("name")));
           user.setSurname(cursor.getString(cursor.getColumnIndex("surname")));
           user.setDob(cursor.getLong(cursor.getColumnIndex("dob")));
           user.setGender(cursor.getString(cursor.getColumnIndex("gender")));
           user.setSummInfo(cursor.getString(cursor.getColumnIndex("summInfo")));
           user.setLastDateOfLogIn(cursor.getLong(cursor.getColumnIndex("lastDateOfLogIn")));
           user.setCountOfAnonsDaily(cursor.getInt(cursor.getColumnIndex("countOfAnonsDaily")));
           user.setProfilPhoto(cursor.getBlob(cursor.getColumnIndex("profilPhoto")));
           user.setProfilBackground(cursor.getBlob(cursor.getColumnIndex("profilBackground")));
        }
        return user;
    }

}
