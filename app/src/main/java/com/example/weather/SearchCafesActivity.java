package com.example.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.Normalizer;

public class SearchCafesActivity extends AppCompatActivity {

    private EditText cityEditText;
    private static final String TAG = "SearchCafesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cafes);

        cityEditText = findViewById(R.id.city);

        loadData();

        findViewById(R.id.checkBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = cityEditText.getText().toString().trim();
                if (!city.isEmpty()) {
                    String normalizedCity = normalizeCityName(city);
                    Log.d(TAG, "Normalized city: " + normalizedCity);
                    saveData(normalizedCity);

                    Intent intent = new Intent(SearchCafesActivity.this, CafesActivity.class);
                    intent.putExtra("CITY_NAME", normalizedCity);
                    startActivity(intent);
                }
            }
        });
    }

    private String normalizeCityName(String cityName) {
        String normalizedCity = cityName.toLowerCase();

        normalizedCity = Normalizer.normalize(normalizedCity, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        normalizedCity = normalizedCity.replaceAll("[^a-zA-Z0-9]", "");

        return normalizedCity;
    }

    private void saveData(String cityName) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CITY_NAME", cityName);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        String loadedCityName = sharedPreferences.getString("CITY_NAME", "");
        if (!loadedCityName.isEmpty()) {
            cityEditText.setText(loadedCityName);
        }
    }
}
