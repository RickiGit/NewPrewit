package com.altrovis.newprewit.Bussines.Unfinished;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.altrovis.newprewit.ActivityMain;
import com.altrovis.newprewit.Entities.GlobalVariable;

/**
 * Created by ricki on 4/6/2016.
 */
public class UnfinishedRefreshAll implements SwipeRefreshLayout.OnRefreshListener {

    ActivityMain context;
    SwipeRefreshLayout swipeRefreshLayoutBerita;
    ListView listViewUnfinishedAll;
    UnfinishedAdapter adapter;

    public UnfinishedRefreshAll(ActivityMain context, SwipeRefreshLayout swipeRefreshLayoutBerita,
                             ListView listViewUnfinishedAll, UnfinishedAdapter adapter) {
        this.context = context;
        this.swipeRefreshLayoutBerita = swipeRefreshLayoutBerita;
        this.listViewUnfinishedAll = listViewUnfinishedAll;
        this.adapter = adapter;
    }

    @Override
    public void onRefresh() {
        if (isOnline()) {
            try {
                updateUnfinishedAll();


            } catch (Exception ex) {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        swipeRefreshLayoutBerita.setRefreshing(false);
    }

    public void updateUnfinishedAll() throws Exception {
        GlobalVariable.listOfUnfinishedAll.clear();
    }

    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }
}
