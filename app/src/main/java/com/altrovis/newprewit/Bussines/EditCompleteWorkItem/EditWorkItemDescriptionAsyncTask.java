package com.altrovis.newprewit.Bussines.EditCompleteWorkItem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.altrovis.newprewit.Bussines.GlobalFunction;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedAsyncTaskAll;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedAsyncTaskByMe;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedAsyncTaskToMe;
import com.altrovis.newprewit.Entities.GlobalVariable;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by ricki on 4/18/2016.
 */
public class EditWorkItemDescriptionAsyncTask extends AsyncTask<Void, Void, JSONObject> {
    ProgressDialog progressDialog;
    Context context;

    String url = GlobalVariable.UrlEditWorkItemDescription;
    String param1 = "?workItemID=";
    String param2 = "&description=";
    String param3 = "&username=";
    String param4 = "&accessToken=";

    String username = "";
    String accessToken = "";
    String description = "";
    int workItemID;

    String completeUrl = "";

    public EditWorkItemDescriptionAsyncTask(Context context, String description, int workItemID){

        this.context = context;
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
                    + param2 + URLEncoder.encode(this.description, "UTF-8")
                    + param3 + this.username
                    + param4 + this.accessToken;
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

                    if(GlobalVariable.unfinishedAdapterToMe != null){
                        GlobalVariable.LastID_UnFinished_ToMe = -1;
                        GlobalVariable.All_UnFinishedToMe_Retrieved = false;
                        GlobalVariable.unfinishedAdapterToMe.clear();
                    }

                    if(GlobalVariable.unfinishedAdapterByMe != null){
                        GlobalVariable.LastID_UnFinished_ByMe = -1;
                        GlobalVariable.All_UnFinishedByMe_Retrieved = false;
                        GlobalVariable.unfinishedAdapterByMe.clear();
                    }

                    if(GlobalVariable.unfinishedAdapterAll != null){
                        GlobalVariable.LastID_UnFinished_All = -1;
                        GlobalVariable.All_UnFinished_Retrieved = false;
                        GlobalVariable.unfinishedAdapterAll.clear();
                    }
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
