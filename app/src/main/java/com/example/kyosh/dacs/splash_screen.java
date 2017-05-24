package com.example.kyosh.dacs;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class splash_screen extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar= (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.getProgressDrawable();
        Thread myThreat=new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3000);
                    Intent intentmain=new Intent(splash_screen.this,MainActivity.class);
                    startActivity(intentmain);
                    finish();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        };
        myThreat.start();
    }

}
