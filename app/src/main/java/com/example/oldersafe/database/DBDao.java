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

    // Login functionality
    public UserBaseInfo login(String account, String psw, String flag) {
        Cursor cursor = null;
        UserBaseInfo userInfo = new UserBaseInfo();
        try {
            cursor = db.query("userinfo", null, "account=? and psw=? and flag=?", new String[]{account, psw, flag}, null, null, null);
            if (cursor.moveToFirst()) {
                userInfo.infoId = cursor.getString(cursor.getColumnIndex("info_id"));
                userInfo.userName = cursor.getString(cursor.getColumnIndex("name"));
                userInfo.phone = cursor.getString(cursor.getColumnIndex("phone"));
                userInfo.flag = cursor.getString(cursor.getColumnIndex("flag"));
                userInfo.state = cursor.getString(cursor.getColumnIndex("state"));
                userInfo.userType = cursor.getString(cursor.getColumnIndex("usertype"));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return userInfo;
    }

    // Register functionality
    public long register(UserBaseInfo o) {
        ContentValues values = new ContentValues();
        values.put("info_id", o.infoId);
        values.put("name", o.userName);
        values.put("account", o.account);
        values.put("psw", o.psw);
        values.put("phone", o.phone);
        values.put("usertype", o.userType);
        values.put("flag", o.flag);
        values.put("state", o.state);
        return db.insert("userinfo", null, values);
    }

    // Retrieve contact list based on criteria
    public ArrayList<Map<String, Object>> getContactData(String name, String type, String flag) {
        ArrayList<Map<String, Object>> contactList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query("userinfo", null, "name=? and usertype=? and flag=?", new String[]{name, type, flag}, null, null, null);
            while (cursor != null && cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<>();
                map.put("info_id", cursor.getString(cursor.getColumnIndex("info_id")));
                map.put("contact", cursor.getString(cursor.getColumnIndex("contact")));
                map.put("contact_phone", cursor.getString(cursor.getColumnIndex("contact_phone")));
                contactList.add(map);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contactList.isEmpty() ? null : contactList;
    }

    // Retrieve help data based on criteria
    public ArrayList<Map<String, Object>> getHelpData(String contact, String state, String flag) {
        ArrayList<Map<String, Object>> contactList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query("userinfo", null, "contact=? and state=? and flag=?", new String[]{contact, state, flag}, null, null, null);
            while (cursor != null && cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", cursor.getString(cursor.getColumnIndex("name")));
                map.put("phone", cursor.getString(cursor.getColumnIndex("phone")));
                map.put("address", cursor.getString(cursor.getColumnIndex("address")));
                map.put("state", cursor.getString(cursor.getColumnIndex("state")));
                map.put("flag", cursor.getString(cursor.getColumnIndex("flag")));
                map.put("contact", cursor.getString(cursor.getColumnIndex("contact")));
                map.put("latitude", cursor.getString(cursor.getColumnIndex("latitude")));
                map.put("longitude", cursor.getString(cursor.getColumnIndex("longitude")));
                contactList.add(map);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contactList.isEmpty() ? null : contactList;
    }

    // Add contact person data
    public long addContact(UserBaseInfo o) {
        ContentValues values = new ContentValues();
        values.put("info_id", o.infoId);
        values.put("name", o.userName);
        values.put("account", o.account);
        values.put("phone", o.phone);
        values.put("usertype", o.userType);
        values.put("flag", o.flag);
        values.put("contact", o.contact);
        values.put("contact_phone", o.contactPhone);
        return db.insert("userinfo", null, values);
    }

    // Edit contact information
    public long editContact(UserBaseInfo o) {
        ContentValues values = new ContentValues();
        values.put("contact", o.contact);
        values.put("contact_phone", o.contactPhone);
        return db.update("userinfo", values, "info_id=?", new String[]{o.infoId});
    }

    // Update address information
    public int updateAddress(String name, String address, String state, double latitude, double longitude) {
        ContentValues value = new ContentValues();
        value.put("address", address);
        value.put("state", state);
        value.put("latitude", latitude);
        value.put("longitude", longitude);
        return db.update("userinfo", value, "name=?", new String[]{name});
    }

    // Get all user data
    public ArrayList<Map<String, Object>> getAllData() {
        ArrayList<Map<String, Object>> contactList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query("userinfo", null, null, null, null, null, null);
            while (cursor != null && cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", cursor.getString(cursor.getColumnIndex("name")));
                map.put("phone", cursor.getString(cursor.getColumnIndex("phone")));
                map.put("account", cursor.getString(cursor.getColumnIndex("account")));
                map.put("psw", cursor.getString(cursor.getColumnIndex("psw")));
                map.put("address", cursor.getString(cursor.getColumnIndex("address")));
                map.put("state", cursor.getString(cursor.getColumnIndex("state")));
                map.put("flag", cursor.getString(cursor.getColumnIndex("flag")));
                map.put("contact", cursor.getString(cursor.getColumnIndex("contact")));
                map.put("contact_phone", cursor.getString(cursor.getColumnIndex("contact_phone")));
                map.put("latitude", cursor.getString(cursor.getColumnIndex("latitude")));
                map.put("longitude", cursor.getString(cursor.getColumnIndex("longitude")));
                contactList.add(map);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contactList.isEmpty() ? null : contactList;
    }
}
