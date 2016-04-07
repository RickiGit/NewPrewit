package com.altrovis.newprewit.Bussines.AddNewWorkItem;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.altrovis.newprewit.Bussines.GlobalFunction;
import com.altrovis.newprewit.Entities.GlobalVariable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Wisnu on 10/03/2016.
 */
public class AddNewWorkItemAsyncTask extends AsyncTask<Void, Void, JSONObject> {

    ProgressDialog progressDialog;
    Context context;
    Dialog dialog;

    String url = GlobalVariable.UrlAddNewWorkItem;
    String param1 = "?description=";
    String param2 = "&projectID=";
    String param3 = "&assignedByProjectMemberID=";
    String param4 = "&assignedToProjectMemberID=";
    String param5 = "&createdBy=";
    String param6 = "&accessToken=";

    String description = "";
    String createdBy = "";
    String accessToken = "";
    int projectID, assignedByID, assignedToID;

    String completeUrl = "";

    public AddNewWorkItemAsyncTask(Context context, String description, int projectID,
                                   int assignedByID, int assignedToID, Dialog dialog){

        this.context = context;
        this.description = description;
        this.projectID = projectID;
        this.assignedByID = assignedByID;
        this.assignedToID = assignedToID;
        this.dialog = dialog;

        SharedPreferences login = context.getSharedPreferences("login", context.MODE_PRIVATE);
        createdBy = login.getString("username", "");
        accessToken = login.getString("accesstoken","");

        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.show();

        try {
            completeUrl = url.concat(param1).concat(URLEncoder.encode(description, "utf-8"))
                             .concat(param2).concat(String.valueOf(projectID))
                             .concat(param3).concat(String.valueOf(assignedByID))
                             .concat(param4).concat(String.valueOf(assignedToID))
                             .concat(param5).concat(createdBy)
                             .concat(param6).concat(accessToken);
        } catch (UnsupportedEncodingException e) {
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
                    Toast.makeText(context, "Data berhasil ditambahkan", Toast.LENGTH_LONG);
                } else {
                    String errorMessage = result.getString("ErrorMessage");
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG);
                }
            } catch (JSONException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
            }
        }
        dialog.dismiss();

    }
}
