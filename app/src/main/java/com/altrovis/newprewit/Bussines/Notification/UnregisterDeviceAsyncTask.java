package com.altrovis.newprewit.Bussines.Notification;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.altrovis.newprewit.ActivityLogin;
import com.altrovis.newprewit.ActivityMain;
import com.altrovis.newprewit.Bussines.GlobalFunction;
import com.altrovis.newprewit.Entities.GlobalVariable;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by Raufan on 27/06/2016.
 */
public class UnregisterDeviceAsyncTask extends AsyncTask<Void, Void, Void> {
    ActivityMain context;
    String username;
    String registrationID;
    String accessToken;

    String url = GlobalVariable.UrlUnregisterDeviceGCM;
    String param1 = "?username=";
    String param2 = "&registrationID=";
    String param3 = "&accessToken=";
    String errorMessage = "";

    String completeUrl = "";

    public UnregisterDeviceAsyncTask(ActivityMain context) {
        this.context = context;

        SharedPreferences login = context.getSharedPreferences("login", context.MODE_PRIVATE);
        username = login.getString("username", "");
        registrationID = login.getString("registrationID","");
        accessToken = login.getString("accesstoken","");
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            completeUrl = url.concat(param1).concat(username).concat(param2).concat(registrationID).concat(param3).concat(accessToken);

            GlobalFunction.GetJSONObject(completeUrl);

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
