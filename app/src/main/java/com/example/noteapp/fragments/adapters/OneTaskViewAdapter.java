package com.example.noteapp.fragments.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.noteapp.MainActivity;
import com.example.noteapp.R;
import com.example.noteapp.dataproc.DataBaseConnection;
import com.example.noteapp.dataproc.TaskData;
import com.example.noteapp.fragments.TaskEditFragment;
import com.example.noteapp.util.MyUtil;

import java.util.ArrayList;

public class OneTaskViewAdapter extends ArrayAdapter<TaskData> {

    final private ArrayList<TaskData> tasksData;
    public OneTaskViewAdapter(Context context, ArrayList<TaskData> arrayList) {
        super(context, R.layout.sample_one_task_view, arrayList);
        tasksData = arrayList;
    }

    public void addNewElement(TaskData data){
        tasksData.add(0, data);
        notifyDataSetChanged();
    }

    public void changeData(int position, TaskData data){
        TaskData tData = tasksData.get(position);
        if (data.title != null) tData.title=data.title;
        if (data.endData != null) tData.endData=data.endData;
        if (data.text != null) tData.text=data.text;
        tasksData.set(position, tData);
        notifyDataSetChanged();
    }

    public boolean isIdInSet(TaskData mData){
        for(int i=0; i<tasksData.size();i++){
            if (tasksData.get(i).isSameID(mData)) return true;
        }
        return false;
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

        ColorStateList colorStateList = ColorStateList.valueOf(
                Color.parseColor(MyUtil.getColorByDate(taskData.endData, 10)));
        view.setBackgroundTintList(colorStateList);

        titleTextView.setText(taskData.title);
        mainTextView.setText(taskData.text);
        endDataTextView.setText(MyUtil.dateToString(taskData.endData));

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
                                DataBaseConnection.getInstance(getContext()).deleteExistingTask(taskData);
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
                createNewFragmentEditTask(v, taskData);

            }
        });

        return view;
    }

    // source code from the MediaRouter in the official support library
    private Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    private void createNewFragmentEditTask(View view, TaskData taskData){

        TaskEditFragment newTaskEditFragment = TaskEditFragment.newInstance(taskData, getPosition(taskData));

        MainActivity ma = (MainActivity) getActivity();
        ma.getSupportFragmentManager().beginTransaction()
                .add(R.id.placeholder_for_task_edit_fragment, newTaskEditFragment)
                .commit();

        MyUtil.changeVisibility(view, View.VISIBLE, View.INVISIBLE);
    }
}


