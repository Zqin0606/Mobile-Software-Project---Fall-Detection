package com.example.oldersafe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.oldersafe.bean.UserBaseInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBDao {

    private Context context;
    private MyDataBaseHelper dbHelper;
    private SQLiteDatabase db;

    // Constructor
    public DBDao(Context context) {
        this.context = context;
    }

    // Open the database
    public void open() throws SQLiteException {
        dbHelper = new MyDataBaseHelper(context);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }

    // Close the database
    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }
}