package com.israel.providum;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.israel.providum.adapter.HistoryAdapter;
import com.israel.providum.database.DatabaseHelper;
import com.israel.providum.model.Historydb;
import com.israel.providum.sessionmanager.SessionManager;
import com.israel.providum.utils.MyDividerItemDecoration;
import com.israel.providum.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class History extends AppCompatActivity {

    private HistoryAdapter mAdapter;
    private List<Historydb> resultList = new ArrayList<>();

    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private TextView noResultsView, result;
    ImageView imageView;
    SessionManager sessionManager;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().setTitle("History");

        //INSTANCE OF SHARED PREFERENCE / SESSION MANAGER
        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDocument();
        s = user.get(SessionManager.DOCUMENT);

        recyclerView = findViewById(R.id.listview_history);
        noResultsView = findViewById(R.id.empty_result_view);
        result = (TextView) findViewById(R.id.result);
        imageView = findViewById(R.id.check_box);


        db = new DatabaseHelper(this);

        resultList.addAll(db.getAllResults());


        mAdapter = new HistoryAdapter(this, resultList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyResults();
        Swipe();

        String s = getIntent().getStringExtra("SCANNED RESULT");


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Historydb historydb = resultList.get(position);
                if (position >= 0) {
                    Intent intent = new Intent(History.this, HistoryDetails.class);
                    intent.putExtra("SCANNED RESULT", String.valueOf(historydb.getResult()));
                    view.getContext().startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(History.this, "Not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onLongClick(View view, int position) {
            }

        }));

    }

    public void Swipe() {
        ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteResult(viewHolder.getAdapterPosition());
                Toast.makeText(History.this, "History Deleted", Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemtouchHelper = new ItemTouchHelper(itemTouchHelper);
        itemtouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * Inserting new note in db
     * and refreshing the list
     */

    public void createResult(String result) {
        // inserting note in db and getting
        // newly inserted note id
        long id = db.insertResult(result);

        // get the newly inserted result from db
        Historydb n = db.getResult(id);

        if (n != null) {
            // adding new note to array list at 0 position
            resultList.add(0, n);

            // refreshing the list
            mAdapter.notifyDataSetChanged();

            toggleEmptyResults();
        }
    }


    /**
     * Updating note in db and updating
     * item in the list by its position
     */
    private void updateResult(String result, int position) {
        Historydb n = resultList.get(position);
        // updating note text
        n.setResult(result);

        // updating note in db
        db.updateNote(n);

        // refreshing the list
        resultList.set(position, n);
        mAdapter.notifyItemChanged(position);

        toggleEmptyResults();
    }

    /**
     * Deleting note from SQLite and removing the
     * item from the list by its position
     */
    private void deleteResult(int position) {
        // deleting the note from db
        db.deleteNote(resultList.get(position));

        // removing the note from the list
        resultList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyResults();
    }


    private void toggleEmptyResults() {
        // you can check resultList.size() > 0

        if (db.getNotesCount() > 0) {
            noResultsView.setVisibility(View.GONE);
        } else {
            noResultsView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_action_mode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_info) {
            Toast.makeText(this, "Swipe left or right to delete history", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(History.this, Dashboard.class);
        intent.putExtra("SCANNED RESULT", s);
        startActivity(intent);
        finish();
    }

}