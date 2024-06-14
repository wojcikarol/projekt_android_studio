package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    EditText city;
    Button checkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = findViewById(R.id.city);
        checkBtn = findViewById(R.id.checkBtn);

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    check();
                } else {
                    Toast.makeText(SearchActivity.this, "Internet connection required", Toast.LENGTH_SHORT).show();
                }
            }
        });
        loadData();
    }

    public void check() {
        String cityName = city.getText().toString();
        saveData(cityName);
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra("CITY_NAME", cityName);
        startActivity(intent);
    }    boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            if(connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }
            return false;
        }


    private void saveData(String output) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CITY_NAME", output);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String loadedCityName = sharedPreferences.getString("CITY_NAME", "");
        if (!loadedCityName.isEmpty()) {
            city.setText(loadedCityName);
        }
    }
}