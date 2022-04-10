package com.example.studentnotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
{
    private ListView noteListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        loadFromDBToMemory();
        setNoteAdapter();
        setOnClickListener();
    }


    private void initWidgets()
    {
        noteListView = findViewById(R.id.lstView);
    }

    private void loadFromDBToMemory()
    {
        SQLLiteManager sqLiteManager = SQLLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateNoteListArray();
    }

    private void setNoteAdapter()
    {
        StudentAdapter studentAdapter = new StudentAdapter(getApplicationContext(), Student.nonDeletedStudent());
        noteListView.setAdapter(studentAdapter);
    }


    private void setOnClickListener()
    {
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Student selectedNote = (Student) noteListView.getItemAtPosition(position);
                Intent editNoteIntent = new Intent(getApplicationContext(), StudentDetailActivity.class);
                editNoteIntent.putExtra(Student.NOTE_EDIT_EXTRA, selectedNote.getId());
                startActivity(editNoteIntent);
            }
        });
    }


    public void newList(View view)
    {
        Intent newNoteIntent = new Intent(this, StudentDetailActivity.class);
        startActivity(newNoteIntent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setNoteAdapter();
    }
}