package com.example.noteapp;

import java.util.Date;

public class TaskData {
    String title, text;
    Date endData;

    public TaskData(String title, String text, Date endData) {
        this.title = title;
        this.text = text;
        this.endData = endData;
    }
}
