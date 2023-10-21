package com.example.noteapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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
        ft.commit();

        setContentView(binding.getRoot());

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView lv = (ListView) findViewById(R.id.list_of_tasks);
                ((OneTaskViewAdapter) lv.getAdapter()).addNewElement(new TaskData("","",new Date()));
            }
        });

    }


}