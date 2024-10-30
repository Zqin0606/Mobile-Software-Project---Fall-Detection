package com.example.oldersafe.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oldersafe.R;
import com.example.oldersafe.database.DBDao;
import com.example.oldersafe.bean.UserBaseInfo;

import java.util.UUID;

public class AddContactActivity extends AppCompatActivity {

    EditText name, phone; // Input fields for name and phone
    Button add; // Add button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // Initialize UI components
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        add = findViewById(R.id.add);

        // Set up the Add button click listener
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(); // Save contact when button is clicked
            }
        });
    }

    // Save contact information to the database
    private void save() {
        DBDao dao = new DBDao(getApplicationContext());
        dao.open(); // Open database connection

        UserBaseInfo userInfo = new UserBaseInfo();
        userInfo.setInfoId(UUID.randomUUID().toString()); // Assign a unique ID
        userInfo.setContact(name.getText().toString()); // Set contact name
        userInfo.setContactPhone(phone.getText().toString()); // Set contact phone

        // Add contact to the database
        long result = dao.addContact(userInfo);
        dao.close(); // Close database connection

        // Show success or failure message
        if (result > 0) {
            Toast.makeText(this, "Contact added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add contact", Toast.LENGTH_SHORT).show();
        }
    }
}
