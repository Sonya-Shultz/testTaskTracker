package com.example.noteapp;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;

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
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                tasksData.remove(position);
                                notifyDataSetChanged();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getRootView()
                        .findViewById(R.id.placeholder_for_task_edit_fragment)
                        .setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}
