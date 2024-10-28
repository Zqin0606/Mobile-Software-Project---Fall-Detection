package com.example.oldersafe.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.oldersafe.R;

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

        // Initialize views
        name = findViewById(R.id.account);
        psw = findViewById(R.id.psw);
        tvData = findViewById(R.id.data);
        rbOld = findViewById(R.id.type_old);
        rbCon = findViewById(R.id.type_con);
    }
}