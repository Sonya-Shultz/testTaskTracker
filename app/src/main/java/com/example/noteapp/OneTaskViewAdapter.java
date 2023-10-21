package com.example.noteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class OneTaskViewAdapter extends ArrayAdapter<TaskData> {

    final private ArrayList<TaskData> tasksData;
    public OneTaskViewAdapter(Context context, ArrayList<TaskData> arrayList) {
        super(context, R.layout.sample_one_task_view, arrayList);
        tasksData = arrayList;
    }

    public void addNewElement(TaskData data){
        tasksData.add(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        TaskData taskData = getItem(position);
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.sample_one_task_view, parent, false);
        }

        TextView titleTextView = view.findViewById(R.id.textTitle);
        TextView mainTextView = view.findViewById(R.id.textDataTask);
        TextView endDataTextView = view.findViewById(R.id.textEndData);
        Button deleteButton = view.findViewById(R.id.buttonDelete);
        Button editButton = view.findViewById(R.id.buttonEdit);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasksData.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
