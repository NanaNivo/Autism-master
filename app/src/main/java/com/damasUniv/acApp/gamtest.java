package com.damasUniv.acApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

//import at.lukle.clickableareasimage.OnClickableAreaClickedListener;

//import at.lukle.clickableareasimage.OnClickableAreaClickedListener;

public class gamtest extends AppCompatActivity  {
     ImageView currentimag;
    @Override
    public  void onBackPressed(){
        Intent back = new Intent(getApplicationContext(), swap_key.class);
        startActivity(back);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamtest);
      //  currentimag=(ImageView)findViewById(R.id.currImage);
       // currentimag.setBackgroundResource(R.drawable.chaircurr1);

    }



}
