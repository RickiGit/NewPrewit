package com.altrovis.newprewit.Bussines.Unfinished;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.altrovis.newprewit.Entities.GlobalVariable;
import com.altrovis.newprewit.Entities.WorkItem;

import java.util.ArrayList;

/**
 * Created by ricki on 4/5/2016.
 */
public class UnfinishedAsyncTaskToMe extends AsyncTask<Void, Void, Void> {
    ProgressDialog progressDialog;
    Context context;
    UnfinishedAdapter adapter;
    ArrayList<WorkItem> listOfUnfinishedWorkItem;

    String url = GlobalVariable.UrlGetAllUnFinishedWorkItemsToMe;
    String param1 = "?username=";
    String param2 = "&accessToken=";
    String param3 = "&lastRetrievedID=";

    String completeURL = "";
    String username = "";
    String accessToken = "";

    public UnfinishedAsyncTaskToMe(Context context, UnfinishedAdapter adapter){
        this.context = context;
        this.adapter = adapter;

        SharedPreferences login = context.getSharedPreferences("login", context.MODE_PRIVATE);
        username = login.getString("username", "");
        accessToken = login.getString("accesstoken","");

        completeURL = url.concat(param1).concat(username).concat(param2).concat(accessToken)
                .concat(param3).concat(String.valueOf(GlobalVariable.LastID_UnFinished_ToMe));

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
            listOfUnfinishedWorkItem = UnfinishedHelper.getListOfWorkItem(completeURL);
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

        adapter.addAll(listOfUnfinishedWorkItem);

        if(listOfUnfinishedWorkItem.size() > 0){
            int lastRetrivedID = listOfUnfinishedWorkItem.get(listOfUnfinishedWorkItem.size() - 1).getID();
            GlobalVariable.LastID_UnFinished_ToMe = lastRetrivedID;
        } else {
            GlobalVariable.All_UnFinishedToMe_Retrieved = true;
        }

        adapter.notifyDataSetChanged();
    }
}
