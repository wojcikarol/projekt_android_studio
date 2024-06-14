package com.example.weather;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class CafesActivity extends AppCompatActivity {

    private ListView cafesListView;
    private TextView cityHeader;
    private static final String TAG = "CafesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafes);

        cafesListView = findViewById(R.id.cafesListView);
        cityHeader = findViewById(R.id.cityHeader);

        String city = getIntent().getStringExtra("CITY_NAME");

        if (city != null && !city.isEmpty()) {
            String capitalizedCity = capitalizeFirstLetter(city);
            cityHeader.setText("Top 5 Cafes in " + capitalizedCity);
            Log.d(TAG, "Received city: " + city);
            List<String> cafes = getCafesForCity(city);
            if (cafes != null && !cafes.isEmpty()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, cafes);
                cafesListView.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No cafes found for this city", Toast.LENGTH_LONG).show();
            }
        }
    }

    private List<String> getCafesForCity(String city) {
        List<String> cafesList = new ArrayList<>();
        try {
            InputStream is = getResources().openRawResource(R.raw.cafes);
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1);
            String jsonText = new String(buffer);
            JSONObject jsonObj = new JSONObject(jsonText);
            JSONObject cities = jsonObj.getJSONObject("cities");

            String normalizedCity = normalizeCityName(city);
            Log.d(TAG, "Normalized city for comparison: " + normalizedCity);

            if (cities.has(normalizedCity)) {
                for (int i = 0; i < cities.getJSONArray(normalizedCity).length(); i++) {
                    cafesList.add(cities.getJSONArray(normalizedCity).getString(i));
                }
            } else {
                Log.d(TAG, "City not found in JSON: " + normalizedCity);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return cafesList;
    }

    private String normalizeCityName(String cityName) {
        String normalizedCity = cityName.toLowerCase();
        normalizedCity = Normalizer.normalize(normalizedCity, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        normalizedCity = normalizedCity.replaceAll("[^a-zA-Z0-9]", "");

        return normalizedCity;
    }

    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
