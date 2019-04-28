package com.example.project;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static com.example.project.MainActivity.find_intent;
import static com.example.project.hello.yourAudioPath;
import static com.example.project.hello.yourFilePath;


public class addphotos extends AppCompatActivity {
    ImageView addimage;
    Button cam, gallery,stor_imag,record_voic;
    EditText text_phot;
    TextView timerTextView;
    String image_loaded,voice_loaded;
    MediaRecorder mediaRecorder=null;
    int temp=0;


    private long startHTime = 0L;
    private Handler customHandler = new Handler();
    // static  String path,text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addphotos);
        addimage = (ImageView) findViewById(R.id.addimage);
        cam = (Button) findViewById(R.id.camera);
        gallery = (Button) findViewById(R.id.galary);
       stor_imag = (Button) findViewById(R.id.store);
        record_voic = (Button) findViewById(R.id.voice);
        text_phot=(EditText) findViewById(R.id.nam_add_pbot);
        timerTextView=(TextView)findViewById(R.id.time1);
        gallery.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 1);

            }
        });
        cam.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {


            }
        });

        record_voic.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View arg0) {
           /*   Intent ii = new Intent(
                      String.valueOf(MediaStore.Audio.Media.RECORD_SOUND_ACTION));


             startActivityForResult(ii, 2);*/



                    if(temp==0) {
                        mediaRecorder = new MediaRecorder();
                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

                        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

                        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);


                        mediaRecorder.setOutputFile(saveToInternalStorageVoice());
                        voice_loaded = saveToInternalStorageVoice();


                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                            startHTime = SystemClock.uptimeMillis();
                            customHandler.postDelayed(updateTimerThread, 0);
                            temp = 1;
                            record_voic.setClickable(true);
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        timeSwapBuff += timeInMilliseconds;
                        customHandler.removeCallbacks(updateTimerThread);
                        mediaRecorder.stop();

                        temp=0;
                    }


            }
        });
   /*    record_voic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(temp==1) {
                    mediaRecorder.stop();
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    temp=0;
                }
                return false;
            }


        });*/


        stor_imag.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent intt = new Intent(getApplicationContext(), MainActivity.class);
                intt.putExtra("path", text_phot.getText().toString());
                intt.putExtra("voice", text_phot.getText().toString());
                //Toast.makeText(addphotos.this,text_phot.getText().toString(),Toast.LENGTH_LONG).show();
                intt.putExtra("text",text_phot.getText().toString());
                find_intent=1;
                startActivity(intt);

            }
        });

    }


    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if(resultCode == RESULT_OK)
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
          String picturePath = cursor.getString(columnIndex);
            cursor.close();
        //  Toast.makeText(addphotos.this, picturePath,Toast.LENGTH_LONG).show();

            addimage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            image_loaded =saveToInternalStorage(BitmapFactory.decodeFile(picturePath));
        }
        if(requestCode == 2 && resultCode == RESULT_OK && null != data)
        {

         /* Uri savedUri = data.getData();
         savedUri.getPath();
            String[] filePathColumn = {MediaStore.Audio.Media.DATA};

            Cursor cursor = getContentResolver().query(savedUri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
           String voicePath = cursor.getString(columnIndex);
          cursor.close();*/





            //   voice_loaded= saveToInternalStorageVoice(voicePath);




          //    Toast.makeText(addphotos.this, mediaRecorder.toString(),Toast.LENGTH_LONG).show();
         //  voice_loaded =saveToInternalStorage(BitmapFactory.decodeFile(voicePath));

        // voice_loaded= voice_loaded+".mp3";

            Toast.makeText(addphotos.this, voice_loaded,Toast.LENGTH_LONG).show();
        }
    }
    private String saveToInternalStorage(Bitmap bitmapImage){
        // Create imageDir
        File mypath=new File(yourFilePath,text_phot.getText().toString());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //                fos.close();
        }
        return mypath.getAbsolutePath();
    }
    private String saveToInternalStorageVoice() {

        File mypath = new File(yourAudioPath, text_phot.getText().toString());

       FileOutputStream foss ;
        try {

             foss = new FileOutputStream(mypath);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return mypath.getAbsolutePath();
    }


    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startHTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            if (timerTextView != null)
                timerTextView.setText("" + String.format("%02d", mins) + ":"
                        + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }

    };
}
