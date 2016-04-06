package com.altrovis.newprewit.Bussines.Unfinished;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.altrovis.newprewit.Bussines.CustomImageViewCircle;
import com.altrovis.newprewit.Entities.WorkItem;
import com.altrovis.newprewit.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ricki on 4/5/2016.
 */
public class UnfinishedAdapter extends ArrayAdapter<WorkItem> {

    Context context;
    int resource;
    ArrayList<WorkItem> listOfUnfinished;
    DateFormat dateFormat;

    public UnfinishedAdapter(Context context, int resource, ArrayList<WorkItem> listOfUnfinished) {
        super(context, resource, listOfUnfinished);

        this.context = context;
        this.resource = resource;
        this.listOfUnfinished = listOfUnfinished;

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
        TextView textViewCreated = (TextView)view.findViewById(R.id.TextViewCreated);
        TextView textViewWork = (TextView)view.findViewById(R.id.TextViewWork);
        //ImageView imageViewAssigned = (ImageView)view.findViewById(R.id.ImageViewAssigned);
        TextView textViewAssigned = (TextView)view.findViewById(R.id.TextViewAssigned);
        TextView textViewProject = (TextView)view.findViewById(R.id.TextViewProject);
        //ImageView imageViewKeterangan = (ImageView)view.findViewById(R.id.ImageViewKeterangan);

        WorkItem workItem = listOfUnfinished.get(position);

        textViewUser.setText(workItem.getUser().getNickname());
        textViewCreated.setText(dateFormat.format(workItem.getCreated()));
        String description = workItem.getDescription();
        if(description.length() > 30){
            description = description.substring(0, 30) + "...";
        }
        textViewWork.setText(description);
        textViewAssigned.setText(workItem.getAssignedBy());
        textViewProject.setText(workItem.getProjectName());

        String urlProfile = workItem.getUser().getUrlProfilPicture();
        Picasso.with(context)
                .load(urlProfile)
                .resize(55, 55)
                .placeholder(R.drawable.caps)
                .error(R.drawable.caps)
                .centerCrop()
                .transform(new CustomImageViewCircle())
                .into(imageViewUser);

        return view;
    }
}
