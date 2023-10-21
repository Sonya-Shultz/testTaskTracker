package com.example.noteapp;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaskEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskEditFragment newInstance(String param1, String param2) {
        TaskEditFragment fragment = new TaskEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_edit, container, false);
        Button closeBttn = view.findViewById(R.id.close_edit_task_button);
        closeBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //clear
                                view.setVisibility(View.INVISIBLE);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //save
                                //clear
                                view.setVisibility(View.INVISIBLE);
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Close without saving?").setPositiveButton("Close", dialogClickListener)
                        .setNegativeButton("Save", dialogClickListener).show();
            }

        });
        return view;
    }
}