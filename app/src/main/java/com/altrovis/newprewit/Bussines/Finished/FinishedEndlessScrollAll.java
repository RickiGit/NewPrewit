package com.altrovis.newprewit.Bussines.Finished;

import android.os.AsyncTask;
import android.widget.AbsListView;

import com.altrovis.newprewit.ActivityMain;
import com.altrovis.newprewit.Entities.GlobalVariable;

/**
 * Created by ricki on 4/6/2016.
 */
public class FinishedEndlessScrollAll implements AbsListView.OnScrollListener {

    private int visibleThreshold = 2;

    private ActivityMain context;
    private FinishedAsyncTaskAll asyncTask;
    private FinishedAdapter adapter;

    public FinishedEndlessScrollAll(ActivityMain context, FinishedAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        if (!GlobalVariable.All_Finished_Retrieved &&
                totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold){
            if (asyncTask == null || asyncTask.getStatus() == AsyncTask.Status.FINISHED) {
                asyncTask = new FinishedAsyncTaskAll(context, adapter);
                asyncTask.execute();
            }
        }
    }
}
