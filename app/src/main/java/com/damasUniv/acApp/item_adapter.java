package com.damasUniv.acApp;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class item_adapter extends BaseAdapter  {
    ArrayList<photItem> photlist = new ArrayList<>();
      Activity cc;
    public item_adapter(ArrayList<photItem> photlist,Activity cc) {
        this.cc=cc;
        this.photlist = photlist;
    }

    @Override
    public int getCount() {
        return photlist.size();
    }

    @Override
    public String getItem(int position) {
        return photlist.get(position).phot_name.toString();
    }

    public  String getItem_imag(int position) {
        return photlist.get(position).phot_img;
    }

    @Override
    public long getItemId(int position) {
        // return photlist.get(position).phot_img;
        return 0;
    }

    //  private final LayoutInflater mInflater;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       LayoutInflater r =cc.getLayoutInflater();
        // LayoutInflater r  = (LayoutInflater) LayoutInflater.from(MainActivity.this);
        final View view = (View) r.inflate(R.layout.item_list,null);


        final TextView name_phot = (TextView) view.findViewById(R.id.name);
        final ImageView img_phot = (ImageView) view.findViewById(R.id.imag);

          name_phot.setText(photlist.get(position).phot_name);
        // img_phot.setImageResource(photlist.get(position).phot_img);
       img_phot.setImageBitmap(BitmapFactory.decodeFile(photlist.get(position).phot_img));


         return view;


    }


}