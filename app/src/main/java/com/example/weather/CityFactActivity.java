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

        dataTextView = findViewById(R.id.dataTextView);

        cityEditText = findViewById(R.id.cityEditText);

        Button searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCity();
            }
        });
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
