package com.israel.providum;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Dashboard extends AppCompatActivity {
    boolean doubleBackPressedOnce = false;
    private CardView cardview1, cardview2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        cardview1 = findViewById(R.id.QRScanner);
        cardview2 = findViewById(R.id.history);

        cardview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,MainActivity.class));
                finish();
            }
        });

        cardview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,History.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackPressedOnce = false;
            }
        }, 2000);
    }
}
