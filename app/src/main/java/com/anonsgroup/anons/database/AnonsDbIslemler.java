package com.anonsgroup.anons.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.anonsgroup.anons.models.Anons;
import com.anonsgroup.anons.models.Anonss;
import com.anonsgroup.anons.models.User;

public class AnonsDbIslemler {
    private SQLiteDatabase db;
    public AnonsDbIslemler(SQLiteDatabase db){
        this.db=db;
    }

    public void anonsKaydet(Anonss anonss){

        
    }
}
