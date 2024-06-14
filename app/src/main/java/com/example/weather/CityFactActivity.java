package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CityFactActivity extends AppCompatActivity implements Connect.ConnectionListener {
    private TextView dataTextView;
    private EditText cityEditText;
    private boolean isBackgroundVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciekawostki);

        dataTextView = findViewById(R.id.dataTextView);
        cityEditText = findViewById(R.id.cityEditText);

        Button searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCity();
            }
        });

        loadData();
    }
    private void searchCity() {
        String city = cityEditText.getText().toString();
        new Connect(this, city).execute();
    }

    @Override
    public void onQueryComplete(String result) {
        if (result != null) {
            dataTextView.setText(result);
            showToast("Pobrano dane z bazy danych.");
            saveData(cityEditText.getText().toString());
        } else {
            showToast("Wystąpił błąd podczas pobierania danych z bazy danych.");
        }
    }

    @Override
    public void onConnectionFailure() {
        showToast("Nie udało się nawiązać połączenia z bazą danych.");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
            cityEditText.setText(loadedCityName);
        }
    }
}
