package com.damasUniv.acApp;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
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
import java.io.InputStreamReader;
import java.util.ArrayList;


import static com.damasUniv.acApp.MainActivity.file;
import static com.damasUniv.acApp.MainActivity.find_intent;
import static com.damasUniv.acApp.hello.yourAudioPath;
import static com.damasUniv.acApp.hello.yourFilePath;
import static com.damasUniv.acApp.swap_key.chose;

public class level1 extends AppCompatActivity {
    LinearLayout con_imag;
    Button rev, favourite;
    SquareImageView show_image;
    ArrayList<photItem> all_list=null;
    ArrayList<photItem>  fav_list=null;
    GridView alllist;
    String path_image;
    @Override
    public  void onBackPressed(){
        Intent back = new Intent(getApplicationContext(), swap_key.class);
        startActivity(back);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);
        // con_imag=(LinearLayout)findViewById(R.id.imag_in_lin);
        chose=1;
        file = new File(this.getFilesDir(), "storag_file4.txt");
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
        alllist = (GridView) findViewById(R.id.gridlist1);
        rev = (Button) findViewById(R.id.revert1);
        favourite = (Button) findViewById(R.id.fav);
       show_image = (SquareImageView) findViewById(R.id.image_level1);
       all_list = fill_all_list();


       alllist.setAdapter(new item_adapter(all_list, level1.this));
        alllist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!alllist.getAdapter().getItem(position).toString().equals("اضافة1")) {
                    show_image.setImageBitmap(BitmapFactory.decodeFile(all_list.get(position).phot_img));
                    make_fav(all_list.get(position).phot_name);
                    Play(all_list.get(position).audio);
                }
                                           }
                                       }
        );

        alllist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (alllist.getAdapter().getItem(position).toString().equals("اضافة1")) {


                    alllist.setEnabled(true);
                    // Toast.makeText(MainActivity.this, temp_parent,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), addphotos.class);
                    intent.putExtra("perent", "null");
                    startActivity(intent);


                    return true;
                } else {
                    String delet_item = alllist.getAdapter().getItem(position).toString();
                   // ArrayList<photItem> temp = readjson_broth(delet_item);
                    JSONObject jes = new JSONObject();
                    try {
                        JSONArray m_jArry = read_file("storag_file4.txt");

                        for (int i = 0; i < m_jArry.length(); i++) {
                            JSONObject jo_inside = m_jArry.getJSONObject(i);
                            String jjj = jo_inside.getString("name");
                            if (jjj.equals(delet_item)) {
                                m_jArry.remove(i);
                              //  temp.remove(position);
                                break;
                            }

                        }
                        String myJSONString = m_jArry.toString();
                        file.delete();
                        file = new File(level1.this.getFilesDir(), "storag_file4.txt");
                        FileOutputStream fos = openFileOutput("storag_file4.txt", Context.MODE_APPEND);
                        fos.write(myJSONString.getBytes());
                        fos.close();
                      //  gridview_categ.setAdapter(new item_adapter(temp, MainActivity.this));
                       all_list=null;
                        all_list = fill_all_list();
                        alllist.setAdapter(new item_adapter(all_list, level1.this));
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
        ArrayList all_list = new ArrayList();
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
            jes.put("level1", "true");
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
        //Toast.makeText(level1.this,name,Toast.LENGTH_LONG).show();
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
