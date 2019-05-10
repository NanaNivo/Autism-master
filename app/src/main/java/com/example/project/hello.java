package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class hello extends AppCompatActivity {
    Animation anim;
    TextView textView;
    static String yourFilePath, yourAudioPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);


      yourFilePath = this.getFilesDir() + "/"+"images"+"/";
       yourAudioPath=this.getFilesDir() + "/"+"audio"+"/";

        Thread thread=new Thread(){
            public void run(){
                try {
                    sleep(1200);

                    if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                        finish();
                        if(SharedPrefManager.getInstance(getApplicationContext()).getUser_level() == 1){
                            startActivity(new Intent(getApplicationContext(), level1.class));
                        }else{
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }

                    }else {
                        Intent intent=new Intent(getApplicationContext(), com.example.project.LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }



                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        thread.start();

        textView=(TextView) findViewById(R.id.textView);
        anim= AnimationUtils.loadAnimation(this,R.anim.down);

        textView.setAnimation(anim);
    }
}
