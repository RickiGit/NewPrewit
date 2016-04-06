package com.altrovis.newprewit.Bussines;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ricki on 4/5/2016.
 */
public class GlobalFunction {
    public static JSONArray GetJSONArray(String urlString) throws Exception {

        URL obj = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestMethod("GET");
        int responseCode = urlConnection.getResponseCode();

        try {
            if (responseCode == 200) {

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                JSONArray jsonArray = new JSONArray(response.toString());
                return jsonArray;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return null;
    }

    public static JSONObject GetJSONObject(String urlString) throws Exception {

        URL obj = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestMethod("GET");
        int responseCode = urlConnection.getResponseCode();

        try {
            if (responseCode == 200) {

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                return jsonObject;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return null;
    }


    public static boolean isOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }
}
