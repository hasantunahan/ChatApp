package com.anonsgroup.anons.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VeriTabaniDb {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase db;
    private static VeriTabaniDb veriTabaniDb;

    private final String kullaniciTablo="user";
    private VeriTabaniDb(Context context){
        this.sqLiteOpenHelper = new VeriTabaniBaglanti(context);
    }

    public static VeriTabaniDb getInstance(Context context){
        if(veriTabaniDb == null){
            veriTabaniDb = new VeriTabaniDb(context);
        }
        return veriTabaniDb;
    }
    public SQLiteDatabase dbAl(){
        return db;
    }
    public void open() {
        this.db = sqLiteOpenHelper.getWritableDatabase();
    }
    public void close(){
        this.db.close();
    }
}
