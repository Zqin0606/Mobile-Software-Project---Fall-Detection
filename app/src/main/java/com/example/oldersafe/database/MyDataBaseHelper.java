package com.example.oldersafe.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @ClassName MyDataBaseHelper
 * @Description TODO
 * @Author lgt
 * @Date 2024/10/15 14:49
 */
public class MyDataBaseHelper extends SQLiteOpenHelper {

    public static final String name = "older.db";
    public static final int DB_VERSION = 1;

    public static final String CREATE_USERDATA =
            "create table userinfo(info_id varchar(64)primary key,name varchar(20)," +
                    "phone varchar(20),account varchar(20),psw varchar(20),usertype varchar(20),state varchar(20)," +
                    "contact varchar(20),contact_phone varchar(20),flag varchar(20),address varchar(500)," +
                    "latitude varchar(20),longitude varchar(20))";
    public MyDataBaseHelper(Context context) {
        super(context, name, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERDATA);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
