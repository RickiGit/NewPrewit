package com.altrovis.newprewit.Entities;

import java.util.Date;

/**
 * Created by ricki on 4/5/2016.
 */
public class WorkItem {
    private int ID;
    private String Description;
    private Date Created;
    private String AssignedBy;
    private String AssignedTo;
    private String ProjectName;
    private Date EstimatedTime;
    private Date CompletedTime;
    private int GapDate;
    private User user;
    private String AssignedByNickname;
    private Date Deadline;

    public WorkItem(){

    }

    public WorkItem(int ID, String description, Date created, String assignedBy, String assignedTo, String projectName, Date estimatedTime, Date completedTime, int gapDate, String assignedByNickname, Date deadline) {
        this.ID = ID;
        this.Description = description;
        this.Created = created;
        this.AssignedBy = assignedBy;
        this.AssignedTo = assignedTo;
        this.ProjectName = projectName;
        this.EstimatedTime = estimatedTime;
        this.CompletedTime = completedTime;
        this.GapDate = gapDate;
        this.AssignedByNickname = assignedByNickname;
        this.Deadline = deadline;
    }

    public Date getDeadline() {
        return Deadline;
    }

    public void setDeadline(Date deadline) {
        Deadline = deadline;
    }

    public String getAssignedByNickname() {
        return AssignedByNickname;
    }

    public void setAssignedByNickname(String assignedByNickname) {
        AssignedByNickname = assignedByNickname;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getCreated() {
        return Created;
    }

    public void setCreated(Date created) {
        Created = created;
    }

    public String getAssignedBy() {
        return AssignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        AssignedBy = assignedBy;
    }

    public String getAssignedTo() {
        return AssignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        AssignedTo = assignedTo;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public Date getEstimatedTime() {
        return EstimatedTime;
    }

    public void setEstimatedTime(Date estimatedTime) {
        EstimatedTime = estimatedTime;
    }

    public Date getCompletedTime() {
        return CompletedTime;
    }

    public void setCompletedTime(Date completedTime) {
        CompletedTime = completedTime;
    }

    public int getGapDate() {
        return GapDate;
    }

    public void setGapDate(int gapDate) {
        GapDate = gapDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
