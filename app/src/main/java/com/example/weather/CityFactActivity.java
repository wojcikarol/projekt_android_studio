package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CityFactActivity extends AppCompatActivity {

    ListView placesListView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_fact);

        placesListView = findViewById(R.id.placesListView);

        Intent intent = getIntent();
        ArrayList<String> placesList = intent.getStringArrayListExtra("PLACES_LIST");

        if (placesList != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, placesList);
            placesListView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No interesting facts available", Toast.LENGTH_SHORT).show();
        }
    }
}

