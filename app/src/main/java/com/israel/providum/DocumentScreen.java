package com.israel.providum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.israel.providum.database.DatabaseHelper;
import com.israel.providum.sessionmanager.SessionManager;

import java.util.HashMap;


public class DocumentScreen extends AppCompatActivity {

    TextView textView;
    String s;
    SessionManager sessionManager;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_screen);

        //INSTANCE OF SHARED PREFERENCE / SESSION MANAGER
        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDocument();
        s = user.get(SessionManager.DOCUMENT);

        textView = findViewById(R.id.documentscanner);
        s = getIntent().getStringExtra("SCANNED RESULT");
        textView.setText(s);

        String resultvalue = String.valueOf(s);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.insertResult(resultvalue);

        sessionManager.createSession(s);


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.app_bar_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.hstory) {
//            Intent intent = new Intent(DocumentScreen.this, History.class);
////            intent.putExtra("SCANNED RESULT", s);
//            startActivity(intent);
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//
//    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DocumentScreen.this, Dashboard.class));
        finish();
    }
}