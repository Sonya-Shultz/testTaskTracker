package com.example.noteapp;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtil {
    static String dateToString(Date date){
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(date);
    }

    static Date stringToDate(String dateStr){
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        try {
            date = formatter.parse(dateStr);
        }
        catch (ParseException e) {

        }
        return date;
    }

    static void changeVisibility(View view, int visibilityTasks, int visibilityButton){
        View rootView = view.getRootView();
        View taskEditView = rootView.findViewById(R.id.placeholder_for_task_edit_fragment);
        View buttonAdd = rootView.findViewById(R.id.button_add);
        taskEditView.setVisibility(visibilityTasks);
        buttonAdd.setVisibility(visibilityButton);
    }

    static boolean checkIfTitleFull(View view){
        EditText title = view.findViewById(R.id.title_edit_view);
        return title.getText().length() > 0;
    }

    static void showToast(String text, Context context){
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
