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
import com.example.oldersafe.database.DBDao;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    EditText userName;
    EditText psw;
    EditText account;
    Button register;
    TextView text;
    String type;
    EditText phone;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        account = findViewById(R.id.account);
        psw = findViewById(R.id.psw);
        userName = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        text = findViewById(R.id.text);
        register = findViewById(R.id.register);
        type = getIntent().getStringExtra("type");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(account.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"account is empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if("".equals(psw.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"password is empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if("".equals(userName.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"name is empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if("".equals(phone.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"phone is empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                save();
            }
        });
    }

    private void save() {
        DBDao dao=new DBDao(getApplicationContext());
        dao.open();
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setInfoId(String.valueOf(UUID.randomUUID()));
        userBaseInfo.setAccount(account.getText().toString());
        userBaseInfo.setPhone(phone.getText().toString());
        userBaseInfo.setPsw(psw.getText().toString());
        userBaseInfo.setFlag("1");
        userBaseInfo.setUserType(type);
        userBaseInfo.setUserName(userName.getText().toString());
        userBaseInfo.setState("1");
        long result = dao.register(userBaseInfo);
        dao.close();
        if (result > 0) {
            Toast.makeText(this, "register success", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "register failure", Toast.LENGTH_SHORT).show();
        }
    }
}
