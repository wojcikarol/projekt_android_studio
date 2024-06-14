package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CiekaweLokalizacje extends AppCompatActivity {

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
                    Toast.makeText(CiekaweLokalizacje.this, "Internet connection required", Toast.LENGTH_SHORT).show();
                }
            }
        });
        loadData();
    }

    public void check() {
        String cityName = city.getText().toString();
        saveData(cityName);

        if (cityName.equalsIgnoreCase("Krakow") || cityName.equalsIgnoreCase("Kraków") || cityName.equalsIgnoreCase("Cracow")) {
            startLocalizationActivity(getKrakowPlaces());
        } else if (cityName.equalsIgnoreCase("Zakopane")) {
            startLocalizationActivity(getZakopanePlaces());
        } else if (cityName.equalsIgnoreCase("Hel")) {
            startLocalizationActivity(getHelPlaces());
        } else if (cityName.equalsIgnoreCase("Gdańsk") || cityName.equalsIgnoreCase("Gdansk")) {
            startLocalizationActivity(getGdanskPlaces());
        } else if (cityName.equalsIgnoreCase("Szczecin")) {
            startLocalizationActivity(getSzczecinPlaces());
        } else if (cityName.equalsIgnoreCase("Tarnow")) {
            startLocalizationActivity(getTarnowPlaces());
        } else if (cityName.equalsIgnoreCase("Zamość") || cityName.equalsIgnoreCase("Zamosc")) {
            startLocalizationActivity(getZamoscPlaces());
        } else if (cityName.equalsIgnoreCase("Warsaw") || cityName.equalsIgnoreCase("Warszawa")) {
            startLocalizationActivity(getWarsawPlaces());
        } else if (cityName.equalsIgnoreCase("Wrocław") || cityName.equalsIgnoreCase("Wroclaw")) {
            startLocalizationActivity(getWroclawPlaces());
        } else {
            Intent intent = new Intent(this, LocalizationActivity.class);
            intent.putExtra("CITY_NAME", cityName);
            startActivity(intent);
        }
    }


    private ArrayList<String> getKrakowPlaces() {
        ArrayList<String> interestingPlaces = new ArrayList<>();
        interestingPlaces.add("Zakrzówek");
        interestingPlaces.add("Wawel");
        interestingPlaces.add("Ojcowski Park Narodowy (ok. Krakowa)");
        interestingPlaces.add("Kopiec Kościuszki");
        return interestingPlaces;
    }

    private ArrayList<String> getZakopanePlaces() {
        ArrayList<String> interestingPlaces = new ArrayList<>();
        interestingPlaces.add("Gubałówka");
        interestingPlaces.add("Krupówki");
        interestingPlaces.add("Wielka Krokiew");
        interestingPlaces.add("Termy Zakopiańskie");
        return interestingPlaces;
    }

    private ArrayList<String> getHelPlaces() {
        ArrayList<String> interestingPlaces = new ArrayList<>();
        interestingPlaces.add("Muzeum Obrony Wybrzeża");
        interestingPlaces.add("Bulwar Nadmorski");
        interestingPlaces.add("Pomnik Neptuna");
        interestingPlaces.add("Latarnia Morska");
        return interestingPlaces;
    }

    private ArrayList<String> getGdanskPlaces() {
        ArrayList<String> interestingPlaces = new ArrayList<>();
        interestingPlaces.add("Plaża Górki Zachodnie");
        interestingPlaces.add("Jaśkowa Dolina");
        interestingPlaces.add("Park Oliwski im. Adama Mickiewicza");
        interestingPlaces.add("Europejskie Centrum Solidarności");
        return interestingPlaces;
    }

    private ArrayList<String> getSzczecinPlaces() {
        ArrayList<String> interestingPlaces = new ArrayList<>();
        interestingPlaces.add("Zamek Książąt Pomorskich");
        interestingPlaces.add("Muzeum Narodowe w Szczecinie");
        interestingPlaces.add("Wały Chrobrego");
        interestingPlaces.add("Filharmonia im. Mieczysława Karłowicza w Szczecinie");
        return interestingPlaces;
    }

    private ArrayList<String> getTarnowPlaces() {
        ArrayList<String> interestingPlaces = new ArrayList<>();
        interestingPlaces.add("Brama Seklerska");
        interestingPlaces.add("Rynek Główny w Tarnowie");
        interestingPlaces.add("Pomnik I Transportu Więźniów do KL Auschwitz");
        interestingPlaces.add("Pomnik Władysława Łokietka");
        return interestingPlaces;
    }

    private ArrayList<String> getZamoscPlaces() {
        ArrayList<String> interestingPlaces = new ArrayList<>();
        interestingPlaces.add("Twierdza Zamość");
        interestingPlaces.add("Kamienice Ormiańskie");
        interestingPlaces.add("Ratusz w Zamościu");
        interestingPlaces.add("Ogród zoologiczny w Zamościu im. Stefana Milera");
        return interestingPlaces;
    }

    private ArrayList<String> getWarsawPlaces(){
        ArrayList<String> interestingPlaces = new ArrayList<>();
        interestingPlaces.add("Pałac Kultury i Nauki w Warszawie");
        interestingPlaces.add("Centrum Nauki Kopernik");
        interestingPlaces.add("Muzeum Powstania Warszawskiego");
        interestingPlaces.add("Łazienki Królewskie");
        return interestingPlaces;
    }

    private ArrayList<String> getWroclawPlaces(){
        ArrayList<String> interestingPlaces = new ArrayList<>();
        interestingPlaces.add("Ogród Japoński");
        interestingPlaces.add("Punkt widokowy na Mostku Pokutnic");
        interestingPlaces.add("Muzeum Sztuki Mieszczańskiej");
        interestingPlaces.add("Wyspa Słodowa");
        return interestingPlaces;
    }

    private void startLocalizationActivity(ArrayList<String> placesList) {
        Intent intent = new Intent(this, LocalizationActivity.class);
        intent.putStringArrayListExtra("PLACES_LIST", placesList);
        startActivity(intent);
    }

    boolean isNetworkAvailable() {
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

