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
    //login functionality
    public UserBaseInfo Login(String account, String psw, String flag) {
        Cursor cursor = db.query("userinfo", null, "account=? and psw=? and flag=?", new String[]{account, psw, flag}, null, null, null);
        UserBaseInfo o = new UserBaseInfo();
        while (cursor.moveToNext()) {
            o.infoId = cursor.getString(cursor.getColumnIndex("info_id"));
            o.userName = cursor.getString(cursor.getColumnIndex("name"));
            o.phone = cursor.getString(cursor.getColumnIndex("phone"));
            o.flag = cursor.getString(cursor.getColumnIndex("flag"));
            o.state = cursor.getString(cursor.getColumnIndex("state"));
            o.userType = cursor.getString(cursor.getColumnIndex("usertype"));
        }
        return o;
    }

    //Register Functionality
    public long register(UserBaseInfo o) {
        ContentValues values = new ContentValues();
        //Register Contacts
        values.put("info_id", o.infoId);
        values.put("name", o.userName);
        values.put("account", o.account);
        values.put("psw", o.psw);
        values.put("phone", o.phone);
        values.put("usertype", o.userType);
        values.put("flag", o.flag);
        values.put("state", o.state);
        //insert into the data base
        return db.insert("userinfo", null, values);
    }








}