package com.cps731.group11.splits.model;

import java.sql.Blob;

public class Friend {

    private Blob picture;
    private String name;

    public Friend(Blob picture, String name){
        this.picture = picture;
        this.name = name;
    }

}
