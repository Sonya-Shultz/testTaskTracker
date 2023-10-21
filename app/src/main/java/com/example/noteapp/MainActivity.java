package com.example.noteapp;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.noteapp.databinding.ActivityMainBinding;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ListOfTasksFragment newListFragment = new ListOfTasksFragment();
        ft.replace(R.id.placeholder_fragment, newListFragment);

        TaskEditFragment newTaskEditFragment = new TaskEditFragment();

        ft.replace(R.id.placeholder_for_task_edit_fragment, newTaskEditFragment);

        ft.commit();

        setContentView(binding.getRoot());
        //newTaskEditFragment.getId();
        findViewById(R.id.placeholder_for_task_edit_fragment).setVisibility(View.INVISIBLE);

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView lv = (ListView) findViewById(R.id.list_of_tasks);
                ((OneTaskViewAdapter) lv.getAdapter()).addNewElement(new TaskData("","",new Date()));
            }
        });

    }

}