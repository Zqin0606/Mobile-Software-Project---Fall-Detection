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

public class AddContactActivity extends AppCompatActivity {

    EditText name, phone; // Input fields for name and phone
    Button add; // Button to add or edit contact
    TextView title; // Title of the activity
    String method, id; // Mode (add/edit) and contact ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // Initialize UI components
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        add = findViewById(R.id.add);
        title = findViewById(R.id.title);

        // Get mode (add/edit) from intent
        method = getIntent().getStringExtra("type");

        if (method.equals("add")) {
            setupAddMode(); // Configure for adding a new contact
        } else {
            setupEditMode(); // Configure for editing an existing contact
        }
    }

    // Set up Add mode
    private void setupAddMode() {
        title.setText("Add Rescue Worker"); // Set title

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(); // Save contact
            }
        });
    }

    // Set up Edit mode
    private void setupEditMode() {
        title.setText("Edit Rescue Worker"); // Set title

        // Retrieve contact details from intent
        id = getIntent().getStringExtra("info_id");
        name.setText(getIntent().getStringExtra("contact"));
        phone.setText(getIntent().getStringExtra("contact_phone"));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit(); // Edit contact
            }
        });
    }

    // Save new contact to the database
    private void save() {
        DBDao dao = new DBDao(getApplicationContext());
        dao.open();

        UserBaseInfo userInfo = new UserBaseInfo();
        userInfo.setInfoId(UUID.randomUUID().toString());
        userInfo.setContact(name.getText().toString());
        userInfo.setContactPhone(phone.getText().toString());

        long result = dao.addContact(userInfo);
        dao.close();

        if (result > 0) {
            Toast.makeText(this, "Contact added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add contact", Toast.LENGTH_SHORT).show();
        }
    }

    // Edit existing contact
    private void edit() {
        DBDao dao = new DBDao(getApplicationContext());
        dao.open();

        UserBaseInfo userInfo = new UserBaseInfo();
        userInfo.setInfoId(id);
        userInfo.setContact(name.getText().toString());
        userInfo.setContactPhone(phone.getText().toString());

        long result = dao.editContact(userInfo);
        dao.close();

        if (result > 0) {
            Toast.makeText(this, "Contact edited successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to edit contact", Toast.LENGTH_SHORT).show();
        }
    }
}
