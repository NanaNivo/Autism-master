package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class swap_key extends AppCompatActivity {

    Button play;
    Button pecs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swap_key);

        play = findViewById(R.id.play);
        pecs = findViewById(R.id.pecs);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), com.example.project.game1.class);
                startActivity(intent);
                finish();
            }
        });


        pecs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SharedPrefManager.getInstance(getApplicationContext()).getUser_level() == 1){

                    startActivity(new Intent(getApplicationContext(), level1.class));
                }else{

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                finish();
            }
        });


    }
}
