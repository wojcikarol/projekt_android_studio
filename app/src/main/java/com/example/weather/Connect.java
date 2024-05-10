package com.example.weather;

import android.os.AsyncTask;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Normalizer;

public class Connect extends AsyncTask<Void, Void, String> {
    // Dane do połączenia z bazą danych PostgreSQL
    private static final String URL = "jdbc:postgresql://195.150.230.208:5432/2023_wiatr_jakub";
    private static final String USER = "2023_wiatr_jakub";
    private static final String PASSWORD = "36414";

    private ConnectionListener listener;
    private String city;

    // Konstruktor przyjmuje listenera jako argument
    public Connect(ConnectionListener listener, String city) {
        this.listener = listener;
        this.city = city;
    }

    // Interfejs ConnectionListener
    public interface ConnectionListener {
        void onQueryComplete(String result);
        void onConnectionFailure();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();

            // Normalizacja wprowadzonego miasta
            city = normalizeCity(city);

            // Używamy funkcji LOWER(), aby przekonwertować miasto w bazie danych na małe litery
            ResultSet resultSet = statement.executeQuery("SELECT ciekawostka FROM mojabazadanych.ciekawostki WHERE LOWER(miasto) = LOWER('" + city + "')");

            StringBuilder stringBuilder = new StringBuilder();
            while (resultSet.next()) {
                String ciekawostka = resultSet.getString("ciekawostka");
                stringBuilder.append(ciekawostka).append("\n");
            }

            resultSet.close();
            statement.close();
            connection.close();

            return stringBuilder.toString();
        } catch (ClassNotFoundException e) {
            Log.e("Connect", "ClassNotFoundException: " + e.getMessage());
        } catch (SQLException e) {
            Log.e("Connect", "SQLException: " + e.getMessage());
        }
        return null;
    }

    // Metoda do normalizacji miasta
    private String normalizeCity(String city) {
        // Normalizacja tekstu z użyciem Form.NFD i usuwanie znaków nieznakowych
        String normalizedCity = Normalizer.normalize(city, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", ""); // Usunięcie znaków nie-ASCII, np. polskich znaków diakrytycznych
        // Zwrócenie tekstu w małych literach
        return normalizedCity.toLowerCase();
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            listener.onQueryComplete(result); // Wywołanie metody w listenerze
        } else {
            listener.onConnectionFailure(); // Wywołanie metody w listenerze
        }
    }
}
