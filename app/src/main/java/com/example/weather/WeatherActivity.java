package com.example.weather;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {
    WeatherResponse weatherResponse;
    String cityName;
    TextView timeLabel;
    TextView cityNameLabel;
    TextView tempBtn;
    TextView pressBtn;
    TextView information;
    ImageView imageView;
    ImageView imageBack;
    SwipeRefreshLayout swipeRefreshLayout;


    private Handler handler;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        cityName = getIntent().getStringExtra("CITY_NAME");
        cityNameLabel = findViewById(R.id.cityNameLabel);
        timeLabel = findViewById(R.id.timeLabel);
        tempBtn = findViewById(R.id.tempBtn);
        pressBtn = findViewById(R.id.pressBtn);
        imageView = findViewById(R.id.imageView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        handler = new Handler();
        imageBack = findViewById(R.id.imageBack);
        information = findViewById(R.id.information);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkAvailable()) {
                    refreshWeatherData(cityName);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(WeatherActivity.this, "Internet connection required", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fetchWeatherData(cityName);
        updateData(cityName);
    }

    private String convertCityNameToFileName(String cityName) {
        cityName = cityName.toLowerCase(Locale.getDefault());

        cityName = cityName.replaceAll("ą", "a");
        cityName = cityName.replaceAll("ć", "c");
        cityName = cityName.replaceAll("ę", "e");
        cityName = cityName.replaceAll("ł", "l");
        cityName = cityName.replaceAll("ń", "n");
        cityName = cityName.replaceAll("ó", "o");
        cityName = cityName.replaceAll("ś", "s");
        cityName = cityName.replaceAll("ż", "z");
        cityName = cityName.replaceAll("ź", "z");

        if (cityName.equalsIgnoreCase("warszawa")) {
            cityName = "warsaw";
        }

        return cityName;
    }


    public void fetchWeatherData(String cityName) {
        String baseUrl = "http://api.openweathermap.org/data/2.5/";
        String apiKey = "749561a315b14523a8f5f1ef95e45864";
        String units = "metric";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        Call<WeatherResponse> call = weatherAPI.getFullWeatherData(cityName + "", units, apiKey);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (!response.isSuccessful()) {
                    if (response.code() == 404) {
                        Intent intent = new Intent(WeatherActivity.this, SearchActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        cityNameLabel.setText("Code " + response.code());
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }

                weatherResponse = response.body();
                double temperature = weatherResponse.getMain().getTemperature();
                double pressure = weatherResponse.getMain().getPressure();
                String name = weatherResponse.getName();
                int timeZone = weatherResponse.getTimezone();

                String currentTimezone = getCurrentTimeWithTimezone(timeZone);

                List<WeatherResponse.WeatherInfo> weatherInfoList = weatherResponse.getWeatherInfoList();
                if (weatherInfoList != null && !weatherInfoList.isEmpty()) {
                    String iconCode = weatherInfoList.get(0).getIcon();
                    String iconUrl = "http://openweathermap.org/img/w/" + iconCode + ".png";
                    Picasso.get().load(iconUrl).into(imageView);
                }
                int cityImageResource = getResourceIdForCity(convertCityNameToFileName(cityName));
                if (cityImageResource != 0) {
                    imageBack.setImageResource(cityImageResource);
                } else {
                }
                String iconCode = weatherInfoList.get(0).getIcon();

                String attraction = getAttractionForWeather(cityName, iconCode);
                information.setText(attraction);

                cityNameLabel.setText(name);
                tempBtn.setText(temperature + "°C");
                pressBtn.setText(pressure + " hPa");
                timeLabel.setText(currentTimezone);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                cityNameLabel.setText(t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private String getAttractionForWeather(String cityName, String iconCode) {
        if (cityName.equalsIgnoreCase("Warszawa")) {
            if (isSunny(iconCode) || isPartlyCloudy(iconCode)) {
                return "Zalecamy spacer po Starym Mieście i zwiedzanie Zameku Królewskiego.";
            } else if (isRainy(iconCode)) {
                return "W deszczu warto odwiedzić Muzeum Narodowe lub Pałac Kultury i Nauki.";
            } else if (isCloudy(iconCode)) {
                return "W zachmurzony dzień warto udać się na spacer nad Wisłę lub do Łazienek Królewskich.";
            } else if (isFoggy(iconCode)) {
                return "W mglisty dzień możesz odwiedzić Muzeum Powstania Warszawskiego lub spacerować po Parku Łazienkowskim.";
            }
        }

        if (cityName.equalsIgnoreCase("Krakow")) {
            if (isSunny(iconCode) || isPartlyCloudy(iconCode)) {
                return "Zachęcamy do zwiedzania Wawelu i spaceru po Rynku Głównym.";
            } else if (isRainy(iconCode)) {
                return "W deszczu warto odwiedzić Podziemia Rynku oraz Muzeum Narodowe.";
            } else if (isCloudy(iconCode)) {
                return "W zachmurzony dzień można udać się na spacer po Plantach lub do Barbakanu.";
            } else if (isFoggy(iconCode)) {
                return "W mglisty dzień możesz zwiedzać Kazimierz lub udać się na Wawel.";
            }
        }

        if (cityName.equalsIgnoreCase("Wroclaw")) {
            if (isSunny(iconCode) || isPartlyCloudy(iconCode)) {
                return "Spacer po Rynku lub odwiedzenie Ostrowa Tumskiego to dobry pomysł.";
            } else if (isRainy(iconCode)) {
                return "W deszczu warto skorzystać z Muzeum Narodowego lub Hali Stulecia.";
            } else if (isCloudy(iconCode)) {
                return "W zachmurzony dzień można udać się na spacer wzdłuż rzeki Oławy lub do Ogrodu Japońskiego.";
            } else if (isFoggy(iconCode)) {
                return "W mglisty dzień zwiedzanie Katedry Świętej Marii Magdaleny lub Ossolineum to dobry pomysł.";
            }
        }

        if (cityName.equalsIgnoreCase("Gdansk")) {
            if (isSunny(iconCode) || isPartlyCloudy(iconCode)) {
                return "Spacer po Starym Mieście i wzdłuż ul. Długiej to must-have.";
            } else if (isRainy(iconCode)) {
                return "W deszczu warto odwiedzić Muzeum II Wojny Światowej lub Żurawia Nad Motławą.";
            } else if (isCloudy(iconCode)) {
                return "W zachmurzony dzień warto skorzystać z Europejskiego Centrum Solidarności lub spacerować po Wyspie Spichrzów.";
            } else if (isFoggy(iconCode)) {
                return "W mglisty dzień warto zwiedzić Kościół Mariacki lub Muzeum Archeologiczne.";
            }
        }

        if (cityName.equalsIgnoreCase("Hel")) {
            if (isSunny(iconCode) || isPartlyCloudy(iconCode)) {
                return "Kąpiel na plaży lub spacer po molo to świetne pomysły w słoneczny dzień.";
            } else if (isRainy(iconCode)) {
                return "W deszczu warto odwiedzić Muzeum Rybołówstwa Morskiego lub Muzeum Obrony Wybrzeża.";
            } else if (isCloudy(iconCode)) {
                return "W zachmurzony dzień można udać się na rejs statkiem wokół Półwyspu Helskiego lub wizytę w Skansenie Rybackim.";
            } else if (isFoggy(iconCode)) {
                return "W mglisty dzień warto zwiedzić Latarnię Morską lub spacerować po Klifie Orłowskim.";
            }
        }

        if (cityName.equalsIgnoreCase("Zakopane")) {
            if (isSunny(iconCode) || isPartlyCloudy(iconCode)) {
                return "W słoneczny dzień zachęcamy do wspinaczki na Gubałówkę lub spaceru po Krupówkach.";
            } else if (isRainy(iconCode)) {
                return "W deszczu warto odwiedzić Muzeum Tatrzańskie lub Krupówki ściągają do Pijalni Głównej.";
            } else if (isCloudy(iconCode)) {
                return "W zachmurzony dzień można udać się na spacer wzdłuż Doliny Kościeliskiej lub wizytę w Willi Koliba.";
            } else if (isFoggy(iconCode)) {
                return "W mglisty dzień możesz zwiedzać Jaskinię Mroźną lub spacerować do Doliny Chochołowskiej.";
            }
        }

        if (cityName.equalsIgnoreCase("Tarnow")) {
            if (isSunny(iconCode) || isPartlyCloudy(iconCode)) {
                return "Zachęcamy do spaceru po Starym Mieście i zwiedzania Ratusza oraz Katedry.";
            } else if (isRainy(iconCode)) {
                return "W deszczu warto odwiedzić Muzeum Diecezjalne oraz spędzić czas w Kawiarni Pod Dominikańskim Aniołem.";
            } else if (isCloudy(iconCode)) {
                return "W zachmurzony dzień można udać się na spacer do Parku Strzeleckiego lub zwiedzić Bazylikę św. Marcina.";
            } else if (isFoggy(iconCode)) {
                return "W mglisty dzień warto odwiedzić Zamek w Tarnowie lub spacerować nad brzegiem rzeki Biała.";
            }
        }

        if (cityName.equalsIgnoreCase("Szczecin")) {
            if (isSunny(iconCode) || isPartlyCloudy(iconCode)) {
                return "Zachęcamy do spaceru po Wałach Chrobrego i zwiedzania Zamku Książąt Pomorskich.";
            } else if (isRainy(iconCode)) {
                return "W deszczu warto odwiedzić Muzeum Narodowe i Filharmonię Szczecińską.";
            } else if (isCloudy(iconCode)) {
                return "W zachmurzony dzień można udać się na spacer do Parku Kasprowicza lub odwiedzić ZOO.";
            } else if (isFoggy(iconCode)) {
                return "W mglisty dzień warto zwiedzić Bulwary w Szczecinie i spacerować nad Odrą.";
            }
        }

        if (cityName.equalsIgnoreCase("Zamosc")) {
            if (isSunny(iconCode) || isPartlyCloudy(iconCode)) {
                return "Zachęcamy do spaceru po Starym Mieście, zwiedzania Zamojskiego Rynku oraz Ratusza.";
            } else if (isRainy(iconCode)) {
                return "W deszczu warto odwiedzić Zamojskie Podziemia Turystyczne i Zamojski Dom Kultury.";
            } else if (isCloudy(iconCode)) {
                return "W zachmurzony dzień można udać się na spacer do Parku Kilińskiego lub na bulwary.";
            } else if (isFoggy(iconCode)) {
                return "W mglisty dzień warto zwiedzić Twierdzę Zamość i spacerować po Plantach.";
            }
        }


        return "";
    }


    private int getResourceIdForCity(String cityName) {
        return getResources().getIdentifier(cityName, "drawable", getPackageName());
    }
    private boolean isFoggy(String iconCode) {
        return iconCode.equals("50n"); // Kod ikony dla mglistej pogody w dzień
    }

    private boolean isSunny(String iconCode) {
        return iconCode.equals("01n") || iconCode.equals("01d"); // Kod ikony dla słonecznej pogody w dzień
    }

    private boolean isPartlyCloudy(String iconCode) {
        return iconCode.equals("02n") || iconCode.equals("03n") || iconCode.equals("02d") || iconCode.equals("03d"); // Kody ikon dla częściowo zachmurzonej pogody w dzień
    }

    private boolean isCloudy(String iconCode) {
        return iconCode.equals("04n") || iconCode.equals("04d"); // Kod ikony dla całkowicie zachmurzonej pogody w dzień
    }

    private boolean isRainy(String iconCode) {
        return iconCode.startsWith("09") || iconCode.startsWith("10"); // Kody ikon dla deszczowej pogody
    }

    boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if(connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
    private void updateData(String cityName) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isNetworkAvailable()) {
                    refreshWeatherData(cityName);
                    handler.postDelayed(this, 300000);
                } else {
                    handler.removeCallbacks(this);
                    Toast.makeText(WeatherActivity.this, "Internet connection required", Toast.LENGTH_SHORT).show();
                }
            }
        }, 0);
    }

    private void refreshWeatherData(String cityName) {
        fetchWeatherData(cityName);
    }

    private String getCurrentTimeWithTimezone(int timezoneOffsetInSeconds) {
        long currentTimeMillis = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);

        calendar.add(Calendar.SECOND, timezoneOffsetInSeconds);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }
}


