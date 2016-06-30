package com.altrovis.newprewit.Bussines.Unfinished;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;

import com.altrovis.newprewit.ActivityMain;
import com.altrovis.newprewit.Entities.GlobalVariable;

/**
 * Created by ricki on 4/5/2016.
 */
public class UnfinishedEndlessScrollByMe implements AbsListView.OnScrollListener {

    private int visibleThreshold = 1;

    private ActivityMain context;
    private UnfinishedAsyncTaskByMe asyncTask;
    private UnfinishedAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public UnfinishedEndlessScrollByMe(ActivityMain context, UnfinishedAdapter adapter, SwipeRefreshLayout swipeRefreshLayout) {
        this.context = context;
        this.adapter = adapter;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        if (!GlobalVariable.All_UnFinishedByMe_Retrieved &&
                totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            if (asyncTask == null || asyncTask.getStatus() == AsyncTask.Status.FINISHED) {
                asyncTask = new UnfinishedAsyncTaskByMe(context, adapter, swipeRefreshLayout);
                asyncTask.execute();
            }
        }
    }
}
