package com.example.oldersafe.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.oldersafe.R;
import java.util.ArrayList;


public class Main2Activity {
    ListView listView;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = findViewById(R.id.rv);

        getDatas();
    }

public void getDatas(){
    ArrayList<String> placeholderData = new ArrayList<>();
    placeholderData.add("Contact 1");
    placeholderData.add("Contact 2");
    placeholderData.add("Contact 3");

    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, placeholderData);
    listView.setAdapter(adapter);
}

}
