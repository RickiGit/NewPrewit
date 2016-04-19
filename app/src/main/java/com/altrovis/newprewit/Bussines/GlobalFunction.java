package com.altrovis.newprewit.Bussines;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.altrovis.newprewit.Bussines.EditCompleteWorkItem.CompleteWorkItemAsyncTask;
import com.altrovis.newprewit.Bussines.EditCompleteWorkItem.EditWorkItemAsyncTask;
import com.altrovis.newprewit.Bussines.EditCompleteWorkItem.EditWorkItemDescriptionAsyncTask;
import com.altrovis.newprewit.Bussines.EditCompleteWorkItem.HapusWorkItemAsyncTask;
import com.altrovis.newprewit.Entities.WorkItem;
import com.altrovis.newprewit.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ricki on 4/5/2016.
 */
public class GlobalFunction {

    public static JSONArray GetJSONArray(String urlString) throws Exception {

        URL obj = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestMethod("GET");
        int responseCode = urlConnection.getResponseCode();

        try {
            if (responseCode == 200) {

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                JSONArray jsonArray = new JSONArray(response.toString());
                return jsonArray;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return null;
    }

    public static JSONObject GetJSONObject(String urlString) throws Exception {

        URL obj = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestMethod("GET");
        int responseCode = urlConnection.getResponseCode();

        try {
            if (responseCode == 200) {

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                return jsonObject;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return null;
    }


    public static boolean isOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    static String deskripsi;
    static String tanggalEstimasi;

    public static void showDialog(final View view, final WorkItem workItem) {

        MaterialDialog dialog;
        SharedPreferences login = view.getContext().getSharedPreferences("login", view.getContext().MODE_PRIVATE);
        String username = login.getString("username", "");

        if (workItem.getAssignedTo().equalsIgnoreCase(username) && workItem.getEstimatedTime() != null) {
            dialog = new MaterialDialog.Builder(view.getContext()).title("Detail")
                    .customView(R.layout.dialog_set_workitem, true)
                    .positiveText("Done")
                    .negativeText("Save")
                    .neutralText("Cancel")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            new CompleteWorkItemAsyncTask(dialog.getContext(), workItem.getID()).execute();
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            new EditWorkItemAsyncTask(dialog.getContext(), deskripsi, tanggalEstimasi, workItem.getID()).execute();
                        }
                    })
                    .show();
        }else if (workItem.getAssignedTo().equalsIgnoreCase(username) && workItem.getEstimatedTime() == null) {
            dialog = new MaterialDialog.Builder(view.getContext()).title("Detail")
                    .customView(R.layout.dialog_set_workitem, true)
                    .positiveText("Save")
                    .neutralText("Cancel")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if(tanggalEstimasi.equals("")){
                                new EditWorkItemDescriptionAsyncTask(dialog.getContext(), deskripsi, workItem.getID()).execute();
                            }else{
                                new EditWorkItemAsyncTask(dialog.getContext(), deskripsi, tanggalEstimasi, workItem.getID()).execute();
                            }
                        }
                    })
                    .show();
        }else if(workItem.getAssignedBy().equalsIgnoreCase(username) && workItem.getEstimatedTime() == null){
            dialog = new MaterialDialog.Builder(view.getContext()).title("Detail")
                    .customView(R.layout.dialog_set_workitem, true)
                    .positiveText("Save")
                    .negativeText("Hapus")
                    .neutralText("Cancel")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            new HapusWorkItemAsyncTask(dialog.getContext(), workItem.getID()).execute();
                        }
                    })
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            new EditWorkItemDescriptionAsyncTask(dialog.getContext(), deskripsi, workItem.getID()).execute();
                        }
                    })
                    .show();
        } else if (workItem.getAssignedBy().equalsIgnoreCase(username) || (workItem.getAssignedTo().equalsIgnoreCase(username))) {
            dialog = new MaterialDialog.Builder(view.getContext()).title("Detail")
                    .customView(R.layout.dialog_set_workitem, true)
                    .positiveText("Save")
                    .neutralText("Cancel")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            new EditWorkItemAsyncTask(dialog.getContext(), deskripsi, tanggalEstimasi, workItem.getID()).execute();
                        }
                    })
                    .show();
        } else {
            dialog = new MaterialDialog.Builder(view.getContext()).title("Detail")
                    .customView(R.layout.dialog_set_workitem, true)
                    .positiveText("OK")
                    .show();
        }

        TextView assignedBy = (TextView) dialog.getCustomView().findViewById(R.id.textView_assignedBy);
        assignedBy.setText(workItem.getAssignedBy());
        TextView project = (TextView) dialog.getCustomView().findViewById(R.id.textView_project);
        project.setText(workItem.getProjectName());
        EditText editTextDescription = (EditText) dialog.getCustomView().findViewById(R.id.editText_description);
        editTextDescription.setText(workItem.getDescription());
        final EditText editTextEstimationDate = (EditText) dialog.getCustomView().findViewById(R.id.editText_EstimationDate);

        if(!workItem.getAssignedBy().equalsIgnoreCase(username)){
            editTextDescription.setEnabled(false);

            if(workItem.getEstimatedTime() != null){
                editTextEstimationDate.setEnabled(false);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String tanggal = sdf.format(workItem.getEstimatedTime());
                editTextEstimationDate.setText(tanggal);
            }
        }

        if(workItem.getEstimatedTime() != null){
            editTextDescription.setEnabled(false);
            editTextEstimationDate.setEnabled(false);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String tanggal = sdf.format(workItem.getEstimatedTime());
            editTextEstimationDate.setText(tanggal);
        }

        if(!username.equalsIgnoreCase(workItem.getAssignedTo())) {
            editTextEstimationDate.setEnabled(false);

            if(workItem.getEstimatedTime() != null){
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String tanggal = sdf.format(workItem.getEstimatedTime());
                editTextEstimationDate.setText(tanggal);
            }
        } else {
            if(workItem.getEstimatedTime() != null){
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String tanggal = sdf.format(workItem.getEstimatedTime());
                editTextEstimationDate.setText(tanggal);
            }

            editTextEstimationDate.setEnabled(true);
            editTextEstimationDate.setInputType(InputType.TYPE_NULL);
            editTextEstimationDate.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        Calendar currentDate = Calendar.getInstance();
                        int year = currentDate.get(Calendar.YEAR);
                        int month = currentDate.get(Calendar.MONTH);
                        int day = currentDate.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePicker = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                Calendar c = Calendar.getInstance();
                                c.set(selectedyear, selectedmonth, selectedday);

                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                String tanggal = sdf.format(c.getTime());
                                editTextEstimationDate.setText(tanggal);
                            }
                        }, year, month, day);
                        datePicker.show();
                    }
                    return false;
                }
            });
        }

        deskripsi = editTextDescription.getText().toString();
        tanggalEstimasi =  editTextEstimationDate.getText().toString();

        editTextDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                deskripsi = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        editTextEstimationDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tanggalEstimasi = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    public static boolean isConnected(Context context, URL url) {
        ConnectivityManager cm = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            try {
                try {
                    HttpURLConnection urlc = null;
                    urlc = (HttpURLConnection)url.openConnection();
                    urlc.setRequestProperty("User-Agent", "test");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(1000); // mTimeout is in seconds
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                Log.i("warning", "Error checking internet connection", e);
                return false;
            }
        }
        return false;
    }
}
