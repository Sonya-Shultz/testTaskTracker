package com.example.noteapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskEditFragment extends Fragment {

    private static final String ARG_TITLE = "Title";
    private static final String ARG_TEXT = "Text";
    private static final String ARG_DATE = "Date";
    private static final String ARG_ID = "Id";
    private static final String ARG_POSITION = "Position";

    private String title, text, date;
    private int id, position;

    public TaskEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title task's title.
     * @param text task's main text.
     * @param date task's end date.
     * @param id task's id in database.
     * @param position task's position on screen.
     * @return A new instance of fragment TaskEditFragment.
     */
    public static TaskEditFragment newInstance(String title, String text, String date,
                                               int id, int position) {
        TaskEditFragment fragment = new TaskEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_TEXT, text);
        args.putString(ARG_DATE, date);
        args.putInt(ARG_ID, id);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public static TaskEditFragment newInstance(TaskData taskData, int position) {
        TaskEditFragment fragment = new TaskEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, taskData.title);
        args.putString(ARG_TEXT, taskData.text);
        args.putString(ARG_DATE, fragment.dateToString(taskData.endData));
        args.putInt(ARG_ID, taskData.id);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            text = getArguments().getString(ARG_TEXT);
            date = getArguments().getString(ARG_DATE);
            id = getArguments().getInt(ARG_ID);
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_edit, container, false);

        Button closeBtn = view.findViewById(R.id.close_edit_task_button);
        Button saveBtn = view.findViewById(R.id.save_task_data_button);

        EditText titleView = view.findViewById(R.id.title_edit_view);
        EditText textView = view.findViewById(R.id.text_edit_view);

        TextView dateView = view.findViewById(R.id.end_data_view);
        Button datePickerBtn = view.findViewById(R.id.button_select_date);

        titleView.setText(title);
        textView.setText(text);
        dateView.setText(date);

        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Date dateTmp = stringToDate(dayOfMonth+"."+(month+1)+"."+year);

                        dateView.setText(dateToString(dateTmp));
                        setDate(dateTmp);

                    }
                }, date.getYear()+1900, date.getMonth(), date.getDate());
                dialog.show();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfTitleFull(view)) {
                    showToast(date);
                    TaskData taskData = new TaskData(title, text, stringToDate(date));
                    taskData.id = id;
                    saveData(view, taskData);
                    changeVisibility(view, View.INVISIBLE, View.VISIBLE);
                    closeSelf(view);
                } else showToast("Empty TITLE!");
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                changeVisibility(view, View.INVISIBLE, View.VISIBLE);
                                closeSelf(view);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                if (checkIfTitleFull(view)) {
                                    TaskData taskData = new TaskData(title, text, stringToDate(date));
                                    taskData.id = id;
                                    saveData(view, taskData);
                                    changeVisibility(view, View.INVISIBLE, View.VISIBLE);
                                    closeSelf(view);
                                }
                                else {
                                    showToast("Empty TITLE!");
                                }
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Close without saving?").setPositiveButton("Close", dialogClickListener)
                        .setNegativeButton("Save", dialogClickListener).show();
            }

        });

        addTextChangeListener(titleView, 0);
        addTextChangeListener(textView, 1);

        return view;

    }

    private void saveData(View view, TaskData taskData){
        try{
            ListView lv = (ListView) view.getRootView().findViewById(R.id.list_of_tasks);
            OneTaskViewAdapter ad = (OneTaskViewAdapter) lv.getAdapter();
            if(!ad.isIdInSet(taskData)){
                ad.addNewElement(taskData);
                //save new
            }else {
                // edit old
                ad.changeData(position, taskData);
            }
            showToast("Data Saved");

        } catch (Exception e){
            showToast("Error while saving!");
        }
    }

    void changeVisibility(View view, int visibilityTasks, int visibilityButton){
        View rootView = view.getRootView();
        View taskEditView = rootView.findViewById(R.id.placeholder_for_task_edit_fragment);
        View buttonAdd = rootView.findViewById(R.id.button_add);
        taskEditView.setVisibility(visibilityTasks);
        buttonAdd.setVisibility(visibilityButton);
    }

    private void closeSelf(View view){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    // 0 - title
    // 1 - text
    private void addTextChangeListener(EditText editText, int type){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switch (type){
                    case 0: title = s.toString(); break;
                    case 1: text = s.toString(); break;
                    default: break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private boolean checkIfTitleFull(View view){
        EditText title = view.findViewById(R.id.title_edit_view);
        return title.getText().length() > 0;
    }

    private void showToast(String text){
        Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void setDate(Date date){
        this.date = dateToString(date);
    }

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
}