package com.altrovis.newprewit.Bussines.AddNewWorkItem;

import com.altrovis.newprewit.Bussines.GlobalFunction;
import com.altrovis.newprewit.Entities.Project;
import com.altrovis.newprewit.Entities.ProjectMember;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Wisnu on 10/03/2016.
 */
public class NewWorkItemHelper {

    private static String TAG_ID = "ID";
    private static String TAG_NAME = "Name";
    private static String TAG_CREATED = "Created";
    private static String TAG_PROJECT_ID = "ProjectID";
    private static String TAG_USERNAME = "Username";

    public static ArrayList<ProjectMember> getListOfProjectMember(String url) throws Exception {

        JSONArray arrayOfProjectMember = GlobalFunction.GetJSONArray(url);
        ArrayList<ProjectMember> listOfProjectMember = new ArrayList<ProjectMember>();

        if (arrayOfProjectMember != null) {
            if (arrayOfProjectMember.length() > 0) {
                for (int j = 0; j < arrayOfProjectMember.length(); j++) {

                    JSONObject detailProjectMember = arrayOfProjectMember.getJSONObject(j);

                    ProjectMember projectMember = new ProjectMember();

                    projectMember.setID(detailProjectMember.getInt(TAG_ID));
                    projectMember.setProjectID(detailProjectMember.getInt(TAG_PROJECT_ID));
                    projectMember.setUsername(detailProjectMember.getString(TAG_USERNAME));

                    listOfProjectMember.add(projectMember);
                }
            }
        }

        return listOfProjectMember;
    }

    public static ArrayList<Project> getListOfProject(String url) throws Exception {

        JSONArray arrayOfProject = GlobalFunction.GetJSONArray(url);
        ArrayList<Project> listOfProject = new ArrayList<Project>();

        if (arrayOfProject != null) {
            if (arrayOfProject.length() > 0) {
                for (int j = 0; j < arrayOfProject.length(); j++) {

                    JSONObject detailProject = arrayOfProject.getJSONObject(j);

                    Project project = new Project();
                    project.setID(detailProject.getInt(TAG_ID));
                    project.setNama(detailProject.getString(TAG_NAME));

                    String dateString = detailProject.getString(TAG_CREATED).replaceAll("\\D", "");
                    long dateTimeStamp = Long.parseLong(dateString);
                    Timestamp timestamp = new Timestamp(dateTimeStamp);
                    project.setCreated(new Date(timestamp.getTime()));

                    listOfProject.add(project);
                }
            }
        }

        return listOfProject;
    }
}
