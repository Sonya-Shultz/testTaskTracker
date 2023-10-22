package com.example.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class DataBaseConnection extends SQLiteOpenHelper {

    private static DataBaseConnection mInstance;
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "TaskTrackerApp.db";
    public static final String TITLE_COL = "titleText";
    public static final String TEXT_COL = "mainText";
    public static final String DATE_COL = "endDate";
    public static final String ID_COL = "id";
    public static final String MY_ID_COL = "id_custom";
    public static final String TABLE_NAME = "tasksDataTable";

    public static synchronized DataBaseConnection getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DataBaseConnection(context.getApplicationContext());
        }
        return mInstance;
    }
    private DataBaseConnection (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void addNewTask(TaskData taskData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TITLE_COL, taskData.title);
        values.put(TEXT_COL, taskData.text);
        values.put(MY_ID_COL, taskData.id);
        values.put(DATE_COL, MyUtil.dateToString(taskData.endData));

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void editExistingTask(TaskData taskData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TITLE_COL, taskData.title);
        values.put(TEXT_COL, taskData.text);
        values.put(DATE_COL, MyUtil.dateToString(taskData.endData));

        db.update(TABLE_NAME, values, MY_ID_COL+"=?", new String[]{""+taskData.id});
        db.close();
    }

    public ArrayList<TaskData> getAllTasks(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor cur = db.rawQuery(query, null);

        ArrayList<TaskData> tasksData = new ArrayList<>();

        if(cur.getCount() != 0){
            cur.moveToFirst();

            do{
                String title = cur.getString(1);
                String text = cur.getString(2);
                Date date = MyUtil.stringToDate(cur.getString (3));
                String id = cur.getString(4);

                TaskData taskData = new TaskData(title, text, date);
                taskData.id = Integer.parseInt(id);

                tasksData.add(taskData);

            }while (cur.moveToNext());
        }
        Collections.reverse(tasksData);
        return tasksData;
    }

    public void deleteExistingTask(TaskData taskData){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME
                + " WHERE " + MY_ID_COL + " = " + taskData.id + " ;";
        db.execSQL(query);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE_COL + " TEXT,"
                + TEXT_COL + " TEXT,"
                + DATE_COL + " TEXT,"
                + MY_ID_COL + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
