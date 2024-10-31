package com.example.oldersafe.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oldersafe.R;
import com.example.oldersafe.bean.UserBaseInfo;
import com.example.oldersafe.config.Constants;
import com.example.oldersafe.config.SharedPreferencesUtils;
import com.example.oldersafe.database.DBDao;

import java.util.UUID;

public class AddContactActivity extends AppCompatActivity {

    EditText name;
    EditText phone;
    Button add;
    String method;
    TextView title;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        add = findViewById(R.id.add);
        title = findViewById(R.id.title);
        method = getIntent().getStringExtra("type");
        if(method.equals("add")){
            title.setText("Add Rescue Worker");
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    save();
                }
            });
        }else{
            title.setText("Edit Rescue Worker");
            id = getIntent().getStringExtra("info_id");
            String contact = getIntent().getStringExtra("contact");
            String contact_phone = getIntent().getStringExtra("contact_phone");
            name.setText(contact);
            phone.setText(contact_phone);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edit();
                }
            });
        }
    }

    private void save() {
        DBDao dao=new DBDao(getApplicationContext());
        dao.open();
        String name1 = (String) SharedPreferencesUtils.getParam(AddContactActivity.this, Constants.User_Name, "00");
        String User_Type = (String) SharedPreferencesUtils.getParam(AddContactActivity.this, Constants.User_Type, "00");
        String Phone = (String) SharedPreferencesUtils.getParam(AddContactActivity.this, Constants.Phone, "00");

        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setPhone(Phone);
        userBaseInfo.setFlag("2");
        userBaseInfo.setUserType(User_Type);
        userBaseInfo.setInfoId(String.valueOf(UUID.randomUUID()));
        userBaseInfo.setUserName(name1);
        userBaseInfo.setContact(name.getText().toString());
        userBaseInfo.setContactPhone(phone.getText().toString());
        long result = dao.addContact(userBaseInfo);
        dao.close();
        if (result > 0) {
            Toast.makeText(this, "add success", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "add failure", Toast.LENGTH_SHORT).show();
        }
    }

    private void edit() {
        DBDao dao=new DBDao(getApplicationContext());
        dao.open();

        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setInfoId(id);
        userBaseInfo.setContact(name.getText().toString());
        userBaseInfo.setContactPhone(phone.getText().toString());
        long result = dao.editContact(userBaseInfo);
        dao.close();
        if (result > 0) {
            Toast.makeText(this, "edit success", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "edit failure", Toast.LENGTH_SHORT).show();
        }
    }

}