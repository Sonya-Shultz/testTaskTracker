package com.example.noteapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class OneTaskView extends LinearLayout {

    private TextView titleTextView;
    private TextView mainTextView;
    private TextView endDataTextView;
    private Button editButton;
    private Button deleteButton;
    public OneTaskView(Context context) {
        super(context);
        init(null, 0);
    }

    public OneTaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public OneTaskView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        titleTextView = findViewById(R.id.textTitle);
        mainTextView = findViewById(R.id.textDataTask);
        endDataTextView = findViewById(R.id.textEndData);
        deleteButton = findViewById(R.id.buttonDelete);
        editButton = findViewById(R.id.buttonEdit);

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.OneTaskView, defStyle, 0);

    }

}

