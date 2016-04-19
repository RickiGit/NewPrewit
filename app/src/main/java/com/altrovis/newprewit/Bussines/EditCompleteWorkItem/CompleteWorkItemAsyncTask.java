package com.altrovis.newprewit.Bussines.EditCompleteWorkItem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.altrovis.newprewit.Bussines.Finished.FinishedAsyncTaskAll;
import com.altrovis.newprewit.Bussines.Finished.FinishedAsyncTaskByMe;
import com.altrovis.newprewit.Bussines.Finished.FinishedAsyncTaskToMe;
import com.altrovis.newprewit.Bussines.GlobalFunction;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedAsyncTaskAll;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedAsyncTaskByMe;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedAsyncTaskToMe;
import com.altrovis.newprewit.Entities.GlobalVariable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Wisnu on 10/03/2016.
 */
public class CompleteWorkItemAsyncTask extends AsyncTask<Void, Void, JSONObject> {

    ProgressDialog progressDialog;
    Context context;

    String url = GlobalVariable.UrlCompleteWorkItem;
    String param1 = "?workItemID=";
    String param2 = "&username=";
    String param3 = "&accessToken=";

    int workItemID;
    String completeUrl = "";
    String username = "";
    String accessToken = "";

    public CompleteWorkItemAsyncTask(Context context, int workItemID){

        this.context = context;
        this.workItemID = workItemID;

        SharedPreferences login = context.getSharedPreferences("login", context.MODE_PRIVATE);
        username = login.getString("username", "");
        accessToken = login.getString("accesstoken","");

        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.show();

        try {
            completeUrl = url + param1 + this.workItemID
                              + param2 + this.username
                              + param3 + this.accessToken;
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
                    Toast.makeText(context, "Data Berhasil diubah", Toast.LENGTH_LONG).show();

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

    }
}
