package com.damasUniv.acApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
//import android.widget.ListView;
import android.widget.LinearLayout;

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

import static com.damasUniv.acApp.hello.yourAudioPath;
import static com.damasUniv.acApp.hello.yourFilePath;
import static com.damasUniv.acApp.swap_key.chose;
public class MainActivity extends Activity {


    // ListView categ;
    GridView gridview_categ, option_view;

    ArrayList<photItem> grid_categ;
    ArrayList<photItem> grid_fell;
    item_adapter itmad;
    ArrayList<photItem> op_temp;

    int t = 0;
    int count_col;
    int width_scrol = 0;
    int width_phot = 0, high_phot;
    HorizontalScrollView hos_scro;
    LinearLayout abov_scrol;
    static File file = null;

    static int find_intent = 0;
  // static String temp_parent = null;
    private long then;
    private int longClickDur = 5000;

    @Override
    public  void onBackPressed(){
        Intent back = new Intent(getApplicationContext(), swap_key.class);
        startActivity(back);

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        chose=2;
        //final String[] list_of_categ_name = getResources().getStringArray(R.array.catger_list);
       // final String[] list_of_fell_name = getResources().getStringArray(R.array.list_felling);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        file = new File(this.getFilesDir(), "storag_file4.txt");
        // file.delete();
       //wtite_file();

        // write_file_image();
        // read_file("storag_image.txt");

        //Toast.makeText(MainActivity.this, yourFilePath, Toast.LENGTH_LONG).show();
        // file.delete();

        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //   write_file(addphotos.picturePath,addphotos.text_phot.getText().toString(),"root");
        //  }
        //add_to_file();
        // read_file();
        if (find_intent == 1) {
            Intent rever_act = getIntent();
            String imag_path = rever_act.getExtras().getString("path");
            String text_name = rever_act.getExtras().getString("text");
            String text_voice = rever_act.getExtras().getString("voice");
            String text_par = rever_act.getExtras().getString("perent");
            // Toast.makeText(MainActivity.this,text_par,Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                write_json_to_file(imag_path, text_name, text_par, text_voice);
            }
        }
        gridview_categ = (GridView) findViewById(R.id.gridlist);
        final Button revert = (Button) findViewById(R.id.revert);
        final Button speaker_void = (Button) findViewById(R.id.talk);

        count_col = 0;
        hos_scro = (HorizontalScrollView) findViewById(R.id.scrol_item);
        abov_scrol = (LinearLayout) findViewById(R.id.lin_up_scrol);


        //Toast.makeText(MainActivity.this, (), Toast.LENGTH_SHORT).show();

        op_temp = new ArrayList<>();
        option_view = (GridView) findViewById(R.id.optionlist);


        ViewTreeObserver viewTreeObserver = abov_scrol.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                abov_scrol.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width = abov_scrol.getMeasuredWidth();
                high_phot = abov_scrol.getMeasuredHeight();
                width_phot = width / 3;

            }
        });


        // write_json();
        ArrayList aa = readjson_son("root");
        gridview_categ.setAdapter(new item_adapter(aa, this));
        gridview_categ.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    count_col++;
                    fill_op_item(gridview_categ.getAdapter().getItem(position).toString());
                    if (!op_temp.isEmpty()) {
                        photItem temp = op_temp.get(op_temp.size() - 1);
                        // Toast.makeText(MainActivity.this,temp.audio+".mp3",Toast.LENGTH_LONG).show();
                        Play(temp.audio);
                    }
                    option_view.setNumColumns(count_col);


                    //for edit the size of op_temp
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(count_col * width_phot, ViewGroup.LayoutParams.MATCH_PARENT);
                    option_view.setLayoutParams(lp);
                    option_view.setAdapter(new item_adapter(op_temp, MainActivity.this));

                    ArrayList<photItem> son = readjson_son(gridview_categ.getAdapter().getItem(position).toString());
                    ArrayList<photItem> broth = readjson_broth(gridview_categ.getAdapter().getItem(position).toString());
                    if (son.isEmpty()) {
                        gridview_categ.setAdapter(new item_adapter(broth, MainActivity.this));
                    } else {
                        gridview_categ.setAdapter(new item_adapter(son, MainActivity.this));
                    }

                }

        });
        gridview_categ.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (gridview_categ.getAdapter().getItem(position).toString().equals("اضافة1")||gridview_categ.getAdapter().getItem(position).toString().equals("اضافة2")||gridview_categ.getAdapter().getItem(position).toString().equals("اضافة3")||gridview_categ.getAdapter().getItem(position).toString().equals("اضافة4")||gridview_categ.getAdapter().getItem(position).toString().equals("اضافة5")||gridview_categ.getAdapter().getItem(position).toString().equals("اضافة6")||gridview_categ.getAdapter().getItem(position).toString().equals("اضافة7")) {


                    gridview_categ.setEnabled(true);
                    ArrayList<photItem> broth = readjson_broth(gridview_categ.getAdapter().getItem(position).toString());
                    String temp_parent=broth.get(0).phot_parent;
                   // Toast.makeText(MainActivity.this, temp_parent,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), addphotos.class);
                   intent.putExtra("perent", temp_parent);
                    startActivity(intent);


                    return true;
                } else {
                    String delet_item = gridview_categ.getAdapter().getItem(position).toString();
                    ArrayList<photItem> temp = readjson_broth(delet_item);
                    JSONObject jes = new JSONObject();
                    try {
                        JSONArray m_jArry = read_file("storag_file4.txt");

                        for (int i = 0; i < m_jArry.length(); i++) {
                            JSONObject jo_inside = m_jArry.getJSONObject(i);
                            String jjj = jo_inside.getString("name");
                            if (jjj.equals(delet_item)) {
                                m_jArry.remove(i);
                                temp.remove(position);
                                break;
                            }

                        }
                        String myJSONString = m_jArry.toString();
                        file.delete();
                        file = new File(MainActivity.this.getFilesDir(), "storag_file4.txt");
                        FileOutputStream fos = openFileOutput("storag_file4.txt", Context.MODE_APPEND);
                        fos.write(myJSONString.getBytes());
                        fos.close();
                        gridview_categ.setAdapter(new item_adapter(temp, MainActivity.this));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return false;
            }

        });


        revert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if (op_temp.isEmpty())
                    revert.setClickable(false);
                else
                    reverton_bot(option_view.getAdapter().getItem(op_temp.size() - 1).toString());
                revert.setClickable(true);

            }
        });
        speaker_void.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (op_temp.isEmpty())
                    speaker_void.setClickable(false);
                else
                    end_speaker();
                speaker_void.setClickable(true);
            }
        });
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("phh.json");
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

    ImageView Imageopt1;

    public ArrayList readjson_son(String name) {
        ArrayList a = new ArrayList();
        Resources resources = this.getResources();

        try {

            JSONArray m_jArry = read_file("storag_file4.txt");
            //Log.v("Decompress", m_jArry.length());

            for (int i = 0; i < m_jArry.length(); i++) {

                JSONObject jo_inside = m_jArry.getJSONObject(i);

                String js_per = jo_inside.getString("parent");
                if (js_per.equals(name)) {
                    String name_photo_json = jo_inside.getString("name");
                    String imag_phot_json = jo_inside.getString("Image");
                    String imag_phot_json2 = yourFilePath + imag_phot_json;
                    //Toast.makeText(MainActivity.this,imag_phot_json2,Toast.LENGTH_LONG).show();
                    String audio_phot_json = jo_inside.getString("ِAudio");
                    String audio_phot_json2 = yourAudioPath + audio_phot_json;
                    // final int resourceId = resources.getIdentifier(imag_phot_json, "drawable", this.getPackageName());

                    a.add(new photItem(imag_phot_json2, name_photo_json, js_per, audio_phot_json));

                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return a;

    }

    public void wtite_file() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("phh.json");
            String myJSONString = m_jArry.toString();
            file = new File(this.getFilesDir(), "storag_file4.txt");
            FileOutputStream fos = openFileOutput("storag_file4.txt", Context.MODE_APPEND);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void write_json_to_file(String image, String text, String parent, String voc) {
        JSONObject jes = new JSONObject();
        try {
            JSONArray m_jArry = read_file("storag_file4.txt");
            //  Toast.makeText(MainActivity.this,m_jArry.toString(),Toast.LENGTH_LONG).show();
            jes.put("id", m_jArry.length() + 2);
            jes.put("name", text);
            jes.put("Image", image);
            jes.put("parent", parent);
            jes.put("ِAudio", voc);
            m_jArry.put(jes);
            String myJSONString = m_jArry.toString();
            file.delete();
            file = new File(this.getFilesDir(), "storag_file4.txt");
            FileOutputStream fos = openFileOutput("storag_file4.txt", Context.MODE_APPEND);
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
            JSONObject jes = ddd.getJSONObject(ddd.length() - 1);

            String name1 = jes.getString("name");
            //return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Log.v("Decompress", ddd.getString(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ddd;
    }

    public void fill_op_item(String name) {
        try {

            if (!name.equals("اضافة1")&&!name.equals("اضافة2")&&!name.equals("اضافة3")&&!name.equals("اضافة4")&&!name.equals("اضافة5")&&!name.equals("اضافة6")&&!name.equals("اضافة7")) {
                FileInputStream fis = this.openFileInput("storag_file4.txt");
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);

                }
                JSONArray m_jArry = new JSONArray(sb.toString());

                for (int i = 0; i < m_jArry.length(); i++) {

                    JSONObject jo_inside = m_jArry.getJSONObject(i);

                    String js_nam = jo_inside.getString("name");

                    if (js_nam.equals(name)) {

                        String js_per = jo_inside.getString("parent");
                        String imag_phot_json = jo_inside.getString("Image");
                        String imag_phot_json2 = yourFilePath + imag_phot_json;
                        String audio_phot_json = jo_inside.getString("ِAudio");
                        String audio_phot_json2 = yourAudioPath + audio_phot_json;

                        // final int resourceId = resources.getIdentifier(imag_phot_json, "drawable", this.getPackageName());

                        op_temp.add(new photItem(imag_phot_json2, js_nam, js_per, audio_phot_json2));

                    }


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList readjson_broth(String name) {
        ArrayList b = new ArrayList();
        String paren_photo_json = null;
        Resources resources = this.getResources();
        try {
            JSONArray m_jArry = read_file("storag_file4.txt");

            for (int i = 0; i < m_jArry.length(); i++) {

                JSONObject jo_inside = m_jArry.getJSONObject(i);

                String js_nam = jo_inside.getString("name");
                if (js_nam.equals(name)) {
                    paren_photo_json = jo_inside.getString("parent");
                }
            }
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String js_car = jo_inside.getString("parent");
                if (js_car.equals(paren_photo_json)) {
                    String name_photo_json = jo_inside.getString("name");
                    String imag_phot_json = jo_inside.getString("Image");
                    String imag_phot_json2 = yourFilePath + imag_phot_json;
                    String audio_phot_json = jo_inside.getString("ِAudio");
                    String audio_phot_json2 = yourAudioPath + audio_phot_json;
                    //  final int resourceId = resources.getIdentifier(imag_phot_json, "drawable", this.getPackageName());

                    b.add(new photItem(imag_phot_json2, name_photo_json, js_car, audio_phot_json2));


                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return b;

    }

    public void Play(String pathName) {

        MediaPlayer player = new MediaPlayer();


        try {

            player.setDataSource(pathName);
            player.prepare();


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        player.start();
        //player.pause();

        //   Toast.makeText(MainActivity.this,pathName,Toast.LENGTH_LONG).show();
    }


    public void reverton_bot(String name) {

        // Toast.makeText(MainActivity.this,name, Toast.LENGTH_SHORT).show();


        ArrayList temp = readjson_broth(name);
        op_temp.remove(op_temp.size() - 1);
        gridview_categ.setAdapter(new item_adapter(temp, MainActivity.this));
        option_view.setAdapter(new item_adapter(op_temp, MainActivity.this));


    }

    MediaPlayer mPlayer;

    void function_play_file(String files) {
        try {

            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(files);
            mPlayer.setOnCompletionListener(completeListener);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    MediaPlayer.OnCompletionListener completeListener;
    int con = 1;
    public void CompletionListener() {
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                if(con<op_temp.size()) {
                    function_play_file(op_temp.get(con).audio);
                    con++;
                    CompletionListener();
                }
            }
        });
    }

    public void end_speaker() {
        function_play_file(op_temp.get(0).audio);
        CompletionListener();
        //while (con < op_temp.size()) {
        con=1;

    }


}










