package com.damasUniv.acApp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.damasUniv.acApp.AppConfig.URL_LOGIN;

public class hello extends AppCompatActivity {

    Animation anim;
    TextView textView;
    static String yourFilePath, yourAudioPath;

    private ProgressDialog pDialog;
/*
    private DialogInterface.OnClickListener onDismiss = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {

                startActivity(new Intent(getApplicationContext(), swap_key.class));
                finish();
            } else {

                Intent intent = new Intent(getApplicationContext(), com.damasUniv.acApp.LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }
    };*/
    String getlevelFromServer() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {

                               // String uid = obj.getString("user_id");
                                String level = obj.getString("user_level");
                                //  Toast.makeText(getApplicationContext(), obj.getString("user_id"), Toast.LENGTH_SHORT).show();

                                SharedPrefManager.getInstance(getApplicationContext()).setLevel(level);
/*
                                Toast.makeText(getApplicationContext(),
                                        uid + " "+level , Toast.LENGTH_LONG).show();

*/
                                //startActivity(new Intent(getApplicationContext(), swap_key.class));
                                //finish();
                               /* if(SharedPrefManager.getInstance(getApplicationContext()).getUser_level() == 1){
                                    startActivity(new Intent(getApplicationContext(), level1.class));
                                }else{
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }*/

                            } else {
                                Toast.makeText(getApplicationContext(), "UserName Or Password Error!", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(getApplicationContext(), game1.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", SharedPrefManager.getInstance(getApplicationContext()).getUser_id());
                params.put("op", "get_level");

               // params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        return  "1";
    }


    public void unzip() {
        File n = new File(yourAudioPath);
        n.mkdir();

        File n2 = new File(yourFilePath);
        n2.mkdir();
        try {
            InputStream fin = this.getAssets().open("files.zip");
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Log.v("Decompress", "Unzipping " + ze.getName());

                if (ze.isDirectory()) {
                    dirChecker(ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(this.getFilesDir() + File.separator + ze.getName());
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }

                    zin.closeEntry();
                    fout.close();
                }

            }
            zin.close();
        } catch (Exception e) {
            Log.e("Decompress", "unzip", e);
        }

    }

    private void dirChecker(String dir) {
        File f = new File(dir);

        if (!f.isDirectory()) {
            f.mkdirs();
        }
    }

    public boolean isConnectedToServer(String url, int timeout) {
        try{
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
            return true;
        } catch (Exception e) {
            // Handle your exceptions
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        yourFilePath = this.getFilesDir() + "/" + "images" + "/";
        yourAudioPath = this.getFilesDir() + "/" + "audio" + "/";


        pDialog.setMessage("Loading Data ...");


        textView = (TextView) findViewById(R.id.textView);
        anim = AnimationUtils.loadAnimation(this, R.anim.down);

        textView.setAnimation(anim);


        final Thread thread = new Thread() {
            public void run() {
                try {


                    sleep(1200);
                    if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                        if(isConnectedToServer(URL_LOGIN, 1200)) {
                            getlevelFromServer();
                        }
                        sleep(1200);
                        startActivity(new Intent(getApplicationContext(), swap_key.class));
                        finish();
                    } else {

                        Intent intent = new Intent(getApplicationContext(), com.damasUniv.acApp.LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };


        Thread sss = new Thread(new Runnable() {

            public void run() {
                unzip();
                runOnUiThread(new Runnable() {

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


        } else {
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
