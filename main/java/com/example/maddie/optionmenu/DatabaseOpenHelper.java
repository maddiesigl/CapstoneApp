package com.example.maddie.optionmenu;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Maddie on 8/8/2018.
 */

public class DatabaseOpenHelper extends SQLiteAssetHelper{

    private static final String DATABASE_NAME = "dump.sqlite";
    private static final int DATABASE_VERSION = 1;

    //constructor

    public DatabaseOpenHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
