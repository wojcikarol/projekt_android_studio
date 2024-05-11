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

public class LocalizationActivity extends AppCompatActivity {

    ListView placesListView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localization);

        placesListView = findViewById(R.id.placesListView);

        Intent intent = getIntent();
        ArrayList<String> placesList = intent.getStringArrayListExtra("PLACES_LIST");

        if (placesList != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, placesList);
            placesListView.setAdapter(adapter);

            placesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedPlace = (String) parent.getItemAtPosition(position);

                    String streetViewUrl = prepareStreetViewUrl(selectedPlace);

                    if (!streetViewUrl.isEmpty()) {
                        Intent streetViewIntent = new Intent(LocalizationActivity.this, StreetViewActivity.class);
                        streetViewIntent.putExtra("STREET_VIEW_URL", streetViewUrl);
                        startActivity(streetViewIntent);
                    } else {
                        Toast.makeText(LocalizationActivity.this, "Brak danych o wybranym miejscu", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Brak danych o miejscach do wyświetlenia", Toast.LENGTH_SHORT).show();
        }
    }


    private String prepareStreetViewUrl(String place) {
        if (place.equalsIgnoreCase("Zakrzówek")) {
            return "https://www.google.com/maps/place/Zakrzówek,+Kraków/@50.0376706,19.8968686,14z/data=!3m1!4b1!4m6!3m5!1s0x47165c823f49590b:0xd66b8a41b5e6db48!8m2!3d50.0412396!4d19.9176933!16s%2Fg%2F1tfhf4pt?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Wawel")) {
            return "https://www.google.com/maps/place/Wawel/@50.0551759,19.9336239,17z/data=!4m6!3m5!1s0x47165b4bbd81c7c3:0xcdc02021d897c6e9!8m2!3d50.0551759!4d19.9336239!16s%2Fg%2F11hfls6mvr?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Ojcowski Park Narodowy (ok. Krakowa)")) {
            return "https://www.google.com/maps/place/Ojcowski+Park+Narodowy/@50.207055,19.8152959,17z/data=!3m1!4b1!4m6!3m5!1s0x471655f91045178f:0xcd136a085c8ae7f1!8m2!3d50.2070516!4d19.8178762!16zL20vMGJjMjJo?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Krupówki")) {
            return "https://www.google.com/maps/place/Krupówki,+34-500+Zakopane/@49.2935806,19.9516325,17z/data=!4m6!3m5!1s0x4715f2913105dc39:0x633583beee07e071!8m2!3d49.2938545!4d19.9538936!16s%2Fg%2F121fl2cw?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Gubałówka")) {
            return "https://www.google.com/maps/place/Gubałówka/@49.3074592,19.8632829,13z/data=!3m1!4b1!4m6!3m5!1s0x4715ed9cd20c012d:0x231c41379ea8d072!8m2!3d49.3074272!4d19.9370173!16s%2Fm%2F026pl7y?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Wielka Krokiew")) {
            return "https://www.google.com/maps/place/COS+Wielka+Krokiew/@49.2794137,19.9613639,17z/data=!3m1!4b1!4m6!3m5!1s0x4715f27c6231ae9d:0x73ccad8058b06225!8m2!3d49.2794102!4d19.9639442!16s%2Fm%2F02844_f?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Termy Zakopiańskie")) {
            return "https://www.google.com/maps/place/Termy+Zakopiańskie/@49.2951081,19.9630621,17z/data=!3m1!4b1!4m6!3m5!1s0x4715f292dd475511:0xbdffa2ec978babff!8m2!3d49.2951046!4d19.9656424!16s%2Fg%2F11f_4l3rnp?hl=pl-PL&entry=ttu";
        } else if (place.equalsIgnoreCase("Kopiec Kościuszki")) {
        return "https://www.google.com/maps/place/Kopiec+Kościuszki/@50.0548449,19.8901756,17z/data=!3m1!4b1!4m6!3m5!1s0x47165b9196828c9d:0x67084144db045c33!8m2!3d50.0548415!4d19.8927559!16s%2Fm%2F02x5wxc?hl=pl-PL&entry=ttu";
    }
        return "";
    }
}
