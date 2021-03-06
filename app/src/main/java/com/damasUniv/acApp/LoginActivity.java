package com.damasUniv.acApp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {


    private Button btnLogin;

    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();

                startActivity(new Intent(this, swap_key.class));

        }


        inputEmail = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    userLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });


    }


    private void userLogin(final String email, final String passwordq) {
        //first getting the values
        final String username = email;
        final String password = passwordq;

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        hideDialog();
                        try {

                             Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {

                                String uid = obj.getString("user_id");
                                String level = obj.getString("user_level");
                              //  Toast.makeText(getApplicationContext(), obj.getString("user_id"), Toast.LENGTH_SHORT).show();

                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(uid,level);
/*
                                Toast.makeText(getApplicationContext(),
                                        uid + " "+level , Toast.LENGTH_LONG).show();

*/
                                startActivity(new Intent(getApplicationContext(), swap_key.class));
                                finish();
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
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
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
