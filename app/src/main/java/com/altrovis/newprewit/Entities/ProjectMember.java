package com.altrovis.newprewit.Entities;

/**
 * Created by ricki on 4/5/2016.
 */
public class ProjectMember {
    private int ID;
    private int ProjectID;
    private String Username;

    public ProjectMember() {
    }

    public ProjectMember(int ID, int projectID, String username) {
        this.ID = ID;
        ProjectID = projectID;
        Username = username;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getProjectID() {
        return ProjectID;
    }

    public void setProjectID(int projectID) {
        ProjectID = projectID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    @Override
    public String toString(){
        return Username;
    }
}
