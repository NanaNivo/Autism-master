package com.damasUniv.acApp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


import static com.damasUniv.acApp.MainActivity.file;
import static com.damasUniv.acApp.hello.yourAudioPath;
import static com.damasUniv.acApp.hello.yourFilePath;

public class level1 extends AppCompatActivity {
    LinearLayout con_imag;
    Button rev, favourite;
    SquareImageView show_image;
    ArrayList<photItem> all_list=new ArrayList<>();
    ArrayList<photItem>  fav_list=null;
    GridView alllist;
    String path_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);
        // con_imag=(LinearLayout)findViewById(R.id.imag_in_lin);
        rev = (Button) findViewById(R.id.revert1);
        favourite = (Button) findViewById(R.id.fav);
       show_image = (SquareImageView) findViewById(R.id.image_level1);
       all_list = fill_all_list();
        alllist = (GridView) findViewById(R.id.gridlist1);

       alllist.setAdapter(new item_adapter(all_list, level1.this));
        alllist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                show_image.setImageBitmap(BitmapFactory.decodeFile(all_list.get(position).phot_img));
                make_fav(all_list.get(position).phot_name);
                Play(all_list.get(position).audio);
                                           }
                                       }
        );
        favourite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

           //   fav_list= fill_fav_list();
                all_list= fill_fav_list();
                alllist.setAdapter(new item_adapter(fav_list, level1.this));
            }
        });
        rev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if (show_image.equals(null))
                    rev.setClickable(false);
                else {
                    all_list=new ArrayList<photItem>();
                    all_list = fill_all_list();
                    alllist.setAdapter(new item_adapter(all_list, level1.this));
                }
                rev.setClickable(true);
            }
        });

    }
    public ArrayList fill_all_list() {
        FileInputStream fis = null;
        try {
            fis = this.openFileInput("storag_file4.txt");
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

                String js_level = jo_inside.getString("level1");

                if (js_level.equals("true")) {
                    String js_nam = jo_inside.getString("name");
                    String js_per = jo_inside.getString("parent");
                    String imag_phot_json = jo_inside.getString("Image");
                    String imag_phot_json2 = yourFilePath + imag_phot_json;
                    String audio_phot_json = jo_inside.getString("ِAudio");
                    String audio_phot_json2 = yourAudioPath + audio_phot_json;
                    all_list.add(new photItem(imag_phot_json2, js_nam, js_per, audio_phot_json2));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return all_list;
    }


    public  ArrayList fill_fav_list() {
        fav_list=new ArrayList<>();
        FileInputStream fis = null;
        try {
            fis = this.openFileInput("storag_file4.txt");
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

                int js_fav = jo_inside.getInt("fav");

                if (js_fav>=3) {
                    String js_nam = jo_inside.getString("name");
                    String js_per = jo_inside.getString("parent");
                    String imag_phot_json = jo_inside.getString("Image");
                    String imag_phot_json2 = yourFilePath + imag_phot_json;
                    String audio_phot_json = jo_inside.getString("ِAudio");
                    String audio_phot_json2 = yourAudioPath + audio_phot_json;
                    photItem temp=new photItem(imag_phot_json2, js_nam, js_per, audio_phot_json2);
                    fav_list.add(temp);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fav_list;
    }

    public void make_fav(String name)
    {
        Toast.makeText(level1.this,name,Toast.LENGTH_LONG).show();
        FileInputStream fis = null;
        try {
            fis = this.openFileInput("storag_file4.txt");
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

                    int js_fav= jo_inside.getInt("fav");
                    js_fav= js_fav+1;
                    jo_inside.put("fav",js_fav);
                    String myJSONString = m_jArry.toString();
                    file= new File(this.getFilesDir(), "storag_file4.txt");
                    file.delete();
                    file= new File(this.getFilesDir(), "storag_file4.txt");
                    FileOutputStream fos = openFileOutput("storag_file4.txt", Context.MODE_APPEND);
                    fos.write(myJSONString.getBytes());
                    fos.close();
                    break;

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void Play(String pathName)  {

        final MediaPlayer player = new MediaPlayer();



        try {
            player.setDataSource(pathName);
            player.prepare();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        player.start();
        //   Toast.makeText(MainActivity.this,pathName,Toast.LENGTH_LONG).show();



    }
}
