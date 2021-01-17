package com.israel.providum;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        boolean handler = new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(Splashscreen.this, Dashboard.class);
                startActivity(i);

            }
        }, 5000);

    }
}