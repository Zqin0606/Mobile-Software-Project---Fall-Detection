package com.example.oldersafe.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oldersafe.R;
import com.example.oldersafe.bean.UserBaseInfo;
import com.example.oldersafe.config.Constants;
import com.example.oldersafe.config.SharedPreferencesUtils;
import com.example.oldersafe.database.DBDao;

public class LoginActivity extends AppCompatActivity {

    EditText name;
    EditText psw;
    TextView tvData;
    RadioButton rbOld;
    RadioButton rbCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkPermission_();

        // Initialize views
        name = findViewById(R.id.account);
        psw = findViewById(R.id.psw);
        tvData = findViewById(R.id.data);
        rbOld = findViewById(R.id.type_old);
        rbCon = findViewById(R.id.type_con);
    }


    public void judgeLogin(){
        DBDao dao=new DBDao(getApplicationContext());
        dao.open();
        UserBaseInfo userBaseInfo=dao.Login(name.getText().toString(),psw.getText().toString(),"1");
        dao.close();
        if(userBaseInfo == null ||userBaseInfo.getInfoId()==null || userBaseInfo.getInfoId().equals("")){
            Toast.makeText(LoginActivity.this,"login fail",Toast.LENGTH_SHORT).show();
            return;
        }else{
            SharedPreferencesUtils.setParam(LoginActivity.this, Constants.User_ID,userBaseInfo.getInfoId());
            SharedPreferencesUtils.setParam(LoginActivity.this, Constants.User_Name,userBaseInfo.getUserName());
            SharedPreferencesUtils.setParam(LoginActivity.this, Constants.User_Type,userBaseInfo.getUserType());
            SharedPreferencesUtils.setParam(LoginActivity.this, Constants.Phone,userBaseInfo.getPhone());
            SharedPreferencesUtils.setParam(LoginActivity.this, Constants.State,userBaseInfo.getState());

            finish();
        }
    }



    public void checkPermission_(){
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.SEND_SMS,
                Manifest.permission.CALL_PHONE
        };
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions,1);
            return;
        }
    }


}