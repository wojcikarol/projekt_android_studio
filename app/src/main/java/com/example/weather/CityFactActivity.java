package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CityFactActivity extends AppCompatActivity implements Connect.ConnectionListener {
    private TextView dataTextView;
    private EditText cityEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciekawostki);

        // Find the TextView where the data will be displayed
        dataTextView = findViewById(R.id.dataTextView);

        // Find the EditText for the city
        cityEditText = findViewById(R.id.cityEditText);

        // Find the search button
        Button searchButton = findViewById(R.id.searchButton);

        // Set click listener for the search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCity();
            }
        });
    }

    private void searchCity() {
        // Get the entered city
        String city = cityEditText.getText().toString();

        // Execute the AsyncTask to establish the database connection and execute the query
        new Connect(this, city).execute();
    }

    @Override
    public void onQueryComplete(String result) {
        // Handle the result from the database query
        if (result != null) {
            // Display the retrieved data in the TextView
            dataTextView.setText(result);
            showToast("Pobrano dane z bazy danych.");
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
}