package com.altrovis.newprewit.Bussines.Finished;

import com.altrovis.newprewit.Bussines.GlobalFunction;
import com.altrovis.newprewit.Entities.User;
import com.altrovis.newprewit.Entities.WorkItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ricki on 4/6/2016.
 */
public class FinishedHelper {
    private static String TAG_ID = "ID";
    private static String TAG_DESCRIPTION = "Description";
    private static String TAG_CREATED = "Created";
    private static String TAG_ASSIGNED_BY = "AssignedBy";
    private static String TAG_ASSIGNED_TO = "AssignedTo";
    private static String TAG_PROJECT_NAME = "ProjectName";
    private static String TAG_ESTIMATION_DATE = "EstimationDate";
    private static String TAG_COMPLATE_DATE = "EndDate";
    private static String TAG_GAP_DAYS = "GapDays";
    private static String TAG_NICKNAME = "NickName";
    private static String TAG_URL_PROFILE = "ProfilPictureUrl";

    public static ArrayList<WorkItem> getListOfWorkItem(String url) throws Exception {

        JSONArray arrayOfWorkItem = GlobalFunction.GetJSONArray(url);
        ArrayList<WorkItem> listOfWorkItem = new ArrayList<WorkItem>();

        if (arrayOfWorkItem != null) {
            if (arrayOfWorkItem.length() > 0) {
                for (int j = 0; j < arrayOfWorkItem.length(); j++) {

                    JSONObject detailWorkItem = arrayOfWorkItem.getJSONObject(j);

                    WorkItem workItem = new WorkItem();

                    workItem.setID(detailWorkItem.getInt(TAG_ID));
                    workItem.setDescription(detailWorkItem.getString(TAG_DESCRIPTION));
                    workItem.setAssignedBy(detailWorkItem.getString(TAG_ASSIGNED_BY));
                    workItem.setAssignedTo(detailWorkItem.getString(TAG_ASSIGNED_TO));
                    workItem.setProjectName(detailWorkItem.getString(TAG_PROJECT_NAME));

                    String dateString = detailWorkItem.getString(TAG_CREATED).replaceAll("\\D", "");
                    long dateTimeStamp = Long.parseLong(dateString);
                    Timestamp timestamp = new Timestamp(dateTimeStamp);
                    workItem.setCreated(new Date(timestamp.getTime()));

                    String cek1 = detailWorkItem.getString(TAG_ESTIMATION_DATE);
                    if(cek1 != null && !cek1.equals("null")){
                        String dateString1 = detailWorkItem.getString(TAG_ESTIMATION_DATE).replaceAll("\\D", "");
                        long dateTimeStamp1 = Long.parseLong(dateString1);
                        Timestamp timestamp1 = new Timestamp(dateTimeStamp1);
                        workItem.setEstimatedTime(new Date(timestamp1.getTime()));
                    }

                    String cek2 = detailWorkItem.getString(TAG_COMPLATE_DATE);
                    if(cek2 != null && !cek2.equals("null")){
                        String dateString2 = detailWorkItem.getString(TAG_COMPLATE_DATE).replaceAll("\\D", "");
                        long dateTimeStamp2 = Long.parseLong(dateString2);
                        Timestamp timestamp2 = new Timestamp(dateTimeStamp2);
                        workItem.setCompletedTime(new Date(timestamp2.getTime()));
                    }

                    workItem.setGapDate(detailWorkItem.getInt(TAG_GAP_DAYS));

                    JSONObject user = detailWorkItem.getJSONObject("EmployeeProxy");
                    User profile = new User();
                    profile.setNickname(user.getString(TAG_NICKNAME));
                    profile.setUrlProfilPicture(user.getString(TAG_URL_PROFILE));
                    workItem.setUser(profile);

                    listOfWorkItem.add(workItem);
                }
            }
        }

        return listOfWorkItem;
    }
}
