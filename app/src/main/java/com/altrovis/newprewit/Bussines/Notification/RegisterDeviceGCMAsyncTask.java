package com.altrovis.newprewit.Bussines.Notification;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.altrovis.newprewit.ActivityLogin;
import com.altrovis.newprewit.ActivityMain;
import com.altrovis.newprewit.Bussines.GlobalFunction;
import com.altrovis.newprewit.Entities.GlobalVariable;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by Raufan on 27/06/2016.
 */
public class RegisterDeviceGCMAsyncTask extends AsyncTask<Void, Void, Void> {

    ActivityMain context;
    String username;
    String accessToken;

    String url = GlobalVariable.UrlRegisterDeviceGCM;
    String param1 = "?username=";
    String param2 = "&registrationID=";
    String param3 = "&accessToken=";
    String errorMessage = "";

    String completeUrl = "";

    public RegisterDeviceGCMAsyncTask(ActivityMain context) {
        this.context = context;

        SharedPreferences login = context.getSharedPreferences("login", context.MODE_PRIVATE);
        username = login.getString("username", "");
        accessToken = login.getString("accesstoken","");
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            GoogleCloudMessaging gcmInstance = GoogleCloudMessaging.getInstance(context);
            String registrationID = gcmInstance.register(GlobalVariable.SenderID);

            completeUrl = url.concat(param1).concat(username).concat(param2).concat(registrationID).concat(param3).concat(accessToken);

            GlobalFunction.GetJSONObject(completeUrl);

            SharedPreferences.Editor editor = context.getSharedPreferences(
                    "login", Context.MODE_PRIVATE).edit();
            editor.putString("registrationID", registrationID);

            editor.commit();

        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        return null;
    }

    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (errorMessage != null && errorMessage.length() > 0) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}