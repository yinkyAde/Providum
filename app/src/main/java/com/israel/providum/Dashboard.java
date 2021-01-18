package com.israel.providum;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.israel.providum.auth.LoginScreen;
import com.israel.providum.sessionmanager.SessionManagerToken;

import java.util.HashMap;

public class Dashboard extends AppCompatActivity {
    boolean doubleBackPressedOnce = false;
    private CardView cardview1, cardview2,cardview3;
    SessionManagerToken sessionManager;
    String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

//        //INSTANCE OF SHARED PREFERENCE / SESSION MANAGER
//        sessionManager = new SessionManagerToken(this);
//        if (sessionManager.isLoggin()) {
//            HashMap<String, String> user = sessionManager.getUserDocument();
//            token = user.get(SessionManagerToken.TOKEN);
//
//        } else {
//            Intent i = new Intent(Dashboard.this, LoginScreen.class);
//            startActivity(i);
//            finish();
//        }

        cardview1 = findViewById(R.id.QRScanner);
        cardview2 = findViewById(R.id.history);
        cardview3 = findViewById(R.id.logout);

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

        cardview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
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
