package com.altrovis.newprewit.Entities;

import com.altrovis.newprewit.ActivityMain;
import com.altrovis.newprewit.Bussines.Finished.FinishedAdapter;
import com.altrovis.newprewit.Bussines.Unfinished.UnfinishedAdapter;

import java.util.ArrayList;

/**
 * Created by ricki on 4/5/2016.
 */
public class GlobalVariable {

    // Login dan Logout
    public static String UrlLogin = "http://apps.altrovis.com/prewit/prewitservice.asmx/Login";
    public static String UrlLogout = "http://apps.altrovis.com/prewit/prewitservice.asmx/Logout";

    // Unfinished WorkItem
    public static String UrlGetAllUnFinishedWorkItemsToMe = "http://apps.altrovis.com/prewit/prewitservice.asmx/GetAllUnFinishedWorksItemToMe";
    public static String UrlGetAllUnFinishedWorkItemsByMe = "http://apps.altrovis.com/prewit/prewitservice.asmx/GetAllUnFinishedWorksItemByMe";
    public static String UrlGetAllUnFinishedWorkItems = "http://apps.altrovis.com/prewit/prewitservice.asmx/GetAllUnFinishedWorksItem";

    public static boolean All_UnFinished_Retrieved = false;
    public static boolean All_UnFinishedByMe_Retrieved = false;
    public static boolean All_UnFinishedToMe_Retrieved = false;

    public static int LastID_UnFinished_All = -1;
    public static int LastID_UnFinished_ByMe = -1;
    public static int LastID_UnFinished_ToMe = -1;

    public static ArrayList<WorkItem> listOfUnfinishedAll = new ArrayList<WorkItem>();
    public static ArrayList<WorkItem> listOfUnfinishedByMe = new ArrayList<WorkItem>();
    public static ArrayList<WorkItem> listOfUnfinishedToMe = new ArrayList<WorkItem>();

    // Finished WorkItem
    public static String UrlGetAllFinishedWorkItems = "http://apps.altrovis.com/prewit/prewitservice.asmx/GetAllFinishedWorksItem";
    public static String UrlGetAllFinishedWorkItemsByMe = "http://apps.altrovis.com/prewit/prewitservice.asmx/GetAllFinishedWorksItemByMe";
    public static String UrlGetAllFinishedWorkItemsToMe = "http://apps.altrovis.com/prewit/prewitservice.asmx/GetAllFinishedWorksItemToMe";

    public static boolean All_Finished_Retrieved = false;
    public static boolean All_FinishedByMe_Retrieved = false;
    public static boolean All_FinishedToMe_Retrieved = false;

    public static int LastID_Finished_All = -1;
    public static int LastID_Finished_ByMe = -1;
    public static int LastID_Finished_ToMe = -1;

    public static ArrayList<WorkItem> listOfFinished = new ArrayList<WorkItem>();
    public static ArrayList<WorkItem> listOfFinishedByMe = new ArrayList<WorkItem>();
    public static ArrayList<WorkItem> listOfFinishedToMe = new ArrayList<WorkItem>();

    public static String UrlGetAllProjects = "http://apps.altrovis.com/prewit/prewitservice.asmx/GetAllProjects";
    public static String UrlGetAllProjectMembers = "http://apps.altrovis.com/prewit/prewitservice.asmx/GetAllProjectMembers";
    public static String UrlAddNewWorkItem = "http://apps.altrovis.com/prewit/prewitservice.asmx/AddNewWorkItem";

    public static ArrayList<Project> listOfProjects = new ArrayList<Project>();
    public static ArrayList<ProjectMember> listOfProjectMembers = new ArrayList<ProjectMember>();

    public static String UrlEditWorkItemEstimation = "http://apps.altrovis.com/prewit/prewitservice.asmx/EditWorkItemEstimation";
    public static String UrlEditWorkItem = "http://apps.altrovis.com/prewit/prewitservice.asmx/EditWorkItem";
    public static String UrlCompleteWorkItem = "http://apps.altrovis.com/prewit/prewitservice.asmx/CompleteWorkItem";
    public static String UrlDeleteWorkItem = "http://apps.altrovis.com/prewit/prewitservice.asmx/DeleteWorkItem";
    public static String UrlEditWorkItemDescription = "http://apps.altrovis.com/prewit/prewitservice.asmx/EditWorkItemDescription";

    public static ActivityMain activityMain;

    public static ArrayList<WorkItem> listOfWorkItemUnfinishedToMe;
    public static ArrayList<WorkItem> listOfWorkItemUnfinishedByMe;
    public static ArrayList<WorkItem> listOfWorkItemUnfinishedAll;

    public static ArrayList<WorkItem> listOfWorkItemFinishedToMe;
    public static ArrayList<WorkItem> listOfWorkItemFinishedByMe;
    public static ArrayList<WorkItem> listOfWorkItemFinishedAll;

    public static UnfinishedAdapter unfinishedAdapterToMe;
    public static UnfinishedAdapter unfinishedAdapterByMe;
    public static UnfinishedAdapter unfinishedAdapterAll;

    public static FinishedAdapter finishedAdapterToMe;
    public static FinishedAdapter finishedAdapterByMe;
    public static FinishedAdapter finishedAdapterAll;

    public static Boolean fragmentFrom = true;

}
