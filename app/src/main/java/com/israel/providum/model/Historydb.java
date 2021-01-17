package com.israel.providum.model;


public class Historydb {
    public static final String TABLE_NAME = "history";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RESULT = "result";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String result;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_RESULT + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Historydb() {
    }

    public Historydb(int id, String result, String timestamp) {
        this.id = id;
        this.result = result;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
