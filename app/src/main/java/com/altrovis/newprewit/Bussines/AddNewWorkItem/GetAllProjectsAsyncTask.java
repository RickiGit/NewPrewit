package com.altrovis.newprewit.Bussines.AddNewWorkItem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.afollestad.materialdialogs.MaterialDialog;
import com.altrovis.newprewit.Entities.GlobalVariable;
import com.altrovis.newprewit.Entities.Project;
import com.altrovis.newprewit.R;

/**
 * Created by Wisnu on 10/03/2016.
 */
public class GetAllProjectsAsyncTask extends AsyncTask<Void, Void, Void> {

    ProgressDialog progressDialog;
    Context context;
    ArrayAdapter<Project> projectAdapter;
    View promptView;

    String url = GlobalVariable.UrlGetAllProjects;
    String param1 = "?username=";
    String param2 = "&accessToken=";

    String completeURL = "";
    String username = "";
    String accessToken = "";

    public GetAllProjectsAsyncTask(Context context, ArrayAdapter<Project> projectAdapter, MaterialDialog dialog) {

        this.context = context;
        this.projectAdapter = projectAdapter;
        this.promptView = dialog.getView();

        SharedPreferences login = context.getSharedPreferences("login", context.MODE_PRIVATE);
        username = login.getString("username", "");
        accessToken = login.getString("accesstoken", "");

        completeURL = url.concat(param1).concat(username)
                        .concat(param2).concat(accessToken);

        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.show();
    }

    protected void onPreExecute() {
        super.onPreExecute();

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            GlobalVariable.listOfProjects = NewWorkItemHelper.getListOfProject(completeURL);
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

        projectAdapter.clear();
        projectAdapter.addAll(GlobalVariable.listOfProjects);
        projectAdapter.notifyDataSetChanged();

        Spinner spinnerProject = (Spinner) promptView.findViewById(R.id.spinner_project);
        spinnerProject.setAdapter(projectAdapter);

    }
}