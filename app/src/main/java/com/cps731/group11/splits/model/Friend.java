package com.cps731.group11.splits.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.sql.Blob;
import java.sql.SQLException;

public class Friend {

    //private Blob picture;
    private String name;
    private int id;

    public Friend(String name, int id){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(){ return this.id; }

    /*public Blob getPicture() {
        return picture;
    }

    public void setPicture(Blob picture) {
        this.picture = picture;
    }

    public Bitmap convertPicture() throws SQLException {
        byte[] byteArray = picture.getBytes(1, (int)picture.length());
        Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bm;
    }*/
}
