package com.damasUniv.acApp;

import java.util.ArrayList;

public class photItem {
    // public int phot_id;
    public String phot_img;
    public String phot_name;
    public String phot_parent;
   public String audio;
    public ArrayList<photItem>phot_list_list;

    public String getPhot_img() {
        return phot_img;
    }
    //hello

    public photItem( String phot_img, String phot_name,String phot_parent,String audio) {
        //this.phot_id = phot_id;
        this.phot_img = phot_img;
        this.phot_name = phot_name;
        this.phot_parent=phot_parent;
        this.audio=audio;
    }
}
