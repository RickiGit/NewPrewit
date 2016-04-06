package com.altrovis.newprewit.Bussines.Finished;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.altrovis.newprewit.Entities.WorkItem;
import com.altrovis.newprewit.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ricki on 4/5/2016.
 */
public class FinishedAdapter extends ArrayAdapter<WorkItem> {

    Context context;
    int resource;
    ArrayList<WorkItem> listOfFinished;
    DateFormat dateFormat;

    public FinishedAdapter(Context context, int resource, ArrayList<WorkItem> listOfFinished) {
        super(context, resource, listOfFinished);

        this.context = context;
        this.resource = resource;
        this.listOfFinished = listOfFinished;

        dateFormat = new SimpleDateFormat("EEEE, d MMMM y HH:mm", new Locale("id", "ID"));
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resource, viewGroup, false);
        }

        ImageView imageViewUser = (ImageView)view.findViewById(R.id.ImageViewUser);
        TextView textViewUser = (TextView)view.findViewById(R.id.TextViewUser);
        TextView textViewWork = (TextView)view.findViewById(R.id.TextViewWork);
        TextView textViewEstimated = (TextView)view.findViewById(R.id.TextViewEstimated);
        TextView textViewCompleted = (TextView)view.findViewById(R.id.TextViewCompleted);
        TextView textViewAssigned = (TextView)view.findViewById(R.id.TextViewAssigned);
        TextView textViewProject = (TextView)view.findViewById(R.id.TextViewProject);

        WorkItem workItem = listOfFinished.get(position);

        textViewUser.setText(workItem.getAssignedTo());

        String description = workItem.getDescription();
        if(description.length() > 55){
            description = description.substring(0, 35) + "...";
        }
        textViewWork.setText(description);

        textViewAssigned.setText(workItem.getAssignedBy());
        textViewProject.setText(workItem.getProjectName());
        textViewEstimated.setText("Estimated : " + dateFormat.format(workItem.getEstimatedTime()));
        textViewCompleted.setText("Completed : " + dateFormat.format(workItem.getCompletedTime()));

        return view;
    }
}
