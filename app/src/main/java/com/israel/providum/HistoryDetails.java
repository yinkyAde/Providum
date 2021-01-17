package com.israel.providum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.israel.providum.sessionmanager.SessionManager;

import java.util.HashMap;

public class HistoryDetails extends AppCompatActivity {
    TextView textView;
    String s;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDocument();
        s = user.get(SessionManager.DOCUMENT);

        textView = findViewById(R.id.documentscanner);

        String s = getIntent().getStringExtra("SCANNED RESULT");
        textView.setText(s);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HistoryDetails.this, History.class);
        intent.putExtra("SCANNED RESULT", s);
        startActivity(intent);
        finish();
    }
}
