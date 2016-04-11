package com.altrovis.newprewit.Bussines.EditCompleteWorkItem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.altrovis.newprewit.Bussines.GlobalFunction;
import com.altrovis.newprewit.Entities.GlobalVariable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Wisnu on 10/03/2016.
 */
public class EditWorkItemAsyncTask extends AsyncTask<Void, Void, JSONObject> {

    ProgressDialog progressDialog;
    Context context;

    String url = GlobalVariable.UrlEditWorkItem;
    String param1 = "?workItemID=";
    String param2 = "&description=";
    String param3 = "&estimatedCompletion=";
    String param4 = "&username=";
    String param5 = "&accessToken=";

    String estimatedDate = "";
    String username = "";
    String accessToken = "";
    String description = "";
    int workItemID;

    String completeUrl = "";

    public EditWorkItemAsyncTask(Context context, String description, String estimatedDate, int workItemID){

        this.context = context;
        this.estimatedDate =  estimatedDate;
        this.workItemID = workItemID;
        this.description = description;

        SharedPreferences login = context.getSharedPreferences("login", context.MODE_PRIVATE);
        username = login.getString("username", "");
        accessToken = login.getString("accesstoken","");

        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.show();

        try {
            completeUrl = url + param1 + this.workItemID
                              + param2 + this.description
                              + param3 + this.estimatedDate
                              + param4 + this.username
                              + param5 + this.accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onPreExecute() {
        super.onPreExecute();

        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        try {
            return GlobalFunction.GetJSONObject(completeUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if(result != null){
            try {
                boolean isSuccessful = result.getBoolean("IsSuccessful");
                if(isSuccessful){
                    Toast.makeText(context, "Data berhasil diubah", Toast.LENGTH_LONG).show();
                } else {
                    String errorMessage = result.getString("ErrorMessage");
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }
}
