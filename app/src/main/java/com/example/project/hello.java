package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class hello extends AppCompatActivity {
    TextView text;
    Animation anim;
    Animation anim1,anim2;
    ImageView imageView,imageView1;
    TextView textView;
    static String yourFilePath, yourAudioPath;
    //static File file = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

      yourFilePath = this.getFilesDir() + "/"+"images"+"/";
       yourAudioPath=this.getFilesDir() + "/"+"audio"+"/";

        Thread thread=new Thread(){
            public void run(){
                try {
                    sleep(2000);
                    Intent intent=new Intent(getApplicationContext(), com.example.project.MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        thread.start();



        textView=(TextView) findViewById(R.id.textView);
        //imageView=(ImageView) findViewById(R.id.imageView);
        //imageView1=(ImageView) findViewById(R.id.imageView1);
        anim= AnimationUtils.loadAnimation(this,R.anim.fade);
      // anim1= AnimationUtils.loadAnimation(this,R.anim.tranc);
       // anim2= AnimationUtils.loadAnimation(this,R.anim.down);
        //imageView.setAnimation(anim1);
       // imageView1.setAnimation(anim2);
        textView.setAnimation(anim);
        //textView.setAnimation(anim1);
    }
}
