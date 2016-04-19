package com.altrovis.newprewit.Bussines.AddNewWorkItem;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.altrovis.newprewit.ActivityMain;
import com.altrovis.newprewit.Bussines.Finished.FinishedAsyncTaskAll;
import com.altrovis.newprewit.Bussines.Finished.FinishedAsyncTaskByMe;
import com.altrovis.newprewit.Bussines.Finished.FinishedAsyncTaskToMe;
import com.altrovis.newprewit.Bussines.GlobalFunction;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedAsyncTaskAll;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedAsyncTaskByMe;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedAsyncTaskToMe;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedEndlessScrollToMe;
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
                    Toast.makeText(context, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show();

                    if(GlobalVariable.unfinishedAdapterToMe != null){
                        GlobalVariable.LastID_UnFinished_ToMe = -1;
                        GlobalVariable.All_UnFinishedToMe_Retrieved = false;
                        GlobalVariable.unfinishedAdapterToMe.clear();

                        UnfinishedAsyncTaskToMe asyncTaskToMe = new UnfinishedAsyncTaskToMe(context, GlobalVariable.unfinishedAdapterToMe);
                        asyncTaskToMe.execute();
                        GlobalVariable.unfinishedAdapterToMe.notifyDataSetChanged();
                    }

                    if(GlobalVariable.unfinishedAdapterByMe != null){
                        GlobalVariable.LastID_UnFinished_ByMe = -1;
                        GlobalVariable.All_UnFinishedByMe_Retrieved = false;
                        GlobalVariable.unfinishedAdapterByMe.clear();

                        UnfinishedAsyncTaskByMe asyncTaskByMe = new UnfinishedAsyncTaskByMe(context, GlobalVariable.unfinishedAdapterByMe);
                        asyncTaskByMe.execute();
                        GlobalVariable.unfinishedAdapterByMe.notifyDataSetChanged();
                    }

                    if(GlobalVariable.unfinishedAdapterAll != null){
                        GlobalVariable.LastID_UnFinished_All = -1;
                        GlobalVariable.All_UnFinished_Retrieved = false;
                        GlobalVariable.unfinishedAdapterAll.clear();

                        UnfinishedAsyncTaskAll asyncTaskAll = new UnfinishedAsyncTaskAll(context, GlobalVariable.unfinishedAdapterAll);
                        asyncTaskAll.execute();
                        GlobalVariable.unfinishedAdapterAll.notifyDataSetChanged();
                    }

                    if(GlobalVariable.finishedAdapterToMe != null){
                        GlobalVariable.LastID_Finished_ToMe = -1;
                        GlobalVariable.All_FinishedToMe_Retrieved = false;
                        GlobalVariable.finishedAdapterToMe.clear();

                        FinishedAsyncTaskToMe asyncTaskToMeFinished = new FinishedAsyncTaskToMe(context, GlobalVariable.finishedAdapterToMe);
                        asyncTaskToMeFinished.execute();
                        GlobalVariable.finishedAdapterToMe.notifyDataSetChanged();
                    }

                    if(GlobalVariable.finishedAdapterByMe != null){
                        GlobalVariable.LastID_Finished_ByMe = -1;
                        GlobalVariable.All_FinishedByMe_Retrieved = false;
                        GlobalVariable.finishedAdapterByMe.clear();

                        FinishedAsyncTaskByMe asyncTaskByMeFinished = new FinishedAsyncTaskByMe(context, GlobalVariable.finishedAdapterByMe);
                        asyncTaskByMeFinished.execute();
                        GlobalVariable.finishedAdapterByMe.notifyDataSetChanged();
                    }

                    if(GlobalVariable.finishedAdapterAll != null){
                        GlobalVariable.LastID_Finished_All = -1;
                        GlobalVariable.All_Finished_Retrieved = false;
                        GlobalVariable.finishedAdapterAll.clear();

                        FinishedAsyncTaskAll asyncTaskAllFinished = new FinishedAsyncTaskAll(context, GlobalVariable.finishedAdapterAll);
                        asyncTaskAllFinished.execute();
                        GlobalVariable.finishedAdapterAll.notifyDataSetChanged();
                    }
                } else {
                    String errorMessage = result.getString("ErrorMessage");
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        dialog.dismiss();
    }
}
