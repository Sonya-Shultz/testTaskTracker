package com.example.noteapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.noteapp.fragments.adapters.OneTaskViewAdapter;
import com.example.noteapp.R;
import com.example.noteapp.dataproc.DataBaseConnection;
import com.example.noteapp.dataproc.TaskData;

import java.util.ArrayList;

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

        ArrayList<TaskData> tasksList = DataBaseConnection.getInstance(getContext()).getAllTasks();

        OneTaskViewAdapter adapter = new OneTaskViewAdapter(view.getContext(), tasksList);

        ListView listOfTasks = (ListView) view.findViewById(R.id.list_of_tasks);

        listOfTasks.setAdapter(adapter);
        return view;
    }


}