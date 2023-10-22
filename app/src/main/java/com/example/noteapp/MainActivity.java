package com.example.noteapp;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.noteapp.databinding.ActivityMainBinding;
import com.example.noteapp.dataproc.TaskData;
import com.example.noteapp.fragments.ListOfTasksFragment;
import com.example.noteapp.fragments.TaskEditFragment;

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

        ft.commit();

        setContentView(binding.getRoot());
        findViewById(R.id.placeholder_for_task_edit_fragment).setVisibility(View.INVISIBLE);

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskData td = new TaskData("","",new Date());

                TaskEditFragment newTaskEditFragment = TaskEditFragment
                        .newInstance(td, 0);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.placeholder_for_task_edit_fragment, newTaskEditFragment)
                        .commit();

                findViewById(R.id.placeholder_for_task_edit_fragment).setVisibility(View.VISIBLE);
                v.setVisibility(View.INVISIBLE);

            }
        });

    }

}