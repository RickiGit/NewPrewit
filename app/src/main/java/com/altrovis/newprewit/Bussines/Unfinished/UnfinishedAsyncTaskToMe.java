package com.altrovis.newprewit.Bussines.Unfinished;

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
 * Created by ricki on 4/5/2016.
 */
public class UnfinishedAsyncTaskToMe extends AsyncTask<Void, Void, Void> {

    Context context;
    UnfinishedAdapter adapter;
    ArrayList<WorkItem> listOfWorkItem;
    SwipeRefreshLayout swipeRefreshLayout;

    String url = GlobalVariable.UrlGetAllUnFinishedWorkItemsToMe;
    String param1 = "?username=";
    String param2 = "&accessToken=";
    String param3 = "&lastRetrievedID=";

    String completeURL = "";
    String username = "";
    String accessToken = "";

    public UnfinishedAsyncTaskToMe(Context context, UnfinishedAdapter adapter, SwipeRefreshLayout swipeRefreshLayout){
        this.context = context;
        this.adapter = adapter;
        this.swipeRefreshLayout = swipeRefreshLayout;

        SharedPreferences login = context.getSharedPreferences("login", context.MODE_PRIVATE);
        username = login.getString("username", "");
        accessToken = login.getString("accesstoken","");

        completeURL = url.concat(param1).concat(username).concat(param2).concat(accessToken).concat(param3).concat(String.valueOf(GlobalVariable.LastID_UnFinished_ToMe));
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
            listOfWorkItem = UnfinishedHelper.getListOfWorkItem(completeURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if(GlobalFunction.isOnline(context)){

            if(listOfWorkItem.size() > 0){
                adapter.addAll(listOfWorkItem);
                adapter.notifyDataSetChanged();
                int lastRetrivedID = listOfWorkItem.get(listOfWorkItem.size() - 1).getID();
                GlobalVariable.LastID_UnFinished_ToMe = lastRetrivedID;
            }else{
                GlobalVariable.All_UnFinishedToMe_Retrieved = true;
            }
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
