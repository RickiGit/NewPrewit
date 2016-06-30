package com.altrovis.newprewit.Bussines.Finished;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.altrovis.newprewit.Bussines.GlobalFunction;
import com.altrovis.newprewit.Entities.GlobalVariable;
import com.altrovis.newprewit.Entities.WorkItem;

import java.util.ArrayList;

/**
 * Created by ricki on 4/6/2016.
 */
public class FinishedAsyncTaskByMe extends AsyncTask<Void, Void, Void> {

    Context context;
    FinishedAdapter adapter;
    ArrayList<WorkItem> listOfFinishedWorkItem = new ArrayList<WorkItem>();
    SwipeRefreshLayout swipeRefreshLayout;

    String url = GlobalVariable.UrlGetAllFinishedWorkItemsByMe;
    String param1 = "?username=";
    String param2 = "&accessToken=";
    String param3 = "&lastRetrievedID=";

    String completeURL = "";
    String username = "";
    String accessToken = "";

    public FinishedAsyncTaskByMe(Context context, FinishedAdapter adapter, SwipeRefreshLayout swipeRefreshLayout){
        this.context = context;
        this.adapter = adapter;
        this.swipeRefreshLayout = swipeRefreshLayout;

        SharedPreferences login = context.getSharedPreferences("login", context.MODE_PRIVATE);
        username = login.getString("username", "");
        accessToken = login.getString("accesstoken","");

        completeURL = url.concat(param1).concat(username).concat(param2).concat(accessToken)
                .concat(param3).concat(String.valueOf(GlobalVariable.LastID_Finished_ByMe));
    }

    protected void onPreExecute() {
        super.onPreExecute();

        if(swipeRefreshLayout != null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            listOfFinishedWorkItem = FinishedHelper.getListOfWorkItem(completeURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if(GlobalFunction.isOnline(context)){
            adapter.addAll(listOfFinishedWorkItem);

            if(listOfFinishedWorkItem.size() > 0){
                int lastRetrivedID = listOfFinishedWorkItem.get(listOfFinishedWorkItem.size() - 1).getID();
                GlobalVariable.LastID_Finished_ByMe = lastRetrivedID;
            }

            if(listOfFinishedWorkItem.size() < 20){
                GlobalVariable.All_FinishedByMe_Retrieved = true;
            }

            adapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(context, "Koneksi bermasalah", Toast.LENGTH_LONG).show();
        }

        if(swipeRefreshLayout != null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }
}
