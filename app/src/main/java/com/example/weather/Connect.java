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
    private static final String URL = "jdbc:postgresql://195.150.230.208:5432/2023_wiatr_jakub";
    private static final String USER = "2023_wiatr_jakub";
    private static final String PASSWORD = "36414";

    private ConnectionListener listener;
    private String city;

    public Connect(ConnectionListener listener, String city) {
        this.listener = listener;
        this.city = city;
    }

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

            city = normalizeCity(city);

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

    private String normalizeCity(String city) {
        String normalizedCity = Normalizer.normalize(city, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        return normalizedCity.toLowerCase();
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            listener.onQueryComplete(result); 
        } else {
            listener.onConnectionFailure();
        }
    }
}
