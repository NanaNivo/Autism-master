package com.damasUniv.acApp;

import android.annotation.TargetApi;
import android.content.Context;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import static com.damasUniv.acApp.MainActivity.find_intent;
import static com.damasUniv.acApp.hello.yourAudioPath;
import static com.damasUniv.acApp.hello.yourFilePath;
import static com.damasUniv.acApp.swap_key.chose;


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
    String temp_parent1=null;
    File mPhotoFile;
   // FileCompressor mCompressor;
    // static  String path,text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addphotos);
        Intent rever_act1 = getIntent();
        temp_parent1= rever_act1.getExtras().getString("perent");
        // Toast.makeText(addphotos.this,temp_parent1,Toast.LENGTH_LONG).show();
        addimage = (ImageView) findViewById(R.id.addimage);
        cam = (Button) findViewById(R.id.camera);
        gallery = (Button) findViewById(R.id.galary);
        stor_imag = (Button) findViewById(R.id.store);
        record_voic = (Button) findViewById(R.id.voice);
        text_phot=(EditText) findViewById(R.id.nam_add_pbot);
        timerTextView=(TextView)findViewById(R.id.time1);
        gallery.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                int namsize=text_phot.getText().length();
                if(namsize<5)
                {
                    Toast.makeText(addphotos.this,"please First Enter the name First",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, 1);
                }

            }
        });
        cam.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                int namsize=text_phot.getText().length();
                if (namsize < 5) {
                    Toast.makeText(addphotos.this, "please First Enter the name First", Toast.LENGTH_LONG).show();
                } else {
                 Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(openCamera, 2);

                }
            }
        });


        record_voic.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View arg0) {
                int namsize=text_phot.getText().length();
                if (namsize < 5) {
                    Toast.makeText(addphotos.this, "please First Enter the name ", Toast.LENGTH_LONG).show();
                } else {
                    if (temp == 0) {
                        mediaRecorder = new MediaRecorder();

                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

                        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

                        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);


                        mediaRecorder.setOutputFile(saveToInternalStorageVoice());
                        voice_loaded = saveToInternalStorageVoice();


                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                            timeSwapBuff = 0l;
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
                    } else {
                        timeSwapBuff += timeInMilliseconds;
                        customHandler.removeCallbacks(updateTimerThread);
                        mediaRecorder.stop();

                        temp = 0;
                    }


                }
            }
        });
        stor_imag.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // char cc = text_phot.getText().charAt(0);
                int namsize = text_phot.getText().length();
                String s = null;
                if (namsize ==0 && mediaRecorder == null && image_loaded == null) {
                    Toast.makeText(addphotos.this, "plese enter tha information ", Toast.LENGTH_LONG).show();
                } else {
                    if (namsize < 5) {
                        text_phot.setText(" ");
                        //addimage.setImageBitmap(BitmapFactory.decodeFile(null));
                        // timerTextView.setText(null);
                        Toast.makeText(addphotos.this, "plese enter tha name correctly ", Toast.LENGTH_LONG).show();
                    }
                    if (mediaRecorder == null) {
                        Toast.makeText(addphotos.this, "plese enter the voice ", Toast.LENGTH_LONG).show();
                    }
                    if (image_loaded == null) {
                        Toast.makeText(addphotos.this, "plese enter the image ", Toast.LENGTH_LONG).show();
                    }

                    if (namsize >= 5 && mediaRecorder != null && image_loaded != null) {
                        if (chose==2)
                        {
                            Intent intt = new Intent(getApplicationContext(), MainActivity.class);
                        intt.putExtra("path", pp);
                        intt.putExtra("voice", vv);
                        intt.putExtra("perent", temp_parent1);
                        //Toast.makeText(addphotos.this,text_phot.getText().toString(),Toast.LENGTH_LONG).show();
                        intt.putExtra("text", text_phot.getText().toString());
                        find_intent = 1;
                        startActivity(intt);
                    }
                        else
                        {
                            Intent intt = new Intent(getApplicationContext(), level1.class);
                            intt.putExtra("path", pp);
                            intt.putExtra("voice", vv);
                            intt.putExtra("perent", temp_parent1);
                            //Toast.makeText(addphotos.this,text_phot.getText().toString(),Toast.LENGTH_LONG).show();
                            intt.putExtra("text", text_phot.getText().toString());
                            find_intent = 1;
                            startActivity(intt);
                        }
                    }

                }
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



           /* Uri photo_uri = data.getData();
            try {
                InputStream imagestream = getContentResolver().openInputStream(photo_uri);
                Bitmap selected_photo = BitmapFactory.decodeStream(imagestream);
                addimage.setImageBitmap(selected_photo);
                Uri tempUri = getImageUri(getApplicationContext(), selected_photo);
                image_loaded =saveToInternalStorage(BitmapFactory.decodeFile(getRealPathFromURI(tempUri)));

            }catch (FileNotFoundException FNFE) {
                Toast.makeText(addphotos.this, FNFE.getMessage(), Toast.LENGTH_LONG).show();
            }*/
        }
        if(requestCode == 2 && resultCode == RESULT_OK && null != data)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            addimage.setImageBitmap(photo);
            Uri tempUri = getImageUri(getApplicationContext(), photo);

           image_loaded =saveToInternalStorage(BitmapFactory.decodeFile(getRealPathFromURI(tempUri)));

            }

        }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        // Create imageDir
        File mypath=new File(yourFilePath,pp);

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
   public String vv=String.valueOf(Math.random());
    public String pp=String.valueOf(Math.random());
    private String saveToInternalStorageVoice() {

        File mypath = new File(yourAudioPath, vv);

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
