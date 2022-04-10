package com.example.studentnotesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student>
{
    public StudentAdapter(Context context, List<Student> students)
    {
        super(context, 0, students);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Student student = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_cell, parent, false);

        TextView coursename = convertView.findViewById(R.id.cellCourseName);
        TextView studentname = convertView.findViewById(R.id.cellStudentName);
        TextView priority = convertView.findViewById(R.id.cellPriority);

        coursename.setText(student.getCoursename());
        studentname.setText(student.getStudentname());
        priority.setText(student.getPriority());

        return convertView;
    }
}
