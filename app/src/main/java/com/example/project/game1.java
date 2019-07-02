package com.example.project;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Timer;

import pl.droidsonroids.gif.GifDrawable;

import static com.example.project.hello.yourAudioPath;
import static com.example.project.hello.yourFilePath;

//import com.felipecsl.gifimageview.library.GifImageView;

public class game1 extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener,View.OnClickListener  {
     ImageView img1,img2,img3,cry,hap,hand;
    GifDrawable gifFromResource1,gifFromResource2;
    TextView coun;
    static File file2 = null;
    ArrayList <photItem>arrayanswer;
    String answer=null;
    String audio=null;
    int counter1=0,counter2=0;
   // GifImageView gifView;
    private android.widget.RelativeLayout.LayoutParams layoutParams;
    String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
        img1=(ImageView)findViewById(R.id.img1);
        img2=(ImageView)findViewById(R.id.img2);
        img3=(ImageView)findViewById(R.id.img3);
        hand=(ImageView)findViewById(R.id.hand);
        cry=(ImageView)findViewById(R.id.cry);
        hap=(ImageView)findViewById(R.id.happy);
        coun=(TextView) findViewById(R.id.contener);
        ((GifDrawable)hap.getDrawable()).stop();
        ((GifDrawable)cry.getDrawable()).stop();
     /*   try {
           gifFromResource1  = new GifDrawable( getResources(), R.drawable.imotioncrry );
             gifFromResource2 = new GifDrawable( getResources(), R.drawable.inimationhap );
        } catch (IOException e) {
            e.printStackTrace();
        }*/
      //  hap.setBackgroundResource(R.drawable.inimationhap);
          file2 = new File(this.getFilesDir(), "storag_file_game.txt");
        file2.delete();
     wtite_file();
    //  edit_fileFinal();
        img1.setTag("img1");
        img1.setOnLongClickListener(this);
        img1.setOnClickListener(this);
        img2.setTag("img2");
        img2.setOnLongClickListener(this);
        img2.setOnClickListener(this);
        img3.setTag("img3");
        img3.setOnLongClickListener(this);
        img3.setOnClickListener(this);
        findViewById(R.id.lingam1).setOnDragListener((View.OnDragListener) this);
        findViewById(R.id.lingam2).setOnDragListener(this);
        findViewById(R.id.lingam3).setOnDragListener(this);
        // findViewById(R.id.space).setOnDragListener(this);
        findViewById(R.id.linans1).setOnDragListener(this);
        findViewById(R.id.linans2).setOnDragListener(this);
        findViewById(R.id.linans3).setOnDragListener(this);
        arrayanswer=fill_array_answer();
        img1.setImageBitmap(BitmapFactory.decodeFile(arrayanswer.get(0).phot_img));
        img2.setImageBitmap(BitmapFactory.decodeFile(arrayanswer.get(1).phot_img));
        img3.setImageBitmap(BitmapFactory.decodeFile(arrayanswer.get(2).phot_img));
        Play(audio);

        hand.setOnClickListener(this);


       // GifImageView gifView =(GifImageView) findViewById(R.id.imotiom);
       // gifView.setBytes(bitmapData);



      //  array_answer=fill_array_answer("givChair");

        //Set Drag Event Listeners for defined layouts





    }


    @Override
    public boolean onLongClick(View v) {
        v.setClickable(false);
        // Create a new ClipData.Item from the ImageView object's tag
        ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
        // Create a new ClipData using the tag as a label, the plain text MIME type, and
        // the already-created item. This will create a new ClipDescription object within the
        // ClipData, and set its MIME type entry to "text/plain"
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(v.getTag().toString(), mimeTypes, item);
        // Instantiates the drag shadow builder.
        View.DragShadowBuilder dragshadow = new View.DragShadowBuilder(v);
        // Starts the drag
        v.startDrag(data        // data to be dragged
                , dragshadow   // drag shadow builder
                , v           // local data about the drag and drop operation
                , 0          // flags (not currently used, set to 0)
        );
        return true;
    }
    LinearLayout own;
    boolean testwin=false;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onDrag(View v, DragEvent event) {
        // Defines a variable to store the action type for the incoming event
        int action = event.getAction();

        // Handles each of the expected events
        switch (action) {

            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    return true;
                }
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                // Applies a GRAY or any color tint to the View. Return true; the return value is ignored.
              //  v.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                // Invalidate the view to force a redraw in the new tint
                ((GifDrawable)hap.getDrawable()).stop();
                ((GifDrawable)cry.getDrawable()).stop();


                // Toast.makeText(this,own.toString(),Toast.LENGTH_LONG).show();
                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;

            case DragEvent.ACTION_DRAG_EXITED:
                // Re-sets the color tint to blue. Returns true; the return value is ignored.
                // view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                //It will clear a color filter .
                ArrayList<photItem> tem=arrayanswer;
                LinearLayout container1 = findViewById(R.id.lingam1);
                LinearLayout container2 = findViewById(R.id.lingam2);
                LinearLayout container3 = findViewById(R.id.lingam3);
                LinearLayout containerans1 = findViewById(R.id.linans1);
                LinearLayout containerans2 = findViewById(R.id.linans2);
                LinearLayout containerans3 = findViewById(R.id.linans3);
                v.getBackground().clearColorFilter();

                // Invalidate the view to force a redraw in the new tint
                v.invalidate();
                View vw1 = (View) event.getLocalState();
                ViewGroup owner1 = (ViewGroup) vw1.getParent();
                View templ=null;
                if(!owner1.equals(container1)&&container1.getChildCount()==0)
                {
                    if(containerans1.getChildCount()!=0) {
                        templ=containerans1.getChildAt(0);
                        containerans1.removeAllViews();
                    }
                    if(containerans2.getChildCount()!=0) {
                        templ=containerans2.getChildAt(0);
                        containerans2.removeAllViews();
                    }
                    if(containerans3.getChildCount()!=0) {
                        templ=containerans3.getChildAt(0);
                        containerans3.removeAllViews();
                    }
                    container1.addView(templ);
                    templ.setVisibility(View.VISIBLE);
                }
                if(!owner1.equals(container2)&&container2.getChildCount()==0)
                {
                    if(containerans1.getChildCount()!=0) {
                        templ=containerans1.getChildAt(0);
                        containerans1.removeAllViews();
                    }
                    if(containerans2.getChildCount()!=0) {
                        templ=containerans2.getChildAt(0);
                        containerans2.removeAllViews();
                    }
                    if(containerans3.getChildCount()!=0) {
                        templ=containerans3.getChildAt(0);
                        containerans3.removeAllViews();
                    }
                    container2.addView(templ);
                    templ.setVisibility(View.VISIBLE);
                }
                if(!owner1.equals(container3)&&container3.getChildCount()==0)
                {
                    if(containerans1.getChildCount()!=0) {
                        templ=containerans1.getChildAt(0);
                        containerans1.removeAllViews();
                    }
                    if(containerans2.getChildCount()!=0) {
                        templ=containerans2.getChildAt(0);
                        containerans2.removeAllViews();
                    }
                    if(containerans3.getChildCount()!=0) {
                        templ=containerans3.getChildAt(0);
                        containerans3.removeAllViews();
                    }
                    container3.addView(templ);
                    templ.setVisibility(View.VISIBLE);
                }

                return true;

            case DragEvent.ACTION_DROP:
                ClipData.Item item = event.getClipData().getItemAt(0);
                String dragData = item.getText().toString();
                Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_SHORT).show();
                v.getBackground().clearColorFilter();
                v.invalidate();

                View vw = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) vw.getParent();
                owner.removeView(vw); //remove the dragged view
                //caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                LinearLayout container = (LinearLayout) v;
             //   checLinans(container,vw);
                container.addView(vw);//Add the dragged view
                vw.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE
                if(container.equals(findViewById(R.id.linans1)))
                {
                    //Toast.makeText(this, "ok mmmm ", Toast.LENGTH_SHORT).show();
                   // answer="img1";
                    if(dragData.equals(answer)) {
                        Toast.makeText(this, "ok mmmm ", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "you win", Toast.LENGTH_SHORT).show();
                        counter1++;
                        coun.setText(String.valueOf(counter1));


                        MediaPlayer pp = Play(yourAudioPath + "ok.mp3");
                        ((GifDrawable) hap.getDrawable()).setLoopCount(1);
                        ((GifDrawable) hap.getDrawable()).reset();
                     // ((GifDrawable) hap.getDrawable()).reset();

                        int c = 0;
                           while(pp.isPlaying())
                           {
                               c++;
                           }
                            edit_fileFinal();
                            if (counter1 < 7) {

                                arrayanswer = fill_array_answer();
                                img1.setImageBitmap(BitmapFactory.decodeFile(arrayanswer.get(0).phot_img));
                                img2.setImageBitmap(BitmapFactory.decodeFile(arrayanswer.get(1).phot_img));
                                img3.setImageBitmap(BitmapFactory.decodeFile(arrayanswer.get(2).phot_img));
                                container.removeView(vw);
                                owner.addView(vw);
                               // ((GifDrawable) hap.getDrawable()).stop();

                               // Play(audio);
                               // ((GifDrawable) hap.getDrawable()).stop();

                            }

                    }
                    else
                    {

                        ((GifDrawable)cry.getDrawable()).reset();
                        Play(yourAudioPath +"cry.mp3");

                    }
                }
                else
                {
                    ((GifDrawable)cry.getDrawable()).reset();
                    Play(yourAudioPath +"cry.mp3");

                }
                // Returns true. DragEvent.getResult() will return true.
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                // Turns off any color tinting
                v.getBackground().clearColorFilter();
                // Invalidates the view to force a redraw
                v.invalidate();
                // Does a getResult(), and displays what happened.
                if (event.getResult())
                    Toast.makeText(this, "The drop was handled.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT).show();
                // returns true; the value is ignored.
                return true;
            // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;

    }

    @Override
    public void onClick(View v) {
        if (v.equals(hand)) {
            Play(audio);
        }
        if(!enter) {
            LinearLayout container = findViewById(R.id.linans1);
            LinearLayout container1 = findViewById(R.id.lingam1);
            LinearLayout container2 = findViewById(R.id.lingam2);
            LinearLayout container3 = findViewById(R.id.lingam3);
            if (v.equals(hand)) {
                Play(audio);
            } else {
                if (v.toString().contains(answer)) {
                    counter2++;
                    coun.setText(String.valueOf(counter2));
                    MediaPlayer pp = Play(yourAudioPath + "ok.mp3");
                    ((GifDrawable) hap.getDrawable()).setLoopCount(1);
                     ((GifDrawable) hap.getDrawable()).reset();
                     int cc=0;
                    ((GifDrawable) cry.getDrawable()).stop();
                    edit_fileFinal();
                    if (counter2 < 7&& finsh==false) {
                        arrayanswer=fill_array_answer();
                        int c=0;
                     while (pp.isPlaying()) {
                            c++;
                        }
                        img1.setImageBitmap(BitmapFactory.decodeFile(arrayanswer.get(0).phot_img));
                        img2.setImageBitmap(BitmapFactory.decodeFile(arrayanswer.get(1).phot_img));
                        img3.setImageBitmap(BitmapFactory.decodeFile(arrayanswer.get(2).phot_img));

                    }
                    if(finsh==true)
                    {
                        Intent intent=new Intent(getApplicationContext(), com.example.project.gamtest.class);
                        startActivity(intent);
                    }
                }

                else
                {
                    ((GifDrawable) hap.getDrawable()).stop();
                    ((GifDrawable) cry.getDrawable()).reset();
                    Play(yourAudioPath +"cry.mp3");
                }
                }
            }
        }



        public boolean checLinans(LinearLayout container,View vw)
        {
           // LinearLayout container = findViewById(R.id.linans1);
            LinearLayout container1 = findViewById(R.id.lingam1);
            LinearLayout container2 = findViewById(R.id.lingam2);
            LinearLayout container3 = findViewById(R.id.lingam3);
           // container = findViewById(R.id.linans1);
            if(container.getChildCount()!=0)
            {
                View child=container.getChildAt(0);
                if(!child.equals(vw)) {
                    Toast.makeText(this, "child" + child.toString(), Toast.LENGTH_LONG).show();
                    if (child.equals(img3)) ;
                    {
                        container.removeView(child);
                        container1.addView(child);
                        child.setVisibility(View.VISIBLE);
                    }
                    if (child.equals(img1)) {
                        container.removeView(child);
                        container3.addView(child);
                        child.setVisibility(View.VISIBLE);
                    }
                    if (child.equals(img2)) {
                        container.removeView(child);
                        container2.addView(child);
                        child.setVisibility(View.VISIBLE);
                        child.setVisibility(View.VISIBLE);
                    }
                }


              /* if(container1.getChildCount()==0&&!owner.equals(container1))
               {
                   container.removeView(img3);
                   container1.addView(img3);
                   container1.getChildAt(0).setVisibility(View.VISIBLE);
               } else if (container2.getChildCount() == 0&&!owner.equals(container2)) {
                   container.removeView(img2);
                   container2.addView(img2);
                   container1.getChildAt(0).setVisibility(View.VISIBLE);

               } else if (container3.getChildCount() == 0&&!owner.equals(container3)) {
                   container.removeView(img1);
                   container3.addView(img1);
                   container1.getChildAt(0).setVisibility(View.VISIBLE);
               }*/
              return true;
            }
            return false;
        }




    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("game.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
   // public ArrayList<photItem> fill_array_answer(String qestion,String ans)
    boolean enter=false;
    boolean finsh=false;
   public ArrayList<photItem> fill_array_answer()
    {
        ArrayList <photItem> temp=new ArrayList();
        try {
            JSONArray m_jArry = read_file("storag_file_game.txt");

            for (int i = 0; i < m_jArry.length(); i++) {

                JSONObject jo_inside = m_jArry.getJSONObject(i);
               // String js_qes = jo_inside.getString("qestion");
                String js_deter=jo_inside.getString("deterqwes");
               if (js_deter.equals("false")) {
                   if(i== m_jArry.length()-1)
                    {
                        finsh=true;
                    }
                    String js_qes = jo_inside.getString("qestion");
                    if(!js_qes.contains("where"))
                    {
                        Toast.makeText(this,"enter",Toast.LENGTH_LONG).show();
                        hand.setBackgroundResource(R.drawable.hand);
                        enter=true;
                        img1.setLongClickable(true);
                        img2.setLongClickable(true);
                        img3.setLongClickable(true);
                        img1.setClickable(false);
                        img2.setClickable(false);
                        img3.setClickable(false);
                     // img1.setOnClickListener(this);
                     //  img2.setOnClickListener(this);
                     //   img3.setOnClickListener(this);

                    }
                    if(js_qes.contains("where"))
                    {
                        hand.setBackgroundResource(R.drawable.where);
                        enter=false;
                        img1.setClickable(true);
                        img2.setClickable(true);
                        img3.setClickable(true);
                        img1.setLongClickable(false);
                        img2.setLongClickable(false);
                        img3.setLongClickable(false);
                    }

                    String ans=jo_inside.getString("answer");
                    if(i!=0) {
                        JSONObject jo_insideprev = m_jArry.getJSONObject(i - 1);

                        String ansprev = jo_insideprev.getString("answer");
                        if (!ans.equals(ansprev)) {
                            counter1 = 0;
                            counter2 = 0;
                        }
                    }
                  String  audio1=jo_inside.getString("audio");
                    audio= yourAudioPath + audio1;
                    JSONArray enter_array = jo_inside.getJSONArray("choice");

                    for (int k = 0; k < enter_array.length(); k++) {
                        JSONObject jo_inside2 = enter_array.getJSONObject(k);

                        String name_photo_json = jo_inside2.getString("name1");
                        String imag_phot_json = jo_inside2.getString("image1");
                        String imag_phot_json2 = yourFilePath + imag_phot_json;
                        String audio_phot_json = jo_inside.getString("audio");
                       // String audio_phot_json2 = yourAudioPath + audio_phot_json;

                        temp.add(new photItem(imag_phot_json2, name_photo_json, null, null));
                        if(temp.get(k).phot_name.equals(ans))
                        {
                            int h=k+1;
                            answer="img"+h;
                           // Toast.makeText(this,answer+"nivo",Toast.LENGTH_LONG).show();
                        }


                    }
                   // Toast.makeText(this, temp.get(0).toString(),Toast.LENGTH_LONG).show();
                    return  temp;
                }
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return  temp;

    }
    public JSONArray edit_file() {
        try {
            JSONArray m_jArry = read_file("storag_file_game.txt");
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String js_deter = jo_inside.getString("deterqwes");
                if (js_deter.equals("false")) {
                    jo_inside.put("deterqwes","true");
                  //  Toast.makeText(this,m_jArry.toString(),Toast.LENGTH_LONG).show();
                    break;
                }
            }
           return  m_jArry;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void edit_fileFinal() {
        try {
            JSONArray m_jArry=edit_file();
            String myJSONString = m_jArry.toString();
            file2.delete();
            file2 = new File(this.getFilesDir(), "storag_file_game.txt");
            FileOutputStream fos = null;
            fos = openFileOutput("storag_file_game.txt", Context.MODE_APPEND);
            fos.write(myJSONString.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void wtite_file() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("game.json");
            String myJSONString = m_jArry.toString();
            file2 = new File(this.getFilesDir(), "storag_file_game.txt");
            FileOutputStream fos = openFileOutput("storag_file_game.txt", Context.MODE_APPEND);
            fos.write(myJSONString.getBytes());
            fos.close();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public JSONArray read_file(String name) {
        JSONArray ddd = null;
        try {
            FileInputStream fis = this.openFileInput(name);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);

            }

            ddd = new JSONArray(sb.toString());
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ddd;
    }

    public MediaPlayer Play(String pathName) {

        MediaPlayer player = new MediaPlayer();


        try {
          /*    if(pathName.equals("ok.mp3"))
             {
                  ((GifDrawable) hap.getDrawable()).reset();
            }*/

            player.setDataSource(pathName);
            player.prepare();


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        player.start();
        return player;
    }
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private long startHTime = 0L;
    private Handler customHandler = new Handler();
    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startHTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
          //  if (timerTextView != null)
             //   timerTextView.setText("" + String.format("%02d", mins) + ":"
                //        + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }

    };

}
