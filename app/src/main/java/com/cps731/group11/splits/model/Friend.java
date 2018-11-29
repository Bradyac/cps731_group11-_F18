package com.cps731.group11.splits.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.sql.Blob;
import java.sql.SQLException;

public class Friend {

    private Blob picture;
    private String name;

    public Friend(Blob picture, String name){
        this.picture = picture;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Blob getPicture() {
        return picture;
    }

    public void setPicture(Blob picture) {
        this.picture = picture;
    }

    public Bitmap convertPicture() throws SQLException {
        byte[] byteArray = picture.getBytes(1, (int)picture.length());
        Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bm;
    }
}
