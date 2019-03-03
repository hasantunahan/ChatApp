package com.anonsgoup.anons.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class VeriTabaniBaglanti extends SQLiteAssetHelper {
    private static final String DB_ADI = "anons.db";
    private static final int DB_VERSION = 1;

    public VeriTabaniBaglanti(Context context) {
        super(context, DB_ADI, null, DB_VERSION);
    }
}
