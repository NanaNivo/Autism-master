package com.damasUniv.acApp;

import java.util.ArrayList;

public class categItem {
    public int categ_id;
    public int categ_Image;
    public String categ_nam;
    public ArrayList<photItem> list_catg;
// nermeen

    public categItem(int categ_id, int categ_Image, String categ_nam, ArrayList<photItem> list_catg) {
        this.categ_id = categ_id;
        this.categ_Image = categ_Image;
        this.categ_nam = categ_nam;
        this.list_catg = list_catg;
    }


}
