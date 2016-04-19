package com.altrovis.newprewit.Bussines.Finished;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.altrovis.newprewit.Bussines.GlobalFunction;
import com.altrovis.newprewit.Entities.GlobalVariable;
import com.altrovis.newprewit.Entities.WorkItem;

import java.util.ArrayList;

/**
 * Created by ricki on 4/6/2016.
 */
public class FinishedAsyncTaskToMe extends AsyncTask<Void, Void, Void> {
    ProgressDialog progressDialog;
    Context context;
    FinishedAdapter adapter;

    String url = GlobalVariable.UrlGetAllFinishedWorkItemsToMe;
    String param1 = "?username=";
    String param2 = "&accessToken=";
    String param3 = "&lastRetrievedID=";

    String completeURL = "";
    String username = "";
    String accessToken = "";

    public FinishedAsyncTaskToMe(Context context, FinishedAdapter adapter){
        this.context = context;
        this.adapter = adapter;

        SharedPreferences login = context.getSharedPreferences("login", context.MODE_PRIVATE);
        username = login.getString("username", "");
        accessToken = login.getString("accesstoken","");

        completeURL = url.concat(param1).concat(username).concat(param2).concat(accessToken)
                .concat(param3).concat(String.valueOf(GlobalVariable.LastID_Finished_ToMe));

        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.show();
    }

    protected void onPreExecute() {
        super.onPreExecute();

        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            GlobalVariable.listOfWorkItemFinishedToMe = FinishedHelper.getListOfWorkItem(completeURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if(GlobalFunction.isOnline(context)){
            adapter.addAll(GlobalVariable.listOfWorkItemFinishedToMe);

            if(GlobalVariable.listOfWorkItemFinishedToMe.size() > 0){
                int lastRetrivedID = GlobalVariable.listOfWorkItemFinishedToMe.get(GlobalVariable.listOfWorkItemFinishedToMe.size() - 1).getID();
                GlobalVariable.LastID_Finished_ToMe = lastRetrivedID;
            } else {
                GlobalVariable.All_FinishedToMe_Retrieved = true;
            }

            adapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(context, "Koneksi bermasalah", Toast.LENGTH_LONG).show();
        }

    }
}
