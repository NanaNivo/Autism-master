package com.damasUniv.acApp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class hello extends AppCompatActivity {

    Animation anim;
    TextView textView;
    static String yourFilePath, yourAudioPath;

    private ProgressDialog pDialog;

    public void unzip() {
        File n = new File(yourAudioPath);
        n.mkdir();

        File n2 = new File(yourFilePath);
        n2.mkdir();
        try  {
            InputStream fin = this.getAssets().open("files.zip");
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Log.v("Decompress", "Unzipping " + ze.getName());

                if(ze.isDirectory()) {
                    dirChecker(ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(this.getFilesDir()+File.separator+ze.getName());
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }

                    zin.closeEntry();
                    fout.close();
                }

            }
            zin.close();
        } catch(Exception e) {
            Log.e("Decompress", "unzip", e);
        }

    }

    private void dirChecker(String dir) {
        File f = new File(dir);

        if(!f.isDirectory()) {
            f.mkdirs();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

      yourFilePath = this.getFilesDir() + "/"+"images"+"/";
       yourAudioPath=this.getFilesDir() + "/"+"audio"+"/";



        pDialog.setMessage("Loading Data ...");




        textView=(TextView) findViewById(R.id.textView);
        anim= AnimationUtils.loadAnimation(this,R.anim.down);

        textView.setAnimation(anim);




        final Thread thread=new Thread(){
            public void run(){
                try {

                    sleep(1200);

                    if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {

                        startActivity(new Intent(getApplicationContext(), swap_key.class));
                        finish();
                    }else {

                        Intent intent=new Intent(getApplicationContext(), com.damasUniv.acApp.LoginActivity.class);
                        startActivity(intent);
                        finish();
                 }



                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };

        Thread sss=new Thread(new Runnable(){

            public void run(){
                unzip();
                runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        hideDialog();
                        thread.start();

                    }

                });
            }

        });




        if (!SharedPrefManager.getInstance(getApplicationContext()).isFirstOpen()) {
            showDialog();
            sss.start();
            SharedPrefManager.getInstance(getApplicationContext()).open();


        }else{
            thread.start();
        }
















    }




    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
