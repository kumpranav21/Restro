package com.example.restro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String > adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        listView = findViewById(R.id.lv1);

        list = new ArrayList<>();
        list.add("Kothrud Bowl Company");
        list.add("TGC The Gargi's Cafe");
        list.add("Vaishali");
        list.add("Exotica");
        list.add("Kaka Da Hotel");
        list.add("Sukh Sagar");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        if(list.contains(recyAdapter_homePage.quer)){
            adapter.getFilter().filter(recyAdapter_homePage.quer);
        }else{
            Toast.makeText(Search.this, "No Match found", Toast.LENGTH_LONG).show();
        }




    }
}