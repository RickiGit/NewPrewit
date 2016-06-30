package com.altrovis.newprewit.Bussines.Finished;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;

import com.altrovis.newprewit.ActivityMain;
import com.altrovis.newprewit.Entities.GlobalVariable;

/**
 * Created by ricki on 4/6/2016.
 */
public class FinishedEndlessScrollByMe implements AbsListView.OnScrollListener {

    private int visibleThreshold = 2;

    private ActivityMain context;
    private FinishedAsyncTaskByMe asyncTask;
    private FinishedAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public FinishedEndlessScrollByMe(ActivityMain context, FinishedAdapter adapter, SwipeRefreshLayout swipeRefreshLayout) {
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

        if (!GlobalVariable.All_FinishedByMe_Retrieved &&
                totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold){
            if (asyncTask == null || asyncTask.getStatus() == AsyncTask.Status.FINISHED) {
                asyncTask = new FinishedAsyncTaskByMe(context, adapter, swipeRefreshLayout);
                asyncTask.execute();
            }
        }
    }
}
