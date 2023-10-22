package com.example.noteapp;

import static java.lang.Math.max;
import static java.lang.Math.min;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    static String getColorByDate(Date date, int maxDateDiff){
        maxDateDiff = (maxDateDiff > 0) ? maxDateDiff : 1;
        Date currentDate = new Date();
        long difference = date.getTime() - currentDate.getTime();
        int days = (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);

        int [] colorGreen = {198, 228, 198};
        int [] colorRed= {216, 135, 135};

        int [] myColor = {0,0,0};

        if (days >= maxDateDiff)
            return String.format("#%02x%02x%02x", colorGreen[0], colorGreen[1], colorGreen[2]);;

        for (int i = 0; i < 3; i++){
            myColor[i] = max(0, min(255, colorGreen[i] * days/maxDateDiff + colorRed[i] * (maxDateDiff - days)/maxDateDiff));
        }

        return String.format("#%02x%02x%02x", myColor[0], myColor[1], myColor[2]);
    }
}
