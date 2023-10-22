package com.example.noteapp.dataproc;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class TaskData {
    public String title, text;

    public int id;
    public Date endData;

    private final int max=500, min=0;

    public TaskData(String title, String text, Date endData) {
        this.id = new Random().nextInt((max - min) + 1) + min;
        this.title = title;
        this.text = text;
        this.endData = endData;
    }

    public boolean isSameID(TaskData data){
        return (Objects.equals(this.id, data.id));
    }
}
