package com.altrovis.newprewit.Entities;

import java.util.Date;

/**
 * Created by Wisnu on 15/03/2016.
 */
public class Project {

    private int ID;
    private String nama;
    private Date created;

    public Project() {
    }

    public Project(int ID, String nama, Date created) {
        this.ID = ID;
        this.nama = nama;
        this.created = created;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString(){
        return nama;
    }

}
