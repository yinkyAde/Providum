package com.israel.providum.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.israel.providum.model.Historydb;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "history_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Historydb.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Historydb.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertResult(String result) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Historydb.COLUMN_RESULT, result);

        // insert row
        long id = db.insert(Historydb.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Historydb getResult(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Historydb.TABLE_NAME,
                new String[]{Historydb.COLUMN_ID, Historydb.COLUMN_RESULT, Historydb.COLUMN_TIMESTAMP},
                Historydb.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Historydb note = new Historydb(
                cursor.getInt(cursor.getColumnIndex(Historydb.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Historydb.COLUMN_RESULT)),
                cursor.getString(cursor.getColumnIndex(Historydb.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return note;
    }

    public List<Historydb> getAllResults() {
        List<Historydb> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Historydb.TABLE_NAME + " ORDER BY " +
                Historydb.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Historydb note = new Historydb();
                note.setId(cursor.getInt(cursor.getColumnIndex(Historydb.COLUMN_ID)));
                note.setResult(cursor.getString(cursor.getColumnIndex(Historydb.COLUMN_RESULT)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Historydb.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Historydb.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateNote(Historydb result) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Historydb.COLUMN_RESULT, result.getResult());

        // updating row
        return db.update(Historydb.TABLE_NAME, values, Historydb.COLUMN_ID + " = ?",
                new String[]{String.valueOf(result.getId())});
    }

    public void deleteNote(Historydb note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Historydb.TABLE_NAME, Historydb.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
}
