package com.example.noteapp;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListOfTasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListOfTasksFragment extends Fragment {

    public ListOfTasksFragment() {
        // Required empty public constructor
    }

    public static ListOfTasksFragment newInstance() {
        ListOfTasksFragment fragment = new ListOfTasksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_tasks, container, false);

        ArrayList<TaskData> lngList = new ArrayList<>();
        TaskData tmp = new TaskData("","",new Date());
        lngList.add(tmp);
        lngList.add(tmp);
        lngList.add(tmp);

        OneTaskViewAdapter adapter = new OneTaskViewAdapter(view.getContext(), lngList);

        ListView listOfTasks = (ListView) view.findViewById(R.id.list_of_tasks);

        listOfTasks.setAdapter(adapter);
        return view;
    }


}