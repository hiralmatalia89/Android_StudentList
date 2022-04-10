package com.example.studentnotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

public class StudentDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private EditText courseEditText, studentEditText;
    private Button deleteButton;
    private Student selectedStudent;
    Spinner prioritySpiner;
    String[] priority = {"Graduate", "4th Year", "3rd Year", "2nd Year", "1st Year"};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        initWidgets();
        checkForEditNote();
    }

    private void initWidgets()
    {
        courseEditText = findViewById(R.id.courseEditText);
        studentEditText = findViewById(R.id.studentEditText);
        prioritySpiner = findViewById(R.id.priority);
        deleteButton = findViewById(R.id.deleteNoteButton);

        prioritySpiner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,priority);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpiner.setAdapter(arrayAdapter);
    }

    private void checkForEditNote()
    {
        Intent previousIntent = getIntent();

        int passedNoteID = previousIntent.getIntExtra(Student.NOTE_EDIT_EXTRA, -1);
        selectedStudent = Student.getStudentForID(passedNoteID);

        if (selectedStudent != null)
        {
            courseEditText.setText(selectedStudent.getCoursename());
            studentEditText.setText(selectedStudent.getStudentname());
        }
        else
        {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    public void saveStudent(View view)
    {
        SQLLiteManager sqLiteManager = SQLLiteManager.instanceOfDatabase(this);
        String coursename = String.valueOf(courseEditText.getText());
        String studentname = String.valueOf(studentEditText.getText());
        String priority = String.valueOf(prioritySpiner.getItemAtPosition(prioritySpiner.getSelectedItemPosition()).toString());

        if(selectedStudent == null)
        {
            int id = Student.studentArrayList.size();
            Student newStudent = new Student(id, coursename, studentname, priority);
            Student.studentArrayList.add(newStudent);
            sqLiteManager.addNoteToDatabase(newStudent);
        }
        else
        {
            selectedStudent.setCoursename(coursename);
            selectedStudent.setStudentname(studentname);
            selectedStudent.setPriority(priority);
            sqLiteManager.updateNoteInDB(selectedStudent);
        }

        finish();
    }

    public void deleteStudent(View view)
    {
        selectedStudent.setDeleted(new Date());
        SQLLiteManager sqLiteManager = SQLLiteManager.instanceOfDatabase(this);
        sqLiteManager.updateNoteInDB(selectedStudent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}