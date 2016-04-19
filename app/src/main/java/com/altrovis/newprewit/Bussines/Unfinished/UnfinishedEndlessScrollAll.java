package com.altrovis.newprewit.Bussines.Unfinished;

import android.os.AsyncTask;
import android.widget.AbsListView;

import com.altrovis.newprewit.ActivityMain;
import com.altrovis.newprewit.Entities.GlobalVariable;

/**
 * Created by ricki on 4/5/2016.
 */
public class UnfinishedEndlessScrollAll implements AbsListView.OnScrollListener {

    private int visibleThreshold = 1;

    private ActivityMain context;
    private UnfinishedAsyncTaskAll asyncTask;
    private UnfinishedAdapter adapter;

    public UnfinishedEndlessScrollAll(ActivityMain context, UnfinishedAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        if (!GlobalVariable.All_UnFinished_Retrieved &&
                totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            if (asyncTask == null || asyncTask.getStatus() == AsyncTask.Status.FINISHED) {
                asyncTask = new UnfinishedAsyncTaskAll(context, adapter);
                asyncTask.execute();
            }
        }
    }
}
